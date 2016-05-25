package com.company.farmerpocket.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.company.farmerpocket.MainActivity;
import com.company.farmerpocket.R;
import com.company.farmerpocket.common.log.Log;
import com.company.farmerpocket.helper.ToastHelper;
import com.company.farmerpocket.qqlogin.BaseUIListener;
import com.company.farmerpocket.qqlogin.Util;
import com.company.farmerpocket.utils.SPUtils;
import com.company.farmerpocket.view.EventLogin;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by hct on 2016/5/24.
 */
public class LoginActivity extends AbsBaseActivity{
    private UMShareAPI mShareAPI;

    private static final String TAG = LoginActivity.class.getName();
    public static String mAppid = "222222";
    @Bind(R.id.tv_login_regist)
    public TextView mUserInfo;
    @Bind(R.id.iv_login_taobao)
    public ImageView mUserLogo;
    private UserInfo mInfo;
    private EditText mEtAppid = null;
    public static Tencent mTencent;
    private static Intent mPrizeIntent = null;
    private static boolean isServerSideLogin = false;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        //Umeng
//        mShareAPI = UMShareAPI.get(this);
        //qq
        initQq();

    }

    private void initQq() {
        if (mTencent == null) {
            mTencent = Tencent.createInstance(mAppid, this);
        }
    }

    @OnClick(R.id.tv_login_login)
    public void userLoginFarmer(){
//        SHARE_MEDIA platform = SHARE_MEDIA.SINA;
//        mShareAPI.doOauthVerify(this, platform, umAuthListener);
        //退出登录Umeng
//        mShareAPI.deleteOauth(this, platform, umdelAuthListener);

    }
    @OnClick(R.id.iv_login_Qq)
    public void userLoginQq(){
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", loginListener);
            isServerSideLogin = true;
            Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
        } else {
            if (!isServerSideLogin) { // SSO模式的登陆，先退出，再进行Server-Side模式登陆
                mTencent.logout(this);
                mTencent.loginServerSide(this, "all", loginListener);
                isServerSideLogin = false;
                Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
                return;
            }
            mTencent.logout(this);
            isServerSideLogin = false;
//            updateUserInfo();
//            updateLoginButton();
        }

    }
    //qq
    private void updateUserInfo() {
        if (mTencent != null && mTencent.isSessionValid()) {
//            ToastHelper.getInstance().showLongToast("开始加载个人信息");
            IUiListener listener = new IUiListener() {

                @Override
                public void onError(UiError e) {

                }

                @Override
                public void onComplete(final Object response) {
//                    Log.i("开始获取个人信息");
                    JSONObject json = (JSONObject)response;
                    android.util.Log.i("log", "成功信息=" + json.toString());
                    if (json.has("figureurl") && json.has("nickname")) {
                        SPUtils.putBoolean(LoginActivity.this, "isQqLogin", true);
                        try {
                            SPUtils.putString(LoginActivity.this, "user_name", json.getString("nickname"));
                            SPUtils.putString(LoginActivity.this, "user_photo_url", json.getString("figureurl_qq_2"));
                            loginSucess(2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onCancel() {

                }
            };
            mInfo = new UserInfo(this, mTencent.getQQToken());
            mInfo.getUserInfo(listener);

        }
    }

    private void loginSucess(int loginWay) {
        // 0: 农夫, 1: 淘宝, 2: qq, 3: 新浪微博
        SPUtils.putBoolean(LoginActivity.this, "isLogin", true);
        EventBus.getDefault().post(new EventLogin(loginWay));
//        startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("isLogin", true));
        LoginActivity.this.finish();
    }

    //qq
    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            if (null == response) {
                Util.showResultDialog(LoginActivity.this, "返回为空", "登录失败");
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                Util.showResultDialog(LoginActivity.this, "返回为空", "登录失败");
                return;
            }
//            Util.showResultDialog(LoginActivity.this, response.toString(), "登录成功");
            // 有奖分享处理
//            handlePrizeShare();
            doComplete((JSONObject)response);
        }

        protected void doComplete(JSONObject values) {

        }

        @Override
        public void onError(UiError e) {
            Util.toastMessage(LoginActivity.this, "onError: " + e.errorDetail);
            Util.dismissDialog();
        }

        @Override
        public void onCancel() {
            Util.toastMessage(LoginActivity.this, "onCancel: ");
            Util.dismissDialog();
            if (isServerSideLogin) {
                isServerSideLogin = false;
            }
        }
    }
    //qq
    public static void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch(Exception e) {
        }
    }
    //qq
    IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
            Log.d("SDKQQAgentPref", "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
//            ToastHelper.getInstance().showLongToast("登录监听");
            initOpenidAndToken(values);
            updateUserInfo();
//            startActivity(new Intent(LoginActivity.this, UserActivity.class));

//            updateLoginButton();
        }
    };
//    private UMAuthListener umAuthListener = new UMAuthListener() {
//        @Override
//        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
//            Toast.makeText( getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
//            Toast.makeText( getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onCancel(SHARE_MEDIA platform, int action) {
//            Toast.makeText( getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
//        }
//    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Umeng
//        mShareAPI.onActivityResult(requestCode, resultCode, data);
        //qq
//        mShareAPI.getPlatformInfo(this, platform, umAuthListener);
        Log.d(TAG, "-->onActivityResult " + requestCode  + " resultCode=" + resultCode);
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode,resultCode,data,loginListener);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
