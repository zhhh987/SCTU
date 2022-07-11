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

    @Query("SELECT *, MAX(received) FROM sms_log_table GROUP BY oa ORDER BY MAX(received) DESC")
    LiveData<List<SmsLog>> getLatestSmsLogsByOa();

    @Query("SELECT * FROM sms_log_table WHERE oa = :selectedOa ORDER BY received DESC")
    LiveData<List<SmsLog>> getAllSmsByOa(String selectedOa);

    @Query("SELECT * FROM sms_log_table WHERE body OR oa LIKE :input ORDER BY received DESC")
    LiveData<List<SmsLog>> getSmsSearchResults(String input);

}
