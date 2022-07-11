package com.haud.sctu.dao;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.haud.sctu.model.CallLog;
import com.haud.sctu.model.SmsLog;

import java.util.List;

@Dao
public interface CallDao {

    @Insert
    void insert(CallLog callLog);

    @Update
    void update(CallLog callLog);

    @Delete
    void delete(CallLog callLog);

    @Query("DELETE FROM call_log_table")
    void deleteAllCallLogs();

    @Query("SELECT * FROM call_log_table")
    LiveData<List<CallLog>> getAllCallLogs();
}

