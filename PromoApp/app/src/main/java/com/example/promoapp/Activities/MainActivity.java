package com.example.promoapp.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.promoapp.Fragments.CustomARFragment;
import com.example.promoapp.HomeFragment;
import com.example.promoapp.R;
import com.example.promoapp.SettingsFragment;
import com.example.promoapp.TestARFragment;
import com.github.florent37.camerafragment.CameraFragment;
import com.github.florent37.camerafragment.configuration.Configuration;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.ar.core.Anchor;
import com.google.ar.core.ArCoreApk;
import com.google.ar.core.AugmentedImage;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.core.exceptions.UnavailableApkTooOldException;
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException;
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException;
import com.google.ar.core.exceptions.UnavailableSdkTooOldException;
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;

import java.net.URL;
import java.util.Collection;

public class MainActivity extends AppCompatActivity implements Scene.OnUpdateListener, CustomARFragment.OnCompleteListener {
    BottomNavigationView nav;
    private CameraFragment cameraFragment;
    private CustomARFragment customARFragment;
    private TestARFragment testARFragment;

    private boolean mUserRequestedInstall = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nav = findViewById(R.id.bottom_navigation);
        nav.setOnNavigationItemSelectedListener(navListener);
        nav.getMenu().findItem(R.id.homeItem).setChecked(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    public void setupDatabase(Config config, Session session){
        Bitmap dratiniMap = BitmapFactory.decodeResource(getResources(),R.drawable.dratini);
        AugmentedImageDatabase aid = new AugmentedImageDatabase(session);
        aid.addImage("dratini",dratiniMap);
        config.setAugmentedImageDatabase(aid);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener(){

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;
            switch (menuItem.getItemId()){
                case R.id.configItem:
                    selectedFragment = new SettingsFragment();
                    break;
                case R.id.homeItem:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.cameraItem:
                    hasPermissionAndOpenCamera();
                    selectedFragment = new CustomARFragment();
//                    selectedFragment = cameraFragment;

                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };

    private void hasPermissionAndOpenCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startActivityCameraFragment();
        } else {
            requestPermission();
        }
    }

    private void startActivityCameraFragment() {
        cameraFragment = CameraFragment.newInstance(new Configuration.Builder().build());
    }

    private void requestPermission() {
        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

        ActivityCompat.requestPermissions(this, permissions, PackageManager.PERMISSION_GRANTED);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onUpdate(FrameTime frameTime) {
        Frame frame = customARFragment.getArSceneView().getArFrame();
//        Collection<AugmentedImage> images = frame.getUpdatedTrackables(AugmentedImage.class);
//
//        for(AugmentedImage image : images){
//            if(image.getTrackingState() == TrackingState.TRACKING){
//                if(image.getName().equals("dratini")){
//                    Anchor anchor = image.createAnchor(image.getCenterPose());
//                    createModel(anchor);
//                }
//            }
//        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createModel(Anchor anchor) {
        ModelRenderable.builder()
                .setSource(this, Uri.parse("model.sfb"))
                .build()
                .thenAccept(modelRenderable -> placeModal(modelRenderable,anchor));
    }

    private void placeModal(ModelRenderable modelRenderable, Anchor anchor) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setRenderable(modelRenderable);
        customARFragment.getArSceneView().getScene().addChild(anchorNode);
    }

    @Override
    public void onComplete() {
        ArFragment arFragment = (ArFragment) getSupportFragmentManager().findFragmentByTag("my_arfragment");
        ArSceneView view = arFragment.getArSceneView();
        Scene scene = view.getScene();
        scene.addOnUpdateListener(this::onUpdateFrame);
    }

    private void onUpdateFrame(FrameTime frameTime) {
        Frame frame = customARFragment.getArSceneView().getArFrame();
        Collection<AugmentedImage> images = frame.getUpdatedTrackables(AugmentedImage.class);

        for(AugmentedImage image : images){
            if(image.getTrackingState() == TrackingState.TRACKING){
                if(image.getName().equals("dratini")){
                    Anchor anchor = image.createAnchor(image.getCenterPose());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        createModel(anchor);
                    }
                }
            }
        }
    }
}
