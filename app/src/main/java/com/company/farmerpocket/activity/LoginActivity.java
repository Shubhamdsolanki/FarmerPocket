package com.company.farmerpocket.activity;

import com.company.farmerpocket.R;

import butterknife.OnClick;

/**
 * Created by hct on 2016/5/24.
 */
public class LoginActivity extends AbsBaseActivity{
    @Override
    protected int getLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected boolean isOpenToolBar() {
        return false;
    }

    @Override
    protected void init() {

    }
    @OnClick(R.id.tv_login_login)
    public void userLoginQq(){

    }
}
