package com.example.asha.chatapplication.data.DataAccessObject;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.asha.chatapplication.data.model.extraMessage;

import java.util.List;

/**
 * Created by asha on 07-03-2019.
 */
@Dao
public interface extraMessageInterfaceDao {
    @Insert
    public void addExtraMessage(extraMessage message);

    @Query("select * from extraMessage where toUserId=:uId or fromUserId=:uId")
    public List<extraMessage> getExtraMessage(Integer uId);

    @Query("delete from extraMessage where toUserId=:uId or fromUserId=:uId")
    public void deleteExtraMessage(Integer uId);

    @Query("SELECT COUNT(*) FROM extraMessage where toUserId=:uId or fromUserId=:uId")
    int getCount(Integer uId);

    @Query("delete from extraMessage")
    public void deleteExtra();
}
