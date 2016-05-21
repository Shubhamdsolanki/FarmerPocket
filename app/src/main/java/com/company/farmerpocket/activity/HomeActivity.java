package com.company.farmerpocket.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.company.farmerpocket.MainActivity;
import com.company.farmerpocket.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }

    /**
     * 首页侧滑菜单按钮点击事件
     */
    @OnClick(R.id.home_menu)
    public void homeMeunClick() {
        if (MainActivity.slidingMenu != null) MainActivity.slidingMenu.toggle();
    }

    private long firstClickTime;

    @Override
    public void onBackPressed() {
        if (firstClickTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(HomeActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
        }
        firstClickTime = System.currentTimeMillis();
    }

}
