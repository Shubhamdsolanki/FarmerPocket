package com.company.farmerpocket.api;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;

import com.company.farmerpocket.FarmerPocketApplication;
import com.company.farmerpocket.common.log.Log;
import com.company.farmerpocket.utils.NetworkUtils;


/**
 * Created by GHY on 2016/5/3.
 * api请求助手类
 */
public class APIHelper {

    private Context mContext;
    private static APIHelper mInstance;

    public APIHelper(Context context) {
        this.mContext = context;
    }

    public static APIHelper getInstance() {
        if (mInstance == null) {
            mInstance = new APIHelper(FarmerPocketApplication.getInstance().getApplicationContext());
        }
        return mInstance;
    }

    /**
     * api请求
     * fragment中请求
     */
    public void requestApi(BaseApiFg baseApiFg) {
        Log.i("Api == RequestApi == " + baseApiFg);
        if (NetworkUtils.isConnected(mContext)) {
            //进行网络请求
            new OkHttpHelper(baseApiFg);
        } else {
            baseApiFg.onError((Fragment) baseApiFg.mReference.get(), ErrorCode.ERROR_NO_NET, "无网络连接");
        }
    }

    /**
     * api请求
     * activity中请求
     */
    public void requestApi(BaseApiAv baseApiAv) {
        Log.i("Api == RequestApi == " + baseApiAv);
        if (NetworkUtils.isConnected(mContext)) {
            //进行网络请求
            new OkHttpHelper(baseApiAv);
        } else {
            baseApiAv.onError((Activity) baseApiAv.mReference.get(), ErrorCode.ERROR_NO_NET, "无网络连接");
        }
    }

}
