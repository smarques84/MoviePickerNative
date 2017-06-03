package com.cyborgkamikazepilots.moviepicker.data.login;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.cyborgkamikazepilots.moviepicker.MyApplication;
import com.cyborgkamikazepilots.moviepicker.data.events.OnTokenInvalid;
import com.cyborgkamikazepilots.moviepicker.data.login.remote.LoginRemoteDataSource;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by smarques on 03/06/2017.
 */

public class LoginRepository implements LoginDataSource {

    public static final String TAG = LoginRepository.class.getName();

    public static final String PREF_KEY_USER_TOKEN = "pref_key_user_token";

    private static LoginRepository sRepository = null;

    public static UserDetails mUserDetails;

    private final LoginRemoteDataSource mLoginRemoteDataSource;

    // Prevent direct instantiation.
    private LoginRepository(@NonNull LoginRemoteDataSource pendingDocumentsRemoteDataSource) {
        mLoginRemoteDataSource = pendingDocumentsRemoteDataSource;
    }

    public synchronized static LoginRepository getInstance(@NonNull LoginRemoteDataSource loginRemoteDataSource) {
        if (null == sRepository) {
            sRepository = new LoginRepository(loginRemoteDataSource);
        }
        return sRepository;
    }

    @Override
    public void authenticateUser(String username, String password, final LoginUserCallback callback) {
        mLoginRemoteDataSource.authenticateUser(username, password, new LoginUserCallback() {
            @Override
            public void onUserLoginSuccessfull(UserDetails userDetails) {
                LoginRepository.this.saveUserDetails(userDetails);
                callback.onUserLoginSuccessfull(userDetails);
            }

            @Override
            public void onUserLoginFailed() {
                callback.onUserLoginFailed();
            }
        });
    }

    @Override
    public void getUserDetails(GetUserDetailsCallback callback) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        String token = sharedPreferences.getString(PREF_KEY_USER_TOKEN, "");

        if (TextUtils.isEmpty(token)) {
            mUserDetails = null;
            callback.onUserDetailsNotAvailable();
        } else {
            UserDetails userDetails = new UserDetails();
            userDetails.token = token;
            mUserDetails = userDetails;
            callback.onUserDetailsLoaded(userDetails);
        }
    }

    @Override
    public void logoutUser() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_KEY_USER_TOKEN, "");
        editor.apply();
        mUserDetails = null;
        EventBus.getDefault().post(new OnTokenInvalid());
    }

    private synchronized void saveUserDetails(UserDetails userDetails) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_KEY_USER_TOKEN, userDetails.token);
        editor.apply();
        mUserDetails = userDetails;
    }
}
