package com.company.farmerpocket;

import android.app.Application;

/**
 * Created by GHY on 2016/5/19.
 */
public class FarmerPocketApplication extends Application {

    private static FarmerPocketApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static FarmerPocketApplication getInstance(){
        return mInstance;
    }
}
