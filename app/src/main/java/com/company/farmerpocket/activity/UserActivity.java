package com.company.farmerpocket.activity;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.company.farmerpocket.MainActivity;
import com.company.farmerpocket.R;
import com.company.farmerpocket.component.SweetAlert.SweetAlertDialog;
import com.company.farmerpocket.helper.ImageHelper;
import com.company.farmerpocket.helper.ToastHelper;
import com.company.farmerpocket.utils.SPUtils;
import com.company.farmerpocket.view.CircleImageView;
import com.company.farmerpocket.view.EventLogin;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 个人中心页面
 */
public class UserActivity extends AbsBaseActivity {
    @Bind(R.id.user_layout_login)
    public LinearLayout ll_logined;
    @Bind(R.id.user_layout_no_login)
    public LinearLayout ll_no_logined;
    @Bind(R.id.iv_user_head)
    public CircleImageView civ_user_head;
    @Bind(R.id.tv_user_name)
    public TextView tv_user_name;
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
        setLoginInfo();
    }

    private void setLoginInfo() {
        EventBus.getDefault().register(this);
        Log.i("log",SPUtils.getString(UserActivity.this, "user_photo_url", "") + "名字=" + SPUtils.getString(UserActivity.this, "user_name"));
//        if (SPUtils.getString(UserActivity.this, "user_name") == null){

//        }
        if (SPUtils.getBoolean(UserActivity.this, "isLogin", false)){
            ll_logined.setVisibility(View.VISIBLE);
            ll_no_logined.setVisibility(View.GONE);
            qqLogin();
        }else {
            ll_logined.setVisibility(View.GONE);
            ll_no_logined.setVisibility(View.VISIBLE);
        }
        SPUtils.putBoolean(UserActivity.this, "isLogin", false);
    }

    private void qqLogin() {
        Log.i("log", "结果3=" + SPUtils.getBoolean(UserActivity.this, "isQqLogin", false));
        if (SPUtils.getBoolean(UserActivity.this, "isQqLogin", false)){
            Log.i("log", "结果4=" + SPUtils.getString(UserActivity.this, "user_name"));
            tv_user_name.setText(SPUtils.getString(UserActivity.this, "user_name"));
            ImageHelper.getInstance().loadImage(civ_user_head, SPUtils.getString(UserActivity.this, "user_photo_url", ""));
        }
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
                .setTitleText("干的漂亮")
                .setContentText("该怎么夸奖你才好呢？")
                .setConfirmText("不用啦")
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

    public void onEventMainThread(EventLogin event) {
        Log.i("log", "结果=" + event.getLoginway());
        ll_logined.setVisibility(View.VISIBLE);
        ll_no_logined.setVisibility(View.GONE);
        switch (event.getLoginway()){
            case 0:
                break;
            case 1:
                break;
            case 2:
                Log.i("log", "结果2=" + event.getLoginway());
                qqLogin();
                break;
            case 3:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
