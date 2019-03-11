package com.example.asha.chatapplication.data.DataAccessObject;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.asha.chatapplication.data.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.DELETE;

/**
 * Created by asha on 07-03-2019.
 */

@Dao
public interface userInterfaceDao
{
    @Insert
    public void addUser(User user);

    @Query("select * from User")
    public List<User> getUsers();

    @Query("delete from User")
    public void deleteUsers();

}
