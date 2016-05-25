package com.company.farmerpocket.activity;

import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.company.farmerpocket.MainActivity;
import com.company.farmerpocket.R;
import com.company.farmerpocket.adapter.CommonRecyclerAdapter;
import com.company.farmerpocket.adapter.baserecycler.BaseQuickAdapter;
import com.company.farmerpocket.api.APIS;
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
 * 品牌团
 */
public class GroupActivity extends AbsBaseActivity {

    /**
     * 下拉刷新上拉加载控件
     */
    @Bind(R.id.group_refresh_scroll_view)
    PullToRefreshLayout mPullToRefreshLayout;

    /**
     * 可下拉刷新上拉加载的recyclerView
     */
    @Bind(R.id.refresh_group_recycler_content_view)
    PullableRecyclerView recyclerView;

    /**
     * 是否是首次进入请求数据
     */
    private boolean isFirstRequest = true;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_group;
    }

    @Override
    protected boolean isOpenToolBar() {
        return false;
    }

    @Override
    protected void init() {
        setActivityStatus(ACTIVITY_STATUS_LOADING);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        //请求数据
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
        String url = APIS.GROUP;
        ApiCommonGoods commonGoods = RetrofitHelper.getRetrofit().create(ApiCommonGoods.class);
        Observable<CommonShopBean> observable = commonGoods.getCommonGoodsData(url);
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
                        //设置activity状态为success
                        if (getActivityStatus() != ACTIVITY_STATUS_SUCCESS) setActivityStatus(ACTIVITY_STATUS_SUCCESS);
                        //获取商品信息
                        List<CommonShopBean.DataEntity> listGoods = commonShopBean.getData();
                        if (listGoods.size() == 0) setActivityStatus(ACTIVITY_STATUS_EMPTY);
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
                WebViewActivity.startWebViewActivity(GroupActivity.this,
                        listGoods.get(position).getShopUrl(),"商品详情");
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
                if (MainActivity.slidingMenu.isMenuShowing()){
                    MainActivity.slidingMenu.toggle();
                }else {
                    Toast.makeText(GroupActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                    firstClickTime = System.currentTimeMillis();
                }
            }
        }
    }
}
