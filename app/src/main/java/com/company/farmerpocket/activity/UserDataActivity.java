package com.company.farmerpocket.activity;

import com.company.farmerpocket.R;

/**
 * 个人资料页面
 */
public class UserDataActivity extends AbsBaseActivity {

    @Override
    protected int getLayoutID() {
        return R.layout.activity_user_data;
    }

    @Override
    protected String setToolBarTitle() {
        return "我的资料";
    }

    @Override
    protected void init() {

    }
}
