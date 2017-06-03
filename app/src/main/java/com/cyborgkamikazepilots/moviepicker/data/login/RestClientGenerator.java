package com.cyborgkamikazepilots.moviepicker.data.login;

import com.cyborgkamikazepilots.moviepicker.MyApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by smarques on 03/06/2017.
 */

public class RestClientGenerator {

    public synchronized static <S> S createService(Class<S> serviceClass) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(addHttpInterceptor());

        httpClient.readTimeout(60, TimeUnit.SECONDS);

        OkHttpClient client = httpClient.build();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonBuilder.serializeNulls();
        Gson gson = gsonBuilder.create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(MyApplication.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(serviceClass);
    }

    private static HttpLoggingInterceptor addHttpInterceptor() {
        HttpLoggingInterceptor httpInterceptor = new HttpLoggingInterceptor();
        httpInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpInterceptor;
    }
}
