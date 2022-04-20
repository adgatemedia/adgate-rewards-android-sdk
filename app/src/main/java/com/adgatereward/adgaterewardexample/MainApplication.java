package com.adgatereward.adgaterewardexample;

import android.app.Application;

import com.adgatemedia.sdk.classes.AdGateMedia;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AdGateMedia.initializeSdk(this);
    }
}
