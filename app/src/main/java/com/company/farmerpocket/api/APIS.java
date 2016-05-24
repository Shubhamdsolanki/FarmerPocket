package com.company.farmerpocket.api;

/**
 * Created by GHY on 2016/5/3.
 * 服务端接口
 */
public class APIS {

    public static final String BASE_URL = "http://114.215.95.55:9995/";

    /**
     * 首页
     */
    public static final String HOME_PAGE = "index.php?s=/App/Index";

    /**
     * 商品分类页面
     * 需要拼接classId
     * 例如：http://114.215.95.55:9995/index.php?s=App/Index/cate/classId/41
     */
    public static final String SHOP_TYPE = "index.php?s=App/Index/cate/classId/";


}
