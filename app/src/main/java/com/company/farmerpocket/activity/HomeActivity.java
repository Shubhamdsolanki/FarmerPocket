package com.company.farmerpocket.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.company.farmerpocket.MainActivity;
import com.company.farmerpocket.R;
import com.company.farmerpocket.adapter.HomeGridAdapter;
import com.company.farmerpocket.api.RetrofitHelper;
import com.company.farmerpocket.api.interfaces.ApiHome;
import com.company.farmerpocket.bean.HomeBean;
import com.company.farmerpocket.component.banner.ConvenientBanner;
import com.company.farmerpocket.component.banner.holder.CBViewHolderCreator;
import com.company.farmerpocket.component.banner.holder.Holder;
import com.company.farmerpocket.component.banner.listener.OnItemClickListener;
import com.company.farmerpocket.component.refreshload.PullToRefreshLayout;
import com.company.farmerpocket.component.refreshload.pullableview.PullableScrollViewHome;
import com.company.farmerpocket.helper.ImageHelper;
import com.company.farmerpocket.helper.ToastHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomeActivity extends AbsBaseActivity {

    /**
     * 首页下拉刷新控件
     */
    @Bind(R.id.home_refresh_scroll_view)
    PullToRefreshLayout mPullToRefreshLayout;
    /**
     * 首页ScrollView
     */
    @Bind(R.id.home_refresh_scroll_content_view)
    PullableScrollViewHome pullableScrollViewHome;
    /**
     * 首页banner
     */
    private ConvenientBanner mBanner;

    /**
     * 是否是首次进入请求数据
     */
    private boolean isFirstRequest = true;

    /**
     * 首页GridView
     */
    private GridView mGridView;

    private List<String> networkImages = new ArrayList<>();
    private List<String> networkImagesUrl = new ArrayList<>();

    /**
     * 商品类别，用于获取id
     */
    private List<HomeBean.DataEntity.ClassEntity> shopType = new ArrayList<>();
    private String[] typeId = new String[10];

    @Override
    protected int getLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    protected boolean isOpenToolBar() {
        return false;
    }

    @Override
    protected void init() {
        //加载内容布局
        View contentView = LayoutInflater.from(this).inflate(R.layout.activity_home_content, null);
        pullableScrollViewHome.addView(contentView);
        //初始化view
        //注意，后加载进来的内容布局不能使用bufferKnife的注解
        initView();
        //请求首页数据
        requestAPI();
        //设置下拉刷新监听
        setRefreshListener();
    }

    /**
     * 设置下拉刷新监听
     */
    private void setRefreshListener() {
        mPullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                //重新请求首页数据
                requestAPI();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

            }
        });
    }

    /**
     * 请求首页数据
     */
    private void requestAPI() {
        ApiHome apiHome = RetrofitHelper.getRetrofit().create(ApiHome.class);
        Observable<HomeBean> observable = apiHome.getHomeData();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HomeBean>() {
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
                    public void onNext(HomeBean homeBean) {
                        List bannerList = homeBean.getData().getCarouselImg();
                        networkImages = new ArrayList<>();
                        for (int i = 0; i < bannerList.size(); i++) {
                            networkImages.add(homeBean.getData().getCarouselImg().get(i).getCarouselImgUrl());
                            networkImagesUrl.add(homeBean.getData().getCarouselImg().get(i).getCarouselUrl());
                        }
                        //加载banner数据
                        initBanner();
                        //商品类别id
                        initTypeId(homeBean);
                        //加载首页数据
                        List<HomeBean.DataEntity.ShopEntity> listShop = homeBean.getData().getShop();
                        setAdapter(listShop);
                    }
                });
    }

    /**
     * 获取商品类别的id
     * @param homeBean
     */
    private void initTypeId(HomeBean homeBean) {
        shopType = homeBean.getData().getClassX();
        if (shopType.size()>10) return;
        for (int i=0;i<shopType.size();i++){
            typeId[i]=shopType.get(i).getClassId();
        }
    }

    /**
     * 设置adapter
     *
     * @param listShop
     */
    private void setAdapter(final List<HomeBean.DataEntity.ShopEntity> listShop) {
        HomeGridAdapter adapter = new HomeGridAdapter(this, listShop);
        mGridView.setAdapter(adapter);
        //解决滑动冲突后需手动滚动scrollView到顶部
        pullableScrollViewHome.smoothScrollTo(0, 0);

        //点击事件
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //跳转到商品详情页面
                WebViewActivity.startWebViewActivity(HomeActivity.this, listShop.get(position).getShopUrl(), "商品详情");
            }
        });
    }

    /**
     * 初始化View
     */
    private void initView() {
        mBanner = (ConvenientBanner) findViewById(R.id.convenientBanner);
        mGridView = (GridView) findViewById(R.id.grid_view);
    }

    /**
     * 加载banner
     */
    private void initBanner() {
        mBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new NetworkImageHolderView();
            }
        }, networkImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                .setOnItemClickListener(new OnBannerClickListener());
    }

    /**
     * 首页分类按钮点击事件
     *
     * @param view
     */
    public void homeTypeClick(View view) {
        switch (view.getId()) {
            case R.id.icon_home_type_chaye:
                startCommonGoodsActivity("茶叶", typeId[0]);
                break;
            case R.id.icon_home_type_shuiguo:
                startCommonGoodsActivity("水果", typeId[1]);
                break;
            case R.id.icon_home_type_nongchan:
                startCommonGoodsActivity("农产品", typeId[2]);
                break;
            case R.id.icon_home_type_shuichanhaixian:
                startCommonGoodsActivity("水产海鲜",typeId[3]);
                break;
            case R.id.icon_home_type_jinkoushuiguo:
                startCommonGoodsActivity("进口水果", typeId[4]);
                break;
            case R.id.icon_home_type_jinkounongchan:
                startCommonGoodsActivity("进口农产品", typeId[5]);
                break;
            case R.id.icon_home_type_jinkoushuichan:
                startCommonGoodsActivity("进口水产", typeId[6]);
                break;
            case R.id.icon_home_type_huafei:
                startCommonGoodsActivity("话费充值", typeId[7]);
                break;
        }
    }


    /**
     * banner网络图片加载
     */
    private class NetworkImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            ImageHelper.getInstance().loadImage(imageView, networkImages.get(position));
        }
    }

    /**
     * banner点击事件
     */
    private class OnBannerClickListener implements OnItemClickListener {

        @Override
        public void onItemClick(int position) {

        }
    }

    /**
     * 首页侧滑菜单按钮点击事件
     */
    @OnClick(R.id.home_menu)
    public void homeMeunClick() {
        if (MainActivity.slidingMenu != null) MainActivity.slidingMenu.toggle();
    }

    /**
     * 跳转到商品页面
     */
    private void startCommonGoodsActivity(final String title, final String id) {
        if (title == null && id == null) return;
        if (id == null) {
            CommonGoodsListActivity.startCommonGoodsListActivity(HomeActivity.this, title);
        } else {
            CommonGoodsListActivity.startCommonGoodsListActivity(HomeActivity.this, title, id);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBanner.startTurning(4000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBanner.stopTurning();
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
                    Toast.makeText(HomeActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                    firstClickTime = System.currentTimeMillis();
                }
            }
        }
    }

}
