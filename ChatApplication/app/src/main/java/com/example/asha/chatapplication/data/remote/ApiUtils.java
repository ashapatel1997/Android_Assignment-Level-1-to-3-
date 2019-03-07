package com.example.asha.chatapplication.data.remote;

/**
 * Created by asha on 05-03-2019.
 */

public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "https://chat.promactinfo.com/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
