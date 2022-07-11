package com.haud.sctu.model;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sms_log_table")
public class SmsLog {
    @NonNull
    private int id;

    @PrimaryKey
    @NonNull
    private byte pdu;

    @NonNull
    private String eventUuid;

    @NonNull
    private String oa;

    @NonNull
    private long received;

    @NonNull
    private String body;

    @NonNull
    private String destinationSimIccid;

    @NonNull
    private String destinationSimMccmnc;

    private int destinationSimSlot;

    @NonNull
    private boolean isUploaded;

    @NonNull
    private boolean isSelected;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte getPdu() {
        return pdu;
    }

    public void setPdu(byte pdu) {
        this.pdu = pdu;
    }

    @NonNull
    public String getEventUuid() {
        return eventUuid;
    }

    public void setEventUuid(@NonNull String eventUuid) {
        this.eventUuid = eventUuid;
    }

    @NonNull
    public String getOa() {
        return oa;
    }

    public void setOa(@NonNull String oa) {
        this.oa = oa;
    }

    public long getReceived() {
        return received;
    }

    public void setReceived(long received) {
        this.received = received;
    }

    @NonNull
    public String getBody() {
        return body;
    }

    public void setBody(@NonNull String body) {
        this.body = body;
    }

    @NonNull
    public String getDestinationSimIccid() {
        return destinationSimIccid;
    }

    public void setDestinationSimIccid(@NonNull String destinationSimIccid) {
        this.destinationSimIccid = destinationSimIccid;
    }

    @NonNull
    public String getDestinationSimMccmnc() {
        return destinationSimMccmnc;
    }

    public void setDestinationSimMccmnc(@NonNull String destinationSimMccmnc) {
        this.destinationSimMccmnc = destinationSimMccmnc;
    }

    public int getDestinationSimSlot() {
        return destinationSimSlot;
    }

    public void setDestinationSimSlot(int destinationSimSlot) {
        this.destinationSimSlot = destinationSimSlot;
    }

    public boolean isUploaded() {
        return isUploaded;
    }

    public void setUploaded(boolean uploaded) {
        isUploaded = uploaded;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public SmsLog(int id, byte pdu, @NonNull String eventUuid, @NonNull String oa, long received,
                  @NonNull String body, @NonNull String destinationSimIccid, @NonNull String destinationSimMccmnc,
                  int destinationSimSlot, boolean isUploaded,boolean isSelected) {
        this.id = id;
        this.pdu = pdu;
        this.eventUuid = eventUuid;
        this.oa = oa;
        this.received = received;
        this.body = body;
        this.destinationSimIccid = destinationSimIccid;
        this.destinationSimMccmnc = destinationSimMccmnc;
        this.destinationSimSlot = destinationSimSlot;
        this.isUploaded = isUploaded;
        this.isSelected = isSelected;
    }
}


