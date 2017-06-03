package com.cyborgkamikazepilots.moviepicker.data.error;

import android.util.Log;
import android.widget.Toast;

import com.cyborgkamikazepilots.moviepicker.MyApplication;
import com.cyborgkamikazepilots.moviepicker.data.events.OnTokenInvalid;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by smarques on 03/06/2017.
 */

public class ErrorHandler {

    private final static String TAG = ErrorHandler.class.getName();

    private static Toast mToast;

    public static boolean checkForErrors(Response<ResponseBody> response) {
        if (response.code() == 200) {
            return true;
        } else {

            //Check if we are not authorized to access the server
            if (response.code() == 401) {
                EventBus.getDefault().post(new OnTokenInvalid());
            }

            Log.i(TAG, "API Error: " + response.code());
            showAToast("API Error: " + response.code());
            return false;
        }
    }

    public static void showAToast (String message){
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(MyApplication.getAppContext(), message, Toast.LENGTH_SHORT);
        mToast.show();
    }
}
