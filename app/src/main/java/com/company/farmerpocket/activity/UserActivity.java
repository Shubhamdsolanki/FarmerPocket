package com.company.farmerpocket.activity;

import com.company.farmerpocket.R;
import com.company.farmerpocket.helper.ToastHelper;

import butterknife.OnClick;

/**
 * 个人中心页面
 */
public class UserActivity extends AbsBaseActivity {

    @Override
    protected int getLayoutID() {
        return R.layout.activity_user;
    }

    @Override
    protected boolean isOpenToolBar() {
        return false;
    }

    @Override
    protected void init() {

    }


    /**
     * 跳转用户资料页面
     */
    @OnClick(R.id.user_data_layout)
    public void userData(){
        startActivity(this,UserDataActivity.class);
    }

    /**
     * 跳转设置页面
     */
    @OnClick(R.id.user_setting)
    public void userSetting() {
        startActivity(this, SettingActivity.class);
    }

    /**
     * 我的积分点击事件
     */
    @OnClick(R.id.user_jifen_layout)
    public void userClickJifen() {
        ToastHelper.getInstance().showToast("我的积分");
    }

    /**
     * 我的消息点击事件
     */
    @OnClick(R.id.user_message_layout)
    public void userClickMessage() {
        ToastHelper.getInstance().showToast("我的消息");
    }

    /**
     * 收货地址点击事件
     */
    @OnClick(R.id.user_address_layout)
    public void userClickAddress() {
        ToastHelper.getInstance().showToast("收货地址");
    }

    /**
     * 帮助中心点击事件
     */
    @OnClick(R.id.user_help_layout)
    public void userClickHelp() {
        ToastHelper.getInstance().showToast("帮助中心");
    }

    /**
     * 意见反馈点击事件
     */
    @OnClick(R.id.user_advice_layout)
    public void userClickAdvice() {
        ToastHelper.getInstance().showToast("意见反馈");
    }

}
