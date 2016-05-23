package com.company.farmerpocket.activity;

import android.widget.Toast;

import com.company.farmerpocket.MainActivity;
import com.company.farmerpocket.R;

public class GroupActivity extends AbsBaseActivity {

    @Override
    protected int getLayoutID() {
        return R.layout.activity_group;
    }

    @Override
    protected boolean isOpenToolBar() {
        return false;
    }

    @Override
    protected void init() {

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
                    Toast.makeText(GroupActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                    firstClickTime = System.currentTimeMillis();
                }
            }
        }
    }
}
