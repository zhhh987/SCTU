package com.haud.sctu.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "call_log_table")
public class CallLog {
    @PrimaryKey //(autoGenerate = true)
    @NonNull
    private int id;

    @NonNull
    private String evenUuid;

    @NonNull
    private String callDataIdentifier;

    @NonNull
    private String sid;

    @NonNull
    private String callType;

    @NonNull
    private long startDatetime;

    @NonNull
    private long endDatetime;

    @NonNull
    private String destinationPhoneAccountHandleId;

    @NonNull
    private String destinationSimIccid;

    private String destinationSimImsi;

    @NonNull
    private String destinationMccmnc;

    private long answeredStartDatetime;

    private long answeredEndDatetime;

    private int deviceCallLogId;

    private int deviceCallLogDuration;

    private String deviceCallLogRecordingFileName;

    @NonNull
    private boolean isCallLogUploaded;

    @NonNull
    private boolean isCallRecordingFileUploaded;

    @NonNull
    private boolean isSelected;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getEvenUuid() {
        return evenUuid;
    }

    public void setEvenUuid(@NonNull String evenUuid) {
        this.evenUuid = evenUuid;
    }

    @NonNull
    public String getCallDataIdentifier() {
        return callDataIdentifier;
    }

    public void setCallDataIdentifier(@NonNull String callDataIdentifier) {
        this.callDataIdentifier = callDataIdentifier;
    }

    @NonNull
    public String getSid() {
        return sid;
    }

    public void setSid(@NonNull String sid) {
        this.sid = sid;
    }

    @NonNull
    public String getCallType() {
        return callType;
    }

    public void setCallType(@NonNull String callType) {
        this.callType = callType;
    }

    public long getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(long startDatetime) {
        this.startDatetime = startDatetime;
    }

    public long getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(long endDatetime) {
        this.endDatetime = endDatetime;
    }

    @NonNull
    public String getDestinationPhoneAccountHandleId() {
        return destinationPhoneAccountHandleId;
    }

    public void setDestinationPhoneAccountHandleId(@NonNull String destinationPhoneAccountHandleId) {
        this.destinationPhoneAccountHandleId = destinationPhoneAccountHandleId;
    }

    @NonNull
    public String getDestinationSimIccid() {
        return destinationSimIccid;
    }

    public void setDestinationSimIccid(@NonNull String destinationSimIccid) {
        this.destinationSimIccid = destinationSimIccid;
    }

    public String getDestinationSimImsi() {
        return destinationSimImsi;
    }

    public void setDestinationSimImsi(String destinationSimImsi) {
        this.destinationSimImsi = destinationSimImsi;
    }

    @NonNull
    public String getDestinationMccmnc() {
        return destinationMccmnc;
    }

    public void setDestinationMccmnc(@NonNull String destinationMccmnc) {
        this.destinationMccmnc = destinationMccmnc;
    }

    public long getAnsweredStartDatetime() {
        return answeredStartDatetime;
    }

    public void setAnsweredStartDatetime(long answeredStartDatetime) {
        this.answeredStartDatetime = answeredStartDatetime;
    }

    public long getAnsweredEndDatetime() {
        return answeredEndDatetime;
    }

    public void setAnsweredEndDatetime(long answeredEndDatetime) {
        this.answeredEndDatetime = answeredEndDatetime;
    }

    public int getDeviceCallLogId() {
        return deviceCallLogId;
    }

    public void setDeviceCallLogId(int deviceCallLogId) {
        this.deviceCallLogId = deviceCallLogId;
    }

    public int getDeviceCallLogDuration() {
        return deviceCallLogDuration;
    }

    public void setDeviceCallLogDuration(int deviceCallLogDuration) {
        this.deviceCallLogDuration = deviceCallLogDuration;
    }

    public String getDeviceCallLogRecordingFileName() {
        return deviceCallLogRecordingFileName;
    }

    public void setDeviceCallLogRecordingFileName(String deviceCallLogRecordingFileName) {
        this.deviceCallLogRecordingFileName = deviceCallLogRecordingFileName;
    }

    public boolean isCallLogUploaded() {
        return isCallLogUploaded;
    }

    public void setCallLogUploaded(boolean callLogUploaded) {
        isCallLogUploaded = callLogUploaded;
    }

    public boolean isCallRecordingFileUploaded() {
        return isCallRecordingFileUploaded;
    }

    public void setCallRecordingFileUploaded(boolean callRecordingFileUploaded) {
        isCallRecordingFileUploaded = callRecordingFileUploaded;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public CallLog(int id, @NonNull String evenUuid, @NonNull String callDataIdentifier, @NonNull String sid, @NonNull String callType,
                   long startDatetime, long endDatetime, @NonNull String destinationPhoneAccountHandleId,
                   @NonNull String destinationSimIccid, String destinationSimImsi, @NonNull String destinationMccmnc,
                   long answeredStartDatetime, long answeredEndDatetime, int deviceCallLogId, int deviceCallLogDuration,
                   String deviceCallLogRecordingFileName, boolean isCallLogUploaded, boolean isCallRecordingFileUploaded, boolean isSelected) {
        this.id = id;
        this.evenUuid = evenUuid;
        this.callDataIdentifier = callDataIdentifier;
        this.sid = sid;
        this.callType = callType;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.destinationPhoneAccountHandleId = destinationPhoneAccountHandleId;
        this.destinationSimIccid = destinationSimIccid;
        this.destinationSimImsi = destinationSimImsi;
        this.destinationMccmnc = destinationMccmnc;
        this.answeredStartDatetime = answeredStartDatetime;
        this.answeredEndDatetime = answeredEndDatetime;
        this.deviceCallLogId = deviceCallLogId;
        this.deviceCallLogDuration = deviceCallLogDuration;
        this.deviceCallLogRecordingFileName = deviceCallLogRecordingFileName;
        this.isCallLogUploaded = isCallLogUploaded;
        this.isCallRecordingFileUploaded = isCallRecordingFileUploaded;
        this.isSelected = isSelected;
    }
}