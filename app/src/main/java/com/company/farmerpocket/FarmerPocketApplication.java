package com.company.farmerpocket;

import android.app.Application;

import com.company.farmerpocket.common.log.Log;
import com.company.farmerpocket.common.logger.LogLevel;
import com.company.farmerpocket.common.logger.Logger;

/**
 * Created by GHY on 2016/5/19.
 */
public class FarmerPocketApplication extends Application {

    private final String DEFAULT_LOGGER_TAG = "FarmerPocket";

    private static FarmerPocketApplication mInstance;

    public static synchronized FarmerPocketApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        //初始化log
        initLog();
    }

    /**
     * 初始化log logger
     */
    private void initLog() {
        if (BuildConfig.DEBUG) {
            Logger.init(DEFAULT_LOGGER_TAG).logLevel(LogLevel.FULL);// default LogLevel.FULL
        } else {
            //release versions不打印log
            Log.isPrint = false;
            Logger.init(DEFAULT_LOGGER_TAG).logLevel(LogLevel.NONE);// default LogLevel.FULL
        }
    }
}
