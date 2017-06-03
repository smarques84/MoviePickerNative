package com.cyborgkamikazepilots.moviepicker.login;

import android.support.annotation.NonNull;


import com.cyborgkamikazepilots.moviepicker.data.events.OnTokenInvalid;
import com.cyborgkamikazepilots.moviepicker.data.login.LoginDataSource;
import com.cyborgkamikazepilots.moviepicker.data.login.LoginRepository;
import com.cyborgkamikazepilots.moviepicker.data.login.UserDetails;
import com.cyborgkamikazepilots.moviepicker.data.login.remote.LoginRemoteDataSource;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by smarques on 03/06/2017.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;
    private final LoginRepository mLoginRepository;

    public LoginPresenter(@NonNull LoginContract.View loginView) {
        mLoginRepository = LoginRepository.getInstance(LoginRemoteDataSource.getInstance());
        mView = loginView;
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void authenticateUser(String username, String password) {

        mView.showProgressBar();

        mLoginRepository.authenticateUser(username, password, new LoginDataSource.LoginUserCallback() {
            @Override
            public void onUserLoginSuccessfull(UserDetails userDetails) {
                mView.showMainScreen();
            }

            @Override
            public void onUserLoginFailed() {
                mView.showOnLoginFail();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(OnTokenInvalid event) {
        //
    }
}
