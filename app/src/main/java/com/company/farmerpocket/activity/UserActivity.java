package com.company.farmerpocket.activity;

import android.widget.Toast;

import com.company.farmerpocket.MainActivity;
import com.company.farmerpocket.R;
import com.company.farmerpocket.component.SweetAlert.SweetAlertDialog;
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
    @OnClick(R.id.user_layout_login)
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
        startActivity(this,JifenActivity.class);
    }

    /**
     * 我的消息点击事件
     */
    @OnClick(R.id.user_message_layout)
    public void userClickMessage() {
        startActivity(this,MessageActivity.class);
    }

    /**
     * 收货地址点击事件
     */
    @OnClick(R.id.user_address_layout)
    public void userClickAddress() {
        ToastHelper.getInstance().showToast("收货地址");
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Good job!")
                .setContentText("You clicked the button!")
                .show();
    }

    /**
     * 帮助中心点击事件
     */
    @OnClick(R.id.user_help_layout)
    public void userClickHelp() {
        startActivity(this,HelpActivity.class);
    }

    /**
     * 意见反馈点击事件
     */
    @OnClick(R.id.user_advice_layout)
    public void userClickAdvice() {
        startActivity(this,AdviceActivity.class);
    }

    /**
     * 登录点击事件
     */
    @OnClick(R.id.iv_user_login)
    public void userClickLogin(){startActivity(this, LoginActivity.class);}
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
                    Toast.makeText(UserActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                    firstClickTime = System.currentTimeMillis();
                }
            }
        }
    }

}
