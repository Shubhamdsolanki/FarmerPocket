package com.company.farmerpocket.activity;

import com.company.farmerpocket.R;

/**
 * 帮助中心
 */
public class HelpActivity extends AbsBaseActivity {

    @Override
    protected int getLayoutID() {
        return R.layout.activity_help;
    }

    @Override
    protected String setToolBarTitle() {
        return "帮助中心";
    }

    @Override
    protected boolean isOpenSwipeBack() {
        return true;
    }

    @Override
    protected void init() {

    }

}
