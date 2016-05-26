package com.company.farmerpocket.activity;

import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.company.farmerpocket.utils.ImeUtils;

import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RecommendActivity extends AbsBaseActivity {

    /**
     * 搜索布局
     */
    @Bind(R.id.base_toolbar_search)
    LinearLayout searchLayout;

    /**
     * 下拉刷新上拉加载控件
     */
    @Bind(R.id.recom_refresh_scroll_view)
    PullToRefreshLayout mPullToRefreshLayout;

    /**
     * 可下拉刷新上拉加载的recyclerView
     */
    @Bind(R.id.refresh_recom_recycler_content_view)
    PullableRecyclerView recyclerView;

    /**
     * 是否是首次进入请求数据
     */
    private boolean isFirstRequest = true;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_recommend;
    }

    @Override
    protected int setToolBarLeftIv() {
        return -1;
    }

    @Override
    protected String setToolBarTitle() {
        return "今日推荐";
    }

    @Override
    protected int setToolBarRightIv() {
        return R.mipmap.icon_search;
    }

    @Override
    protected void init() {
        //设置toolbar按钮点击事件
        setToolBarClickListener();
        setActivityStatus(ACTIVITY_STATUS_LOADING);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        //请求数据
        requestAPI();
        //设置下拉上拉
        setRefreshLoadMore();
    }

    /**
     * 设置toolBar点击事件
     */
    private void setToolBarClickListener() {
        setOnToolBarRightIvClickListener(new ToolBarRightIvClickListener() {
            @Override
            public void onToolBarRightIvClick() {
                setToolBarIsVisibility(View.GONE);
                searchLayout.setVisibility(View.VISIBLE);
                TextView tvCancel = (TextView) searchLayout.findViewById(R.id.search_cancel);
                final EditText editText = (EditText) searchLayout.findViewById(R.id.search_et);
                ImeUtils.showSoftKeyboard(editText);
                editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setToolBarIsVisibility(View.VISIBLE);
                        searchLayout.setVisibility(View.GONE);
                        ImeUtils.hideSoftKeyboard(editText);
                    }
                });
                editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            ImeUtils.hideSoftKeyboard(editText);
                            ToastHelper.getInstance().showToast(editText.getText().toString());
                            editText.setText("");
                            setToolBarIsVisibility(View.VISIBLE);
                            searchLayout.setVisibility(View.GONE);
                            return true;
                        }
                        return false;
                    }

                });
            }
        });
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
                },1000);

            }
        });
    }

    /**
     * 请求数据
     */
    private void requestAPI() {
        String url = APIS.RECOMMEND;
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
                        //数据加载失败，关闭下拉刷新，过滤首次加载
                        if (!isFirstRequest) mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                        isFirstRequest = false;
                        //请求失败，设置activity状态为error
                        if (getActivityStatus() != ACTIVITY_STATUS_ERROR) setActivityStatus(ACTIVITY_STATUS_ERROR);
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
                WebViewActivity.startWebViewActivity(RecommendActivity.this,
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
                    Toast.makeText(RecommendActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
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
