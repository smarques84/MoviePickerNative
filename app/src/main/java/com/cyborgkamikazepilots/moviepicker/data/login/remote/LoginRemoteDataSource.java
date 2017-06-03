package com.cyborgkamikazepilots.moviepicker.data.login.remote;

import com.cyborgkamikazepilots.moviepicker.data.error.ErrorHandler;
import com.cyborgkamikazepilots.moviepicker.data.login.LoginDataSource;
import com.cyborgkamikazepilots.moviepicker.data.login.UserDetails;
import com.cyborgkamikazepilots.moviepicker.data.login.RestClientGenerator;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.Callable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by smarques on 03/06/2017.
 */

public class LoginRemoteDataSource implements LoginDataSource {

    private static LoginRemoteDataSource INSTANCE;

    //private static final int SERVICE_LATENCY_IN_MILLIS = 5000;

    public static LoginRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LoginRemoteDataSource();
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private LoginRemoteDataSource() {

    }


    @Override
    public void authenticateUser(String username, String password, final LoginUserCallback callback) {
        LoginAPI loginAPI = RestClientGenerator.createService(LoginAPI.class);

        LoginRequest loginRequest = new LoginRequest();

        loginRequest.username = username;
        loginRequest.password = password;

        Call<ResponseBody> call = loginAPI.loginUser(loginRequest);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (ErrorHandler.checkForErrors(response)) {

                    Observable<UserDetails> gsonObservable = Observable.fromCallable(new Callable<UserDetails>() {
                        @Override
                        public UserDetails call() throws IOException {
                            Gson gson = new Gson();

                            UserDetails userDetails = gson.fromJson(response.body().string(), UserDetails.class);

                            return userDetails;
                        }
                    });

                    gsonObservable
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<UserDetails>() {

                                @Override
                                public void onCompleted() { }

                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onNext(UserDetails userDetails){
                                    callback.onUserLoginSuccessfull(userDetails);
                                }
                            });
                } else {
                    callback.onUserLoginFailed();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onUserLoginFailed();
            }
        });
    }

    @Override
    public void getUserDetails(GetUserDetailsCallback callback) {
        //Not implemented here
    }

    @Override
    public void logoutUser() {
        //Not implemented here
    }
}
