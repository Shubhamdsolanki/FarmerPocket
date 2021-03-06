package com.company.farmerpocket.activity;

import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.company.farmerpocket.MainActivity;
import com.company.farmerpocket.R;
import com.company.farmerpocket.adapter.JifenShopRecyclerAdapter;
import com.company.farmerpocket.adapter.baserecycler.BaseQuickAdapter;
import com.company.farmerpocket.api.APIS;
import com.company.farmerpocket.api.RetrofitHelper;
import com.company.farmerpocket.api.interfaces.ApiJifenShop;
import com.company.farmerpocket.bean.JifenBean;
import com.company.farmerpocket.component.refreshload.PullToRefreshLayout;
import com.company.farmerpocket.component.refreshload.pullableview.PullableRecyclerView;

import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 积分商城
 */
public class ShopActivity extends AbsBaseActivity {

    /**
     * 下拉刷新上拉加载控件
     */
    @Bind(R.id.shop_refresh_scroll_view)
    PullToRefreshLayout mPullToRefreshLayout;

    /**
     * 可下拉刷新上拉加载的recyclerView
     */
    @Bind(R.id.refresh_shop_recycler_content_view)
    PullableRecyclerView recyclerView;

    /**
     * 是否是首次进入请求数据
     */
    private boolean isFirstRequest = true;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_shop;
    }

    @Override
    protected int setToolBarLeftIv() {
        return -1;
    }

    @Override
    protected String setToolBarTitle() {
        return "积分商城";
    }

    @Override
    protected void init() {
        setActivityStatus(ACTIVITY_STATUS_LOADING);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        //请求数据
        requestAPI();
        //设置下拉上拉
        setRefreshLoadMore();
    }

    /**
     * 设置刷新监听
     */
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
                }, 1000);

            }
        });
    }

    /**
     * 请求数据
     */
    private void requestAPI() {
        String url = APIS.SHOP;
        ApiJifenShop jifenShop = RetrofitHelper.getRetrofit().create(ApiJifenShop.class);
        Observable<JifenBean> observable = jifenShop.getJifenShopData(url);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JifenBean>() {
                    @Override
                    public void onCompleted() {
                        //数据加载成功。关闭下拉刷新
                        mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                        isFirstRequest = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        //数据加载失败，关闭下拉刷新，过滤首次加载
                        if (!isFirstRequest) mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                        isFirstRequest = false;
                        //请求失败，设置activity状态为error
                        if (getActivityStatus() != ACTIVITY_STATUS_ERROR) setActivityStatus(ACTIVITY_STATUS_ERROR);
                    }

                    @Override
                    public void onNext(JifenBean jifenBean) {
                        //设置activity状态为success
                        if (getActivityStatus() != ACTIVITY_STATUS_SUCCESS)
                            setActivityStatus(ACTIVITY_STATUS_SUCCESS);
                        List<JifenBean.DataEntity> jifenList = jifenBean.getData();
                        if (jifenList.size() == 0) setActivityStatus(ACTIVITY_STATUS_EMPTY);
                        setAdapter(jifenList);
                    }
                });
    }

    /**
     * 设置adapter
     *
     * @param jifenList
     */
    private void setAdapter(List<JifenBean.DataEntity> jifenList) {
        JifenShopRecyclerAdapter adapter = new JifenShopRecyclerAdapter(this, jifenList);
        recyclerView.setAdapter(adapter);
        //设置点击事件
        adapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //跳转到积分兑换
            }
        });
    }

    private long firstClickTime;

    @Override
    public void onBackPressed() {
        if (firstClickTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            //退出时先关闭侧滑菜单
            if (MainActivity.slidingMenu != null) {
                if (MainActivity.slidingMenu.isMenuShowing()) {
                    MainActivity.slidingMenu.toggle();
                } else {
                    Toast.makeText(ShopActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                    firstClickTime = System.currentTimeMillis();
                }
            }
        }
    }

    @Override
    protected void onErrorClick(View view) {
        super.onErrorClick(view);
        //重新请求
        setActivityStatus(ACTIVITY_STATUS_LOADING);
        requestAPI();
    }

}
