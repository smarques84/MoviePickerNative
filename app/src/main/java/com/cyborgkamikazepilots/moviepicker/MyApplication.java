package com.cyborgkamikazepilots.moviepicker;

import android.app.Application;
import android.content.Context;

/**
 * Created by smarques on 03/06/2017.
 */

public class MyApplication extends Application {

    public final static String BASE_URL = "https://gentle-depths-95165.herokuapp.com/";

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.sContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.sContext;
    }

}
