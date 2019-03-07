package com.example.asha.chatapplication.data.model;

/**
 * Created by asha on 06-03-2019.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("fromUserId")
    @Expose
    private Integer fromUserId;
    @SerializedName("toUserId")
    @Expose
    private Integer toUserId;

    @SerializedName("createdDateTime")
    @Expose
    private String createdDateTime;


    public Message(String message, Integer toUserId) {
        this.message = message;
        this.toUserId = toUserId;
    }

    public Message() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

}
