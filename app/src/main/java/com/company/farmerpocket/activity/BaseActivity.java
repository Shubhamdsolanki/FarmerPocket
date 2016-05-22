package com.company.farmerpocket.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.company.farmerpocket.MainActivity;

/**
 * Created by HY on 2016/5/22.
 * BaseActivity 公共的逻辑可以放这里
 */
public class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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
                    Toast.makeText(BaseActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                    firstClickTime = System.currentTimeMillis();
                }
            }
        }
    }
}
