package com.example.asha.chatapplication.data.DataAccessObject;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.asha.chatapplication.data.model.Message;

import java.util.List;

/**
 * Created by asha on 07-03-2019.
 */
@Dao
public interface messageInterfaceDao
{
    @Insert
    public void addMessage(Message message);

    @Query("select * from Message where toUserId=:uId or fromUserId=:uId")
    public List<Message> getMessages(Integer uId);

    @Query("delete from Message where toUserId=:uId or fromUserId=:uId")
    public void deleteMessage(Integer uId);

    @Query("SELECT COUNT(*) FROM Message")
    int getCount();

    @Query("delete from Message")
    public void delete();
}
