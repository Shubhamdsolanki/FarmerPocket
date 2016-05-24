package com.company.farmerpocket.api.interfaces;


import com.company.farmerpocket.api.APIS;
import com.company.farmerpocket.bean.HomeBean;

import retrofit2.http.GET;
import rx.Observable;

/**
 * home页面
 */
public interface ApiHome {

    @GET(APIS.HOME_PAGE)
    Observable<HomeBean> getHomeData();

}
