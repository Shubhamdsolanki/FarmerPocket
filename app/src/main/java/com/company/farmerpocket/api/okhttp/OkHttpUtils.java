package com.company.farmerpocket.api.okhttp;

import android.os.Handler;
import android.os.Looper;

import com.company.farmerpocket.api.okhttp.builder.GetBuilder;
import com.company.farmerpocket.api.okhttp.builder.HeadBuilder;
import com.company.farmerpocket.api.okhttp.builder.OtherRequestBuilder;
import com.company.farmerpocket.api.okhttp.builder.PostFileBuilder;
import com.company.farmerpocket.api.okhttp.builder.PostFormBuilder;
import com.company.farmerpocket.api.okhttp.builder.PostStringBuilder;
import com.company.farmerpocket.api.okhttp.callback.Callback;
import com.company.farmerpocket.api.okhttp.request.RequestCall;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * OkHttpUtils
 */
public class OkHttpUtils {

    public static final long DEFAULT_MILLISECONDS = 10000;
    private static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;

    public OkHttpUtils(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
            mOkHttpClient = okHttpClientBuilder.build();
        } else {
            mOkHttpClient = okHttpClient;
        }
        init();
    }

    private void init() {
        mDelivery = new Handler(Looper.getMainLooper());
    }


    public static OkHttpUtils getInstance(OkHttpClient okHttpClient) {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtils(okHttpClient);
                }
            }
        }
        return mInstance;
    }

    public static OkHttpUtils getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtils(null);
                }
            }
        }
        return mInstance;
    }


    public Handler getDelivery() {
        return mDelivery;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public static GetBuilder get() {
        return new GetBuilder();
    }

    public static PostStringBuilder postString() {
        return new PostStringBuilder();
    }

    public static PostFileBuilder postFile() {
        return new PostFileBuilder();
    }

    public static PostFormBuilder post() {
        return new PostFormBuilder();
    }

    public static OtherRequestBuilder put() {
        return new OtherRequestBuilder(METHOD.PUT);
    }

    public static HeadBuilder head() {
        return new HeadBuilder();
    }

    public static OtherRequestBuilder delete() {
        return new OtherRequestBuilder(METHOD.DELETE);
    }

    public static OtherRequestBuilder patch() {
        return new OtherRequestBuilder(METHOD.PATCH);
    }

    public void execute(final RequestCall requestCall, Callback callback) {
        if (callback == null)
            callback = Callback.CALLBACK_DEFAULT;
        final Callback finalCallback = callback;

        requestCall.getCall().enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                sendFailResultCallback(call, e, finalCallback);
            }

            @Override
            public void onResponse(final Call call, final Response response) {
                try {
                    Object o = finalCallback.parseNetworkResponse(response);
                    sendSuccessResultCallback(o, finalCallback);
                } catch (Exception e) {
                    sendFailResultCallback(call, e, finalCallback);
                }

            }
        });
    }

    public void sendFailResultCallback(final Call call, final Exception e, final Callback callback) {
        if (callback == null) return;
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(call, e);
                callback.onAfter();
            }
        });
    }

    public void sendSuccessResultCallback(final Object object, final Callback callback) {
        if (callback == null) return;
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(object);
                callback.onAfter();
            }
        });
    }

    public void cancelTag(Object tag) {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    public void setConnectTimeout(int timeout, TimeUnit units) {
        mOkHttpClient = getOkHttpClient().newBuilder()
                .connectTimeout(timeout, units)
                .build();
    }

    public void setReadTimeout(int timeout, TimeUnit units) {
        mOkHttpClient = getOkHttpClient().newBuilder()
                .readTimeout(timeout, units)
                .build();
    }

    public void setWriteTimeout(int timeout, TimeUnit units) {
        mOkHttpClient = getOkHttpClient().newBuilder()
                .writeTimeout(timeout, units)
                .build();
    }


    public static class METHOD {
        public static final String HEAD = "HEAD";
        public static final String DELETE = "DELETE";
        public static final String PUT = "PUT";
        public static final String PATCH = "PATCH";
    }
}

