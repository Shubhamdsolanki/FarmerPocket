package com.company.farmerpocket;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;

import com.company.farmerpocket.activity.CommonGoodsListActivity;
import com.company.farmerpocket.activity.GroupActivity;
import com.company.farmerpocket.activity.HomeActivity;
import com.company.farmerpocket.activity.RecommendActivity;
import com.company.farmerpocket.activity.ShopActivity;
import com.company.farmerpocket.activity.UserActivity;
import com.company.farmerpocket.component.slidingmenu.SlidingMenu;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * MainActivity
 */
public class MainActivity extends TabActivity implements RadioGroup.OnCheckedChangeListener {

    //底部导航条
    private RadioGroup mBottomTab;
    private TabHost mTabHost;
    public static SlidingMenu slidingMenu;

    private Intent mIntentPageOne;
    private Intent mIntentPageTwo;
    private Intent mIntentPageThree;
    private Intent mIntentPageFour;
    private Intent mIntentPageFive;

    private final String INTENT_PAGE_ONE = "INTENT_PAGE_ONE";
    private final String INTENT_PAGE_TWO = "INTENT_PAGE_TWO";
    private final String INTENT_PAGE_THREE = "INTENT_PAGE_THREE";
    private final String INTENT_PAGE_FOUR = "INTENT_PAGE_FOUR";
    private final String INTENT_PAGE_FIVE = "INTENT_PAGE_FIVE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化侧滑菜单
        initSlidingMenu();
        //初始化butterKnife
        ButterKnife.bind(this);
        mBottomTab = (RadioGroup) findViewById(R.id.slid_main_tab);
        mBottomTab.setOnCheckedChangeListener(this);
        mTabHost = getTabHost();
        initTabIntent();
        initTabs();
    }

    /**
     * 初始化侧滑菜单
     */
    private void initSlidingMenu() {
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.layout_slid_menu);
    }

    @OnClick(R.id.slid_menu_item_one)
    public void slidMenuClick(){
        closeMenuStartCommonGoodsActivity("茶叶",null);
    }

    @OnClick(R.id.slid_menu_item_two)
    public void slidMenuClick2(){
        closeMenuStartCommonGoodsActivity("水果",null);
    }

    @OnClick(R.id.slid_menu_item_three)
    public void slidMenuClick3(){
        closeMenuStartCommonGoodsActivity("农产品",null);
    }

    @OnClick(R.id.slid_menu_item_four)
    public void slidMenuClick4(){
        closeMenuStartCommonGoodsActivity("水产海鲜",null);
    }


    @OnClick(R.id.slid_menu_item_five)
    public void slidMenuClick5(){
        closeMenuStartCommonGoodsActivity("进口水果",null);
    }


    @OnClick(R.id.slid_menu_item_six)
    public void slidMenuClick6(){
        closeMenuStartCommonGoodsActivity("进口农产品",null);
    }

    @OnClick(R.id.slid_menu_item_seven)
    public void slidMenuClick7(){
        closeMenuStartCommonGoodsActivity("进口水产",null);
    }

    /**
     * 跳转页面并关闭侧滑菜单
     */
    private void closeMenuStartActivity(final Class<?> activity){
        if (slidingMenu.isMenuShowing()) slidingMenu.toggle();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,activity);
                startActivity(intent);
            }
        },500);
    }

    /**
     * 跳转到商品页面并关闭侧滑菜单
     */
    private void closeMenuStartCommonGoodsActivity(final String title, final String url){
//        if (slidingMenu.isMenuShowing()) slidingMenu.toggle();
        if (title == null && url == null) return;
        if (url == null){
            CommonGoodsListActivity.startCommonGoodsListActivity(MainActivity.this, title);
        }else {
            CommonGoodsListActivity.startCommonGoodsListActivity(MainActivity.this,title,url);
        }
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (url == null){
//                    CommonGoodsListActivity.startCommonGoodsListActivity(MainActivity.this, title);
//                }else {
//                    CommonGoodsListActivity.startCommonGoodsListActivity(MainActivity.this,title,url);
//                }
//            }
//        },500);
    }

    /**
     * 分发intent
     * 用于跳转到各个页面
     *
     * @param intent
     */
    private void dispatchIntent(Intent intent) {
        if (intent == null || intent.getStringExtra("source") == null) return;
        String source = intent.getStringExtra("source");
        mTabHost.setCurrentTabByTag(source);
        if (source.equals(INTENT_PAGE_ONE)) {
            ((RadioButton) mBottomTab.findViewById(R.id.slid_tab_intent1)).setChecked(true);
        } else if (source.equals(INTENT_PAGE_TWO)) {
            ((RadioButton) mBottomTab.findViewById(R.id.slid_tab_intent2)).setChecked(true);
        } else if (source.equals(INTENT_PAGE_THREE)) {
            ((RadioButton) mBottomTab.findViewById(R.id.slid_tab_intent3)).setChecked(true);
        } else if (source.equals(INTENT_PAGE_FOUR)) {
            ((RadioButton) mBottomTab.findViewById(R.id.slid_tab_intent4)).setChecked(true);
        } else if (source.equals(INTENT_PAGE_FIVE)) {
            ((RadioButton) mBottomTab.findViewById(R.id.slid_tab_intent5)).setChecked(true);
        }else {
            //do nothing
        }
    }


    private void initTabs() {
        mTabHost.addTab(mTabHost
                .newTabSpec(INTENT_PAGE_ONE)
                .setIndicator(INTENT_PAGE_ONE, getResources().getDrawable(R.mipmap.ic_launcher))
                .setContent(mIntentPageOne));

        mTabHost.addTab(mTabHost
                .newTabSpec(INTENT_PAGE_TWO)
                .setIndicator(INTENT_PAGE_TWO, getResources().getDrawable(R.mipmap.ic_launcher))
                .setContent(mIntentPageTwo));

        mTabHost.addTab(mTabHost
                .newTabSpec(INTENT_PAGE_THREE)
                .setIndicator(INTENT_PAGE_THREE, getResources().getDrawable(R.mipmap.ic_launcher))
                .setContent(mIntentPageThree));

        mTabHost.addTab(mTabHost
                .newTabSpec(INTENT_PAGE_FOUR)
                .setIndicator(INTENT_PAGE_FOUR, getResources().getDrawable(R.mipmap.ic_launcher))
                .setContent(mIntentPageFour));

        mTabHost.addTab(mTabHost
                .newTabSpec(INTENT_PAGE_FIVE)
                .setIndicator(INTENT_PAGE_FIVE, getResources().getDrawable(R.mipmap.ic_launcher))
                .setContent(mIntentPageFive));

    }

    private void initTabIntent() {
        mIntentPageOne = new Intent(this, HomeActivity.class);
        mIntentPageTwo = new Intent(this, RecommendActivity.class);
        mIntentPageThree = new Intent(this, GroupActivity.class);
        mIntentPageFour = new Intent(this, ShopActivity.class);
        mIntentPageFive = new Intent(this, UserActivity.class);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.slid_tab_intent1:
                mTabHost.setCurrentTabByTag(INTENT_PAGE_ONE);
                break;
            case R.id.slid_tab_intent2:
                mTabHost.setCurrentTabByTag(INTENT_PAGE_TWO);
                break;
            case R.id.slid_tab_intent3:
                mTabHost.setCurrentTabByTag(INTENT_PAGE_THREE);
                break;
            case R.id.slid_tab_intent4:
                mTabHost.setCurrentTabByTag(INTENT_PAGE_FOUR);
                break;
            case R.id.slid_tab_intent5:
                mTabHost.setCurrentTabByTag(INTENT_PAGE_FIVE);
                break;
        }
    }

}
