package com.company.farmerpocket.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.company.farmerpocket.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    private String web_home_url = "http://114.215.95.55:9995/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_go_web_view)
    public void goWebViewClick(){
        WebViewActivity.startWebViewActivity(this,web_home_url);
    }
}
