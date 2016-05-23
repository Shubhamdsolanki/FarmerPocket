package com.company.farmerpocket.api.okhttp.builder;


import com.company.farmerpocket.api.okhttp.OkHttpUtils;
import com.company.farmerpocket.api.okhttp.request.OtherRequest;
import com.company.farmerpocket.api.okhttp.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder {
    @Override
    public RequestCall build() {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers).build();
    }
}
