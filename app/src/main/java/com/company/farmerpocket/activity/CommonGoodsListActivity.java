package com.company.farmerpocket.activity;

import android.content.Context;
import android.content.Intent;

import com.company.farmerpocket.R;

/**
 * 通用商品展示页面
 */
public class CommonGoodsListActivity extends AbsBaseActivity {

    private final static String PAGE_TITLE = "PAGE_TITLE";
    private final static String URL = "URL";

    @Override
    protected int getLayoutID() {
        return R.layout.activity_common_goods_list;
    }

    @Override
    protected int setToolBarRightIv() {
        return R.mipmap.icon_search;
    }

    @Override
    protected void init() {
        //设置页面标题
        String pageTitle = getIntent().getStringExtra(PAGE_TITLE);
        setToolBarTitle(pageTitle);
        //获取传递过来的url
        String url = getIntent().getStringExtra(URL);
    }

    /**
     * 开启商品展示列表页面
     * @param context
     * @param title 页面标题
     */
    public static void startCommonGoodsListActivity(Context context,String title){
        Intent intent = new Intent(context,CommonGoodsListActivity.class);
        intent.putExtra(PAGE_TITLE,title);
        context.startActivity(intent);
    }

    /**
     * 开启商品展示列表页面
     * @param context
     * @param title 页面标题
     * @param url 请求的url
     */
    public static void startCommonGoodsListActivity(Context context,String title,String url){
        Intent intent = new Intent(context,CommonGoodsListActivity.class);
        intent.putExtra(PAGE_TITLE,title);
        intent.putExtra(URL,url);
        context.startActivity(intent);
    }

}
