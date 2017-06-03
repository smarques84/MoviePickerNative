package com.cyborgkamikazepilots.moviepicker.login;

import java.util.ArrayList;

/**
 * Created by smarques on 03/06/2017.
 */

public interface LoginContract {
    interface View {
        void showMainScreen();
        void showProgressBar();
        void showOnLoginFail();
    }

    interface Presenter {
        void onStart();
        void onStop();
        void authenticateUser(String username, String password);
    }
}
