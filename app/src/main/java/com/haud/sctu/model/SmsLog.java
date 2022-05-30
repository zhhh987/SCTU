package com.haud.sctu.model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sms_log_table")
public class SmsLog {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String sid;

    private String content;

    private String date;

    private boolean isSelected;

    public int getId() {
        return id;
    }

    public String getSid() {
        return sid;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public SmsLog(String sid, String content, String date, boolean isSelected) {
        this.sid = sid;
        this.content = content;
        this.date = date;
        this.isSelected = isSelected;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}


