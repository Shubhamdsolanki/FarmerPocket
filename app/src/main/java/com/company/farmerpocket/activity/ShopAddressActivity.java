package com.company.farmerpocket.activity;

import com.company.farmerpocket.R;

/**
 * 收货地址
 */
public class ShopAddressActivity extends AbsBaseActivity {

    @Override
    protected int getLayoutID() {
        return R.layout.activity_shop_address;
    }

    @Override
    protected String setToolBarTitle() {
        return "收货地址";
    }

    @Override
    protected boolean isOpenSwipeBack() {
        return true;
    }

    @Override
    protected void init() {

    }

}
