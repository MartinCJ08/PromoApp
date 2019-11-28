package com.example.promoapp.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.example.promoapp.Fragments.HomeFragment;
import com.example.promoapp.Item;
import com.example.promoapp.R;
import com.example.promoapp.Fragments.SettingsFragment;
import com.github.florent37.camerafragment.CameraFragment;
import com.github.florent37.camerafragment.configuration.Configuration;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity  {
    public static ArrayList<String> list;
    BottomNavigationView nav;
    private CameraFragment cameraFragment;
    Intent inArFragment;
    static final int SAVE_PROMO_CODE = 420;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inArFragment = new Intent(this, ArActivity.class);
        list = new ArrayList<>();
        nav = findViewById(R.id.bottom_navigation);
        nav.setOnNavigationItemSelectedListener(navListener);
        nav.getMenu().findItem(R.id.homeItem).setChecked(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener(){

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;
            switch (menuItem.getItemId()){
                case R.id.configItem:
                    selectedFragment = new SettingsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
//                    break;
                case R.id.homeItem:
                    selectedFragment = new HomeFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
//                    break;
                case R.id.cameraItem:
                    hasPermissionAndOpenCamera();
                    startActivityForResult(inArFragment,SAVE_PROMO_CODE);
//                    startActivity(inArFragment);
                    return true;
//                    selectedFragment = cameraFragment;
//                    break;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SAVE_PROMO_CODE){
            if(resultCode == RESULT_OK){
                list.add(data.getStringExtra("PROMO"));
            }
        }
    }
}
