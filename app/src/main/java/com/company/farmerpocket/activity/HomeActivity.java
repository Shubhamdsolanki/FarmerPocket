package com.company.farmerpocket.activity;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.company.farmerpocket.MainActivity;
import com.company.farmerpocket.R;
import com.company.farmerpocket.component.banner.ConvenientBanner;
import com.company.farmerpocket.component.banner.holder.CBViewHolderCreator;
import com.company.farmerpocket.component.banner.holder.Holder;
import com.company.farmerpocket.component.banner.listener.OnItemClickListener;
import com.company.farmerpocket.helper.ImageHelper;
import com.company.farmerpocket.helper.ToastHelper;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class HomeActivity extends AbsBaseActivity {


    @Bind(R.id.convenientBanner)
    ConvenientBanner banner;

    private String[] images = {"http://www.pp3.cn/uploads/allimg/111112/110323M57-5.jpg",
            "http://p4.so.qhimg.com/sdr/1228_768_/t013e442f43954f6ef4.jpg",
            "http://img2.3lian.com/2014/f2/37/d/39.jpg",
            "http://p1.so.qhimg.com/sdr/1228_768_/t01fc41b1c114cc1b46.jpg"
    };

    private List<String> networkImages;

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
        initBanner();
    }

    private void initBanner() {
        networkImages = Arrays.asList(images);
        banner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new NetworkImageHolderView();
            }
        }, networkImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(new OnBannerClickListener());

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    /**
     * 网络图片加载例子
     */
    private class NetworkImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            ImageHelper.getInstance().loadImage(imageView, images[position]);
        }
    }

    private class OnBannerClickListener implements OnItemClickListener {

        @Override
        public void onItemClick(int position) {
            ToastHelper.getInstance().showToast("点击了第：" + position + "张");
        }
    }

    /**
     * 首页侧滑菜单按钮点击事件
     */
    @OnClick(R.id.home_menu)
    public void homeMeunClick() {
        if (MainActivity.slidingMenu != null) MainActivity.slidingMenu.toggle();
    }

    @OnClick(R.id.icon_home_type_chaye)
    public void homeType1() {
        startCommonGoodsActivity("茶叶", null);
    }

    @OnClick(R.id.icon_home_type_shuiguo)
    public void homeType2() {
        startCommonGoodsActivity("水果", null);
    }

    @OnClick(R.id.icon_home_type_nongchan)
    public void homeType3() {
        startCommonGoodsActivity("农产品", null);
    }

    @OnClick(R.id.icon_home_type_shuichanhaixian)
    public void homeType4() {
        startCommonGoodsActivity("水产海鲜", null);
    }

    @OnClick(R.id.icon_home_type_jinkoushuiguo)
    public void homeType5() {
        startCommonGoodsActivity("进口水果", null);
    }

    @OnClick(R.id.icon_home_type_jinkounongchan)
    public void homeType6() {
        startCommonGoodsActivity("进口农产品", null);
    }

    @OnClick(R.id.icon_home_type_jinkoushuichan)
    public void homeType7() {
        startCommonGoodsActivity("进口水产", null);
    }

    /**
     * 跳转到商品页面
     */
    private void startCommonGoodsActivity(final String title, final String url) {
        if (title == null && url == null) return;
        if (url == null) {
            CommonGoodsListActivity.startCommonGoodsListActivity(HomeActivity.this, title);
        } else {
            CommonGoodsListActivity.startCommonGoodsListActivity(HomeActivity.this, title, url);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        banner.startTurning(4000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        banner.stopTurning();
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
                    Toast.makeText(HomeActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                    firstClickTime = System.currentTimeMillis();
                }
            }
        }
    }

}
