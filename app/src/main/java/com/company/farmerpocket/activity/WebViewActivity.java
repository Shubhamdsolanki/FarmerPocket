package com.company.farmerpocket.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.company.farmerpocket.R;

import butterknife.Bind;

public class WebViewActivity extends AbsBaseActivity {

    @Bind(R.id.common_webView)
    WebView webView;

    private Intent mIntent;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void init() {
        mIntent = this.getIntent();
        String title = mIntent.getStringExtra("title");
        setToolBarTitle(title);
        initWebView();
        loadUrl();
        //设置loading
        setActivityStatus(ACTIVITY_STATUS_LOADING);
    }

    @Override
    protected boolean isOpenSwipeBack() {
        return true;
    }

    /**
     * 根据url加载h5
     */
    public void loadUrl() {
        String url = mIntent.getStringExtra("url");
        webView.loadUrl(url);
    }

    /**
     * 开启webView界面
     * @param activity
     * @param link
     */
    public static void startWebViewActivity(Activity activity, String link) {
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra("url", link);
        activity.startActivity(intent);
    }

    /**
     * 开启webView界面
     * @param activity
     * @param link
     * @param title
     */
    public static void startWebViewActivity(Activity activity, String link, String title) {
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra("url", link);
        intent.putExtra("title", title);
        activity.startActivity(intent);
    }

    /**
     * 初始化WebView
     */
    private void initWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);//支持缩放
        webSettings.setDisplayZoomControls(false); //隐藏缩放按钮
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    return false;
                }
                // 可以打开h5页面的电话、邮件等
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                startActivity(intent);
                return true;
            }

            @Override
            public void onPageStarted(final WebView view, String url, Bitmap favicon) {
                //这里可以显示loading
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //这里可以取消loading
                setActivityStatus(ACTIVITY_STATUS_SUCCESS);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (webView != null){
            if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                webView.goBack();// 返回web的前一个页面
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
