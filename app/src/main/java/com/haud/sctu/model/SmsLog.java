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

    private boolean isUploaded;

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

    public boolean getIsUploaded() {
        return isUploaded;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public SmsLog(String sid, String content, String date, boolean isUploaded, boolean isSelected) {
        this.sid = sid;
        this.content = content;
        this.date = date;
        this.isUploaded = isUploaded;
        this.isSelected = isSelected;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIsUploaded(boolean isUploaded) {
        this.isUploaded = isUploaded;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}


