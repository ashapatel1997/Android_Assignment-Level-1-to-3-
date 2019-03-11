package com.example.asha.chatapplication.data.DataAccessObject;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.asha.chatapplication.data.model.Message;
import com.example.asha.chatapplication.data.model.User;
import com.example.asha.chatapplication.data.model.extraMessage;

/**
 * Created by asha on 07-03-2019.
 */


@Database(entities = {User.class, Message.class,extraMessage.class},version = 11)
public abstract class databaseClass extends RoomDatabase
{
    public abstract userInterfaceDao userInterfaceDao();
    public abstract messageInterfaceDao messageInterfaceDao();
    public abstract extraMessageInterfaceDao extraMessageInterfaceDao();
}