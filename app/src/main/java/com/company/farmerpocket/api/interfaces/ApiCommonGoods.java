package com.company.farmerpocket.api.interfaces;


import com.company.farmerpocket.api.APIS;
import com.company.farmerpocket.bean.CommonShopBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 通用商品列表api
 */
public interface ApiCommonGoods {

    @GET(APIS.SHOP_TYPE)
    Observable<CommonShopBean> getCommonGoodsData(@Query("s") String id);

}
