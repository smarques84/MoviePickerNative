package com.cyborgkamikazepilots.moviepicker.data.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by smarques on 03/06/2017.
 */

public class UserDetails {

    @SerializedName("token")
    @Expose
    public String token;

}
