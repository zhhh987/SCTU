package com.haud.sctu.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomDatabase;
import androidx.room.Update;

import com.haud.sctu.model.SmsLog;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface SmsDao {

    @Insert
    void insert(SmsLog smsLog);

    @Update
    void update(SmsLog smsLog);

    @Delete
    void delete(SmsLog smsLog);

    @Query("DELETE FROM sms_log_table WHERE oa = :selectedOa")
    void deleteAllSmsLogsByOa(String selectedOa);

    @Query("SELECT * FROM sms_log_table ORDER BY received DESC")
    LiveData<List<SmsLog>> getAllSms();

    @Query("SELECT * FROM sms_log_table WHERE oa = :selectedOa ORDER BY received DESC")
    LiveData<List<SmsLog>> getAllSmsByOa(String selectedOa);

    @Query ("SELECT COUNT(*) FROM sms_log_table WHERE oa = :selectedOa")
    LiveData<Integer> getSmsByOaCount(String selectedOa);


    @Query("SELECT * FROM sms_log_table WHERE body OR oa LIKE :input ORDER BY received DESC")
    LiveData<List<SmsLog>> getSmsSearchResults(String input);

}
