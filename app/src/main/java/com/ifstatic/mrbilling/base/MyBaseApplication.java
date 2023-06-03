package com.ifstatic.mrbilling.base;

import android.app.Application;

public class MyBaseApplication extends Application {

    private static MyBaseApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static MyBaseApplication getApplication(){
        return application;
    }
}
