package com.cyborgkamikazepilots.moviepicker.data.login.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by smarques on 03/06/2017.
 */

public class LoginRequest {

    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("password")
    @Expose
    public String password;

}
