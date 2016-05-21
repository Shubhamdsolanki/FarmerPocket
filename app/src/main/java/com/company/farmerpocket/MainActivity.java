package com.company.farmerpocket;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;

import com.company.farmerpocket.activity.GroupActivity;
import com.company.farmerpocket.activity.HomeActivity;
import com.company.farmerpocket.activity.RecommendActivity;
import com.company.farmerpocket.activity.ShopActivity;
import com.company.farmerpocket.activity.UserActivity;
import com.company.farmerpocket.component.slidmenu.SlidingMenu;
import com.company.farmerpocket.helper.ToastHelper;

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
        ButterKnife.bind(this);
        mBottomTab = (RadioGroup) findViewById(R.id.slid_main_tab);
        slidingMenu = (SlidingMenu) findViewById(R.id.id_slid_menu_tab);
        slidingMenu.setScale(false);//正常侧滑，不缩放
        mBottomTab.setOnCheckedChangeListener(this);
        mTabHost = getTabHost();
        initTabIntent();
        initTabs();
    }

    @OnClick(R.id.slid_menu_item_one)
    public void slidMenuClick(){
        ToastHelper.getInstance().showToast("item1");
    }

    @OnClick(R.id.slid_menu_item_two)
    public void slidMenuClick2(){
        ToastHelper.getInstance().showToast("item2");
    }

    @OnClick(R.id.slid_menu_item_three)
    public void slidMenuClick3(){
        ToastHelper.getInstance().showToast("item3");
    }

    @OnClick(R.id.slid_menu_item_four)
    public void slidMenuClick4(){
        ToastHelper.getInstance().showToast("item4");
    }


    @OnClick(R.id.slid_menu_item_five)
    public void slidMenuClick5(){
        ToastHelper.getInstance().showToast("item5");
    }


    @OnClick(R.id.slid_menu_item_six)
    public void slidMenuClick6(){
        ToastHelper.getInstance().showToast("item6");
    }

    @OnClick(R.id.slid_menu_item_seven)
    public void slidMenuClick7(){
        ToastHelper.getInstance().showToast("item7");
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
