package com.company.farmerpocket.api.interfaces;


import com.company.farmerpocket.api.APIS;
import com.company.farmerpocket.bean.JifenBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 积分商城api
 */
public interface ApiJifenShop {

    @GET(APIS.INDEX)
    Observable<JifenBean> getJifenShopData(@Query("s") String url);

}
