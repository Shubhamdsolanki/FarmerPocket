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
     * INDEX
     */
    public static final String INDEX = "index.php?";

    /**
     * 首页商品分类页面
     * 需要拼接classId
     * 例如：http://114.215.95.55:9995/index.php?s=App/Index/cate/classId/41
     */
    public static final String HOME_TYPE = "App/Index/cate/classId/";

    /**
     * 品牌团
     */
    public static final String GROUP = "App/Index/pinpaituan.html";

    /**
     * 今日推荐
     */
    public static final String RECOMMEND = "App/Index/todayrem.html";

    /**
     * 积分商城
     */
    public static final String SHOP = "App/Index/gift.html";


}
