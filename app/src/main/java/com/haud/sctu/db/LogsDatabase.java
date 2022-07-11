package com.haud.sctu.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.haud.sctu.dao.CallDao;
import com.haud.sctu.model.CallLog;
import com.haud.sctu.dao.SmsDao;
import com.haud.sctu.model.SmsLog;

@Database(entities = {SmsLog.class, CallLog.class}, version = 7)
public abstract class LogsDatabase extends RoomDatabase {

    private static LogsDatabase instance;
    public abstract SmsDao smsDao();
    public abstract CallDao callDao();

    public static synchronized LogsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    LogsDatabase.class, "logsDatabase").fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
