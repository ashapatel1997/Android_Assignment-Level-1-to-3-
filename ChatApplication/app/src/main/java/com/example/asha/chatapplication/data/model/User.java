package com.example.asha.chatapplication.data.model;

/**
 * Created by asha on 05-03-2019.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;




public class User {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("token")
    @Expose
    private String token;

    public User(String name) {
        this.name = name;
    }

    public User(Integer id) {
        this.id = id;
    }

    public User(Integer id, String name, String token) {
        this.id = id;
        this.name = name;
        this.token = token;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

