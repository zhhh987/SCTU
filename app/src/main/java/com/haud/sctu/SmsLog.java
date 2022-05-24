package com.haud.sctu;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sms_log_table")
public class SmsLog {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String date;

    private String time;

    private String info;

    private boolean isSelected;

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getInfo() {
        return info;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public SmsLog(String date, String time, String info, boolean isSelected) {
        this.date = date;
        this.time = time;
        this.info = info;
        this.isSelected = isSelected;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}


