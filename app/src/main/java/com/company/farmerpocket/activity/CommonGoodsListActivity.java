package com.company.farmerpocket.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.company.farmerpocket.R;
import com.company.farmerpocket.adapter.CommonRecyclerAdapter;
import com.company.farmerpocket.adapter.baserecycler.BaseQuickAdapter;
import com.company.farmerpocket.api.RetrofitHelper;
import com.company.farmerpocket.api.interfaces.ApiCommonGoods;
import com.company.farmerpocket.bean.CommonShopBean;
import com.company.farmerpocket.component.refreshload.PullToRefreshLayout;
import com.company.farmerpocket.component.refreshload.pullableview.PullableRecyclerView;
import com.company.farmerpocket.helper.ToastHelper;

import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 通用商品展示页面
 */
public class CommonGoodsListActivity extends AbsBaseActivity {

    private final static String PAGE_TITLE = "PAGE_TITLE";
    private final static String SHOP_ID = "SHOP_ID";

    /**
     * 下拉刷新上拉加载控件
     */
    @Bind(R.id.common_refresh_scroll_view)
    PullToRefreshLayout mPullToRefreshLayout;

    /**
     * 可下拉刷新上拉加载的recyclerView
     */
    @Bind(R.id.refresh_common_recycler_content_view)
    PullableRecyclerView recyclerView;

    /**
     * 是否是首次进入请求数据
     */
    private boolean isFirstRequest = true;

    /**
     * 分类商品查询id
     */
    private String goodsId;

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
        goodsId = getIntent().getStringExtra(SHOP_ID);
        if (goodsId == null) return;
        //初始化recyclerView
        initRecyclerView();
        requestAPI();
        //设置下拉上拉
        setRefreshLoadMore();
    }

    private void setRefreshLoadMore() {
        mPullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                //重新请求api
                requestAPI();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                //暂时没有加载更多，模拟关闭
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    }
                },1000);

            }
        });
    }

    /**
     * 请求数据
     */
    private void requestAPI() {
        //服务器接口不规范，需要这样拼
        String url = "App/Index/cate/classId/";
        ApiCommonGoods commonGoods = RetrofitHelper.getRetrofit().create(ApiCommonGoods.class);
        Observable<CommonShopBean> observable = commonGoods.getCommonGoodsData(url+goodsId);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CommonShopBean>() {
                    @Override
                    public void onCompleted() {
                        //数据加载成功。关闭下拉刷新
                        mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                        isFirstRequest = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastHelper.getInstance().showToast("请求数据失败");
                        //数据加载失败，关闭下拉刷新，过滤首次加载
                        if (!isFirstRequest) mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                        isFirstRequest = false;
                    }

                    @Override
                    public void onNext(CommonShopBean commonShopBean) {
                        //获取商品信息
                        List<CommonShopBean.DataEntity> listGoods = commonShopBean.getData();
                        setAdapter(listGoods);
                    }
                });
    }

    /**
     * 设置adapter
     * @param listGoods
     */
    private void setAdapter(final List<CommonShopBean.DataEntity> listGoods) {
        CommonRecyclerAdapter adapter = new CommonRecyclerAdapter(this,listGoods);
        recyclerView.setAdapter(adapter);
        //设置点击事件
        adapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                WebViewActivity.startWebViewActivity(CommonGoodsListActivity.this,
                        listGoods.get(position).getShopUrl(),"商品详情");
            }
        });
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
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
     * @param id 分类商品的id
     */
    public static void startCommonGoodsListActivity(Context context,String title,String id){
        Intent intent = new Intent(context,CommonGoodsListActivity.class);
        intent.putExtra(PAGE_TITLE,title);
        intent.putExtra(SHOP_ID,id);
        context.startActivity(intent);
    }

}
