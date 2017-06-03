package com.cyborgkamikazepilots.moviepicker.data.login;

/**
 * Created by smarques on 03/06/2017.
 */

public interface LoginDataSource {

    interface LoginUserCallback {
        void onUserLoginSuccessfull(UserDetails userDetails);
        void onUserLoginFailed();
    }

    interface GetUserDetailsCallback {
        void onUserDetailsLoaded(UserDetails userDetails);
        void onUserDetailsNotAvailable();
    }

    interface RemoteDataSource {
        void authenticateUser(String username, String password, LoginUserCallback callback);
    }

    void authenticateUser(String username, String password, LoginUserCallback callback);

    void getUserDetails(GetUserDetailsCallback callback);

    void logoutUser();
}
