package com.company.farmerpocket.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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
import com.company.farmerpocket.weibo.AccessTokenKeeper;
import com.company.farmerpocket.weibo.ConstantsWeibo;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
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

import java.text.SimpleDateFormat;
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
    private AuthInfo mAuthInfo;

    /** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能  */
    private Oauth2AccessToken mAccessToken;

    /** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
    private SsoHandler mSsoHandler;

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
        //Umeng
//        mShareAPI = UMShareAPI.get(this);
        //qq
        initQq();

    }

    private void initWeibo() {
        mAuthInfo = new AuthInfo(this, ConstantsWeibo.APP_KEY,
                ConstantsWeibo.REDIRECT_URL, ConstantsWeibo.SCOPE);
        mSsoHandler = new SsoHandler(LoginActivity.this, mAuthInfo);

        // 从 SharedPreferences 中读取上次已保存好 AccessToken 等信息，
        // 第一次启动本应用，AccessToken 不可用
        mAccessToken = AccessTokenKeeper.readAccessToken(this);
        if (mAccessToken.isSessionValid()) {
            updateTokenView(true);
        }
    }

    /**
     * 微博认证授权回调类。
     * 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用 {@link SsoHandler#authorizeCallBack} 后，
     *    该回调才会被执行。
     * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
     * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
     */
    class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            //从这里获取用户输入的 电话号码信息
            String  phoneNum =  mAccessToken.getPhoneNum();
            if (mAccessToken.isSessionValid()) {
                // 显示 Token
                updateTokenView(false);

                // 保存 Token 到 SharedPreferences
                AccessTokenKeeper.writeAccessToken(LoginActivity.this, mAccessToken);
//                Toast.makeText(LoginActivity.this,
//                        R.string.weibosdk_demo_toast_auth_success, Toast.LENGTH_SHORT).show();
            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String code = values.getString("code");
                String message = getString(R.string.weibosdk_demo_toast_auth_failed);
                if (!TextUtils.isEmpty(code)) {
                    message = message + "\nObtained the code: " + code;
                }
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this,
                    R.string.weibosdk_demo_toast_auth_canceled, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(LoginActivity.this,
                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 显示当前 Token 信息。
     *
     * @param hasExisted 配置文件中是否已存在 token 信息并且合法
     */
    private void updateTokenView(boolean hasExisted) {
        String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                new java.util.Date(mAccessToken.getExpiresTime()));
        String format = getString(R.string.weibosdk_demo_token_to_string_format_1);
//        mTokenText.setText(String.format(format, mAccessToken.getToken(), date));

        String message = String.format(format, mAccessToken.getToken(), date);
        if (hasExisted) {
            message = getString(R.string.weibosdk_demo_token_has_existed) + "\n" + message;
        }
//        mTokenText.setText(message);
    }

    public void weiBoLogin(){
//        // SSO 授权, 仅客户端
//        mSsoHandler.authorizeClientSso(new AuthListener());
//        // 手机短信授权
//        //  title 短信注册页面title，可选，不传时默认为""验证码登录""。此处WeiboAuthListener 对象 listener
//        //可以是和sso 同一个 listener   回调对象 也可以是不同的。开发者根据需要选择
//        mSsoHandler.registerOrLoginByMobile("验证码登录",new AuthListener());
//        // SSO 授权, 仅Web
//        mSsoHandler.authorizeWeb(new AuthListener());
        // SSO 授权, ALL IN ONE   如果手机安装了微博客户端则使用客户端授权,没有则进行网页授权
        mSsoHandler.authorize(new AuthListener());

    }

    private void initQq() {
        if (mTencent == null) {
            mTencent = Tencent.createInstance(mAppid, this);
        }
    }

    @OnClick(R.id.iv_login_Weibo)
    public void userLoginWeiBo(){
        initWeibo();
        weiBoLogin();
//        AccessTokenKeeper.clear(getApplicationContext());
//        mAccessToken = new Oauth2AccessToken();
//        updateTokenView(false);
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

        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }

//        super.onActivityResult(requestCode, resultCode, data);
    }
}
