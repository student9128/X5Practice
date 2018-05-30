package com.practice.kevin.x5practice;

import android.app.Application;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by <a href="http://blog.csdn.net/student9128">Kevin</a> on 2018/5/29.
 * <h3>
 * Describe:
 * <h3/>
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        QbSdk.PreInitCallback callback = new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {

            }

            @Override
            public void onViewInitFinished(boolean b) {
                Log.d("BaseApplication", "onViewInitFinished: 初始化完成");
            }
        };
        QbSdk.initX5Environment(getApplicationContext(), callback);
    }
}
