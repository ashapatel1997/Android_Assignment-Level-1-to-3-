package com.example.asha.chatapplication.data.remote;

import android.widget.Toast;

import com.example.asha.chatapplication.data.model.Message;
import com.example.asha.chatapplication.data.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by asha on 05-03-2019.
 */

public interface APIService
{
    //user login
    @Headers({"Content-Type:application/json"})
    @POST("api/user/login")
    Call<User> saveUser(@Body User name);

    //fetch all users
    @GET("api/user")
    Call<List<User>> getUsers(@Header("Authorization") String authToken);

    //fetch messages of user
    @Headers({"Content-Type:application/json"})
    @GET("api/chat/{id}") //get Id of specific user on click
    Call<List<Message>> getMessages(@Header("Authorization") String authToken, @Path("id") Integer id);

    //send chat message to user
    @Headers({"Content-Type:application/json"})
    @POST("api/chat")
    Call<Void> sendMessage(@Header("Authorization") String authToken,@Body Message message);


}
