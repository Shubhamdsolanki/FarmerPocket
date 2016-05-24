package com.company.farmerpocket.component.refreshload.pullableview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class PullableScrollViewHome extends ScrollView implements Pullable {

    public PullableScrollViewHome(Context context) {
        super(context);
    }

    public PullableScrollViewHome(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableScrollViewHome(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canPullDown() {
        if (getScrollY() == 0)
            return true;
        else
            return false;
    }

    @Override
    public boolean canPullUp() {
//        if (getScrollY() >= (getChildAt(0).getHeight() - getMeasuredHeight()))
//            return true;
//        else
//            return false;
        //首页暂不支持上拉，return false
        return false;
    }

}
