package com.company.farmerpocket.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.company.farmerpocket.R;
import com.company.farmerpocket.helper.ToastHelper;
import com.company.farmerpocket.utils.SPUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 设置页面
 */
public class SettingActivity extends AbsBaseActivity {

    /**
     * 签到提醒图片
     */
    @Bind(R.id.iv_qiandao_off)
    ImageView ivQianOff;
    @Bind(R.id.iv_qiandao_on)
    ImageView ivQianOn;

    /**
     * 是否打开签到提醒
     * sp key
     */
    private final String isOpenQian = "IS_OPEN_QIAN";

    @Override
    protected int getLayoutID() {
        return R.layout.activity_setting;
    }

    @Override
    protected String setToolBarTitle() {
        return "设置";
    }

    @Override
    protected boolean isOpenSwipeBack() {
        return true;
    }

    @Override
    protected void init() {
        //获取是否开启了签到提醒
        boolean isOpen = SPUtils.getBoolean(this,isOpenQian);
        if (isOpen){
            ivQianOff.setVisibility(View.GONE);
            ivQianOn.setVisibility(View.VISIBLE);
        }else {
            ivQianOff.setVisibility(View.VISIBLE);
            ivQianOn.setVisibility(View.GONE);
        }
    }

    /**
     * 签到提醒
     */
    @OnClick(R.id.layout_qiandao)
    public void clickQiandao(){
        if (ivQianOff.getVisibility() == View.VISIBLE){
            ivQianOff.setVisibility(View.GONE);
            ivQianOn.setVisibility(View.VISIBLE);
            //打开签到提醒
            SPUtils.putBoolean(this,isOpenQian,true);
        }else {
            ivQianOff.setVisibility(View.VISIBLE);
            ivQianOn.setVisibility(View.GONE);
            //关闭签到提醒
            SPUtils.putBoolean(this,isOpenQian,false);
        }
    }

    /**
     * 清除缓存
     */
    @OnClick(R.id.tv_clear_cache)
    public void clickClearCache(){
        ToastHelper.getInstance().showToast("清除缓存");
    }

    /**
     * 喜欢打分
     */
    @OnClick(R.id.layout_like)
    public void clickLike(){
        Uri uri = Uri.parse("market://details?id="+getPackageName());
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
