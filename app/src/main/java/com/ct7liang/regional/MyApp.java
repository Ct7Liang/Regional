package com.ct7liang.regional;

import android.app.Application;

import com.ct7liang.address.Regional;

/**
 * Created by Administrator on 2018-03-27.
 *
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Regional.init(this, "com.ct7liang.regional");
        Regional.initTag("address");
    }

}
