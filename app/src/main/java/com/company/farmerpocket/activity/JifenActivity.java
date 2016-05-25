package com.company.farmerpocket.activity;

import com.company.farmerpocket.R;

/**
 * 我的积分页面
 */
public class JifenActivity extends AbsBaseActivity {

    @Override
    protected int getLayoutID() {
        return R.layout.activity_jifen;
    }

    @Override
    protected String setToolBarTitle() {
        return "我的积分";
    }

    @Override
    protected boolean isOpenSwipeBack() {
        return true;
    }

    @Override
    protected void init() {

        setActivityStatus(ACTIVITY_STATUS_LOADING);
    }

}
