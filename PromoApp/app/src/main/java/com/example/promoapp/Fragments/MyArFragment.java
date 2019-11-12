package com.example.promoapp.Fragments;

import android.util.Log;

import com.example.promoapp.Activities.ArActivity;
import com.google.ar.core.Config;
import com.google.ar.core.Session;
import com.google.ar.sceneform.ux.ArFragment;

public class MyArFragment extends ArFragment {

    @Override
    protected Config getSessionConfiguration(Session session) {
        getPlaneDiscoveryController().setInstructionView(null);
        Config config = new Config(session);
        config.setUpdateMode(Config.UpdateMode.LATEST_CAMERA_IMAGE);
        session.configure(config);
        getArSceneView().setupSession(session);

        if ((((ArActivity) getActivity()).setupAugmentedImagesDb(config, session))) {
            Log.wtf("wtf", "Success");
        } else {
            Log.wtf("wtf","Faliure setting up db");
        }
        return config;
    }
}
