package com.haud.sctu.dao;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.haud.sctu.model.PhoneLog;

import java.util.List;

@Dao
public interface PhoneDao {

    @Insert
    void insert(PhoneLog phoneLog);

    @Update
    void update(PhoneLog phoneLog);

    @Delete
    void delete(PhoneLog phoneLog);

    @Query("DELETE FROM phone_log_table")
    void deleteAllPhoneLogs();

    @Query("SELECT * FROM phone_log_table")
    LiveData<List<PhoneLog>> getAllPhoneLogs();
}

