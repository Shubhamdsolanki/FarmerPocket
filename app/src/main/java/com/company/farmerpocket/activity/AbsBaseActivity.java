package com.company.farmerpocket.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.company.farmerpocket.MainActivity;
import com.company.farmerpocket.R;
import com.company.farmerpocket.common.logger.Logger;

import butterknife.ButterKnife;

/**
 * Created by GHY on 2016/5/22.
 * Activity基类
 */
public abstract class AbsBaseActivity extends AppCompatActivity {

    private final String TAG = "AbsBaseActivity";
    private static final int NO_LAYOUT = 0;

    /**
     * 根布局View
     */
    private View mRootFrameView;
    /**
     * 根布局FrameLayout
     */
    private FrameLayout mRootFrameLayout;
    /**
     * 子类添加的View
     */
    private View mSonView;

    /**
     * 自定义toolbar布局
     */
    private RelativeLayout toolbarLayout;

    /**
     * 获取布局ID
     *
     * @return
     */
    protected abstract int getLayoutID();

    /**
     * 初始化
     */
    protected abstract void init();


    /**
     * 是否打开ToolBar
     * 默认为true
     *
     * @return
     */
    protected boolean isOpenToolBar() {
        return true;
    }


    /**
     * 设置toolbar标题
     *
     * @return
     */
    protected String setToolBarTitle() {
        return "";
    }


    /**
     * 设置toolBar右侧图片
     *
     * @return
     */
    protected int setToolBarRightIv() {
        return 0;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //保持屏幕竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        int id = getLayoutID();
        //加载根布局
        mRootFrameView = getLayoutInflater().inflate(R.layout.abs_base_activity, null);
        mRootFrameLayout = (FrameLayout) mRootFrameView.findViewById(R.id.abs_base_frame_layout);
        //设置布局
        setContentView(mRootFrameView);
        //加载子类布局
        if (id != NO_LAYOUT) {
            mSonView = getLayoutInflater().inflate(id, null);
            mRootFrameLayout.addView(mSonView);
        } else {
            Logger.e("mSonView == null");
        }
        //初始化ToolBar
        initToolBar();
        //初始化ButterKnife
        ButterKnife.bind(this);
        //初始化
        init();
    }


    /**
     * 初始化自定义的ToolBar
     */
    private void initToolBar() {
        toolbarLayout = (RelativeLayout) findViewById(R.id.base_toolbar);
        ImageView backImage = (ImageView) findViewById(R.id.iv_toolbar_back);
        TextView title = (TextView) findViewById(R.id.tv_toolbar_title);
        ImageView rightImage = (ImageView) findViewById(R.id.iv_toolbar_right);
        if (backImage != null)
            backImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AbsBaseActivity.this.finish();
                }
            });
        if (title != null) title.setText(setToolBarTitle());
        if (rightImage != null) {
            if (setToolBarRightIv() != 0) {
                rightImage.setVisibility(View.VISIBLE);
                rightImage.setImageResource(setToolBarRightIv());
                rightImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (rightIvClickListener != null)
                            rightIvClickListener.onToolBarRightIvClick();
                    }
                });
            } else {
                rightImage.setVisibility(View.GONE);
            }
        }
        if (!isOpenToolBar()) {
            toolbarLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 开启activity
     *
     * @param context
     * @param activity
     */
    public void startActivity(Context context, Class<?> activity) {
        Intent intent = new Intent(context, activity);
        startActivity(intent);
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
                    Toast.makeText(AbsBaseActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                    firstClickTime = System.currentTimeMillis();
                }
            }
        }
    }


    /**
     * toolBar右侧图片点击监听
     */
    private ToolBarRightIvClickListener rightIvClickListener;

    public void setOnToolBarRightIvClickListener(ToolBarRightIvClickListener listener) {
        rightIvClickListener = listener;
    }


    public interface ToolBarRightIvClickListener {
        void onToolBarRightIvClick();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
