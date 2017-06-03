package com.cyborgkamikazepilots.moviepicker.data.login.remote;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by smarques on 03/06/2017.
 */

public interface LoginAPI {
//    @FormUrlEncoded
//    @POST("auth/login")
//    Call<ResponseBody> loginUser(
//            @Field("email") String email,
//            @Field("password") String password
//    );

    @POST("users/login")
    Call<ResponseBody> loginUser(
            @Body LoginRequest loginRequest
    );
}
