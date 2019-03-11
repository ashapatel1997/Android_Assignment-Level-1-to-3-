package com.example.asha.chatapplication.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by asha on 05-03-2019.
 */

public class RetrofitClient {




    private static Retrofit retrofit = null;



    public static Retrofit getClient(String baseUrl) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.HOURS)
                .readTimeout(100,TimeUnit.HOURS).build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }





}

