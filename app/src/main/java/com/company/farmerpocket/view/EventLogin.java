package com.company.farmerpocket.view;

/**
 * Created by hct on 2016/5/25.
 */
public class EventLogin {
    // 0: 农夫, 1: 淘宝, 2: qq, 3: 新浪微博
    int loginway;
    public EventLogin(int loginway){
        this.loginway = loginway;
    }

    public int getLoginway() {
        return loginway;
    }
}
