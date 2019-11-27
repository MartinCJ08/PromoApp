package com.example.promoapp.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.promoapp.Fragments.MyArFragment;
import com.example.promoapp.R;
import com.google.ar.core.Anchor;
import com.google.ar.core.AugmentedImage;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class ArActivity extends AppCompatActivity {
    ArFragment arFragment;
    boolean shouldAddModel = true;
    boolean scan = false;
    Intent inData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);

        inData = new Intent();

        arFragment = (MyArFragment) getSupportFragmentManager().findFragmentById(R.id.my_ar_fragment);
        arFragment.getPlaneDiscoveryController().hide();
        arFragment.getArSceneView().getScene().addOnUpdateListener(this::onUpdateFrame);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void onUpdateFrame(FrameTime frameTime) {
        Frame frame = arFragment.getArSceneView().getArFrame();
        Collection<AugmentedImage> augmentedImages = frame.getUpdatedTrackables(AugmentedImage.class);
        for (AugmentedImage augmentedImage : augmentedImages) {
            if (augmentedImage.getTrackingState() == TrackingState.TRACKING) {
                if (augmentedImage.getName().equals("fox") && shouldAddModel) {
                    placeObject(arFragment, augmentedImage.createAnchor(augmentedImage.getCenterPose()), Uri.parse("ArcticFox_Posed.sfb"));
                    shouldAddModel = false;
                    scan = true;
                    inData.putExtra("PROMO", "fox");
                    setResult(Activity.RESULT_OK,inData);
                } else if(augmentedImage.getName().equals("domino") && shouldAddModel){
                    placeObject(arFragment, augmentedImage.createAnchor(augmentedImage.getCenterPose()),Uri.parse("Pizza_Slice_01.sfb"));
                    shouldAddModel = false;
                    scan = true;
                    inData.putExtra("PROMO", "dominos");
                    setResult(Activity.RESULT_OK,inData);
                } else if(augmentedImage.getName().equals("caffenio") && shouldAddModel) {
                    placeObject(arFragment, augmentedImage.createAnchor(augmentedImage.getCenterPose()),Uri.parse("caffenio.sfb"));
                    shouldAddModel = false;
                    scan = true;
                    inData.putExtra("PROMO", "caffenio");
                    setResult(Activity.RESULT_OK,inData);
                }else if(augmentedImage.getName().equals("xiaomi") && shouldAddModel) {
                    placeObject(arFragment, augmentedImage.createAnchor(augmentedImage.getCenterPose()),Uri.parse("xiaomi.sfb"));
                    shouldAddModel = false;
                    scan = true;
                    inData.putExtra("PROMO", "xiaomi");
                    setResult(Activity.RESULT_OK,inData);
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void placeObject(ArFragment arFragment, Anchor anchor, Uri parse) {
        Toast.makeText(getApplicationContext(),"Place Model",Toast.LENGTH_SHORT).show();
        ModelRenderable.builder()
                .setSource(arFragment.getContext(), parse)
                .build()
                .thenAccept(modelRenderable -> addNodeToScene(arFragment, anchor, modelRenderable))
                .exceptionally(throwable -> {
                    Toast.makeText(arFragment.getContext(), "Error:" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                    return null;
                });
    }

    public boolean setupAugmentedImagesDb(Config config, Session session) {
        AugmentedImageDatabase augmentedImageDatabase;
        Bitmap bmDomino = loadAugmentedImage("domino.png");
        Bitmap bmFox = loadAugmentedImage("earth.jpg");
        Bitmap bmCaffenio = loadAugmentedImage("caffenio.jpg");
//        Bitmap bmXiaomi = loadAugmentedImage("xiaomi.png"); TODO: Change image from xiaomi
        if (bmDomino == null || bmFox == null ) {
            return false;
        }
        augmentedImageDatabase = new AugmentedImageDatabase(session);
        augmentedImageDatabase.addImage("fox", bmFox);
        augmentedImageDatabase.addImage("domino", bmDomino);
        augmentedImageDatabase.addImage("caffenio", bmCaffenio);
//        augmentedImageDatabase.addImage("xiaomi", bmXiaomi);
        config.setAugmentedImageDatabase(augmentedImageDatabase);
        return true;
    }

    private Bitmap loadAugmentedImage(String nomFile) {
        try (InputStream is = getAssets().open(nomFile)) {
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            Log.wtf("wtf", "IO Exception", e);
        }
        return null;
    }

    private void addNodeToScene(ArFragment arFragment, Anchor anchor, Renderable renderable) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode node = new TransformableNode(arFragment.getTransformationSystem());
        node.setRenderable(renderable);
        node.setParent(anchorNode);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        node.select();
    }

    public void onSaveData(View v){
        if( !scan ){

        }

        finish();
    }
}
