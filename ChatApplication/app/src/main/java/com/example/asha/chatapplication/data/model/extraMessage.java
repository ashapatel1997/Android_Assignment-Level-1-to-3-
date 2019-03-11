package com.example.asha.chatapplication.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by asha on 07-03-2019.
 */
@Entity
public class extraMessage
{


    @PrimaryKey (autoGenerate = true)
    @NonNull
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


    public extraMessage(String message, Integer toUserId) {
        this.message = message;
        this.toUserId = toUserId;
    }


    public extraMessage(@NonNull Integer id, String message, Integer fromUserId, Integer toUserId, String createdDateTime) {
        this.id = id;
        this.message = message;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.createdDateTime = createdDateTime;
    }

    public extraMessage() {
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


