package com.m520it.testim.common;

import android.app.Application;

import io.rong.imkit.RongIM;

/**
 * Created by Administrator on 2017/7/24 0024.
 */

public class MyApplication extends Application {
    private String token = "";
    private String mId = "";
    @Override
    public void onCreate() {
        super.onCreate();
        RongIM.init(this);

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getId() {
        return mId;
    }
}
