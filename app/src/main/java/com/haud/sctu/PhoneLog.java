package com.haud.sctu;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "phone_log_table")
public class PhoneLog {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String sid;

    private String duration;

    private String simCardNumber;

    private boolean isSelected;

    private boolean isMissedCall;

    public int getId() {
        return id;
    }

    public String getSid() {
        return sid;
    }

    public String getDuration() {
        return duration;
    }

    public String getSimCardNumber() {
        return simCardNumber;
    }

    public boolean getIsMissedCall() {
        return isMissedCall;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public PhoneLog(String sid, String duration, String simCardNumber, boolean isMissedCall, boolean isSelected) {
        this.simCardNumber = simCardNumber;
        this.sid = sid;
        this.duration = duration;
        this.isMissedCall = isMissedCall;
        this.isSelected = isSelected;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

}