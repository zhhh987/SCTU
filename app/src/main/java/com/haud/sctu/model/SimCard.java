package com.haud.sctu.model;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

public class SimCard {
    private String imsi;

    @NonNull
    private String iccid;

    @NonNull
    private String phoneAccountHandleId;

    private String msisdn;

    @NonNull
    private String mcc;

    private String mnc;

    @PrimaryKey
    @NonNull
    private int slot;

    private String slotImei;

    @NonNull
    private String carrier;

    private boolean isRoaming;

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    @NonNull
    public String getIccid() {
        return iccid;
    }

    public void setIccid(@NonNull String iccid) {
        this.iccid = iccid;
    }

    @NonNull
    public String getPhoneAccountHandleId() {
        return phoneAccountHandleId;
    }

    public void setPhoneAccountHandleId(@NonNull String phoneAccountHandleId) {
        this.phoneAccountHandleId = phoneAccountHandleId;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    @NonNull
    public String getMcc() {
        return mcc;
    }

    public void setMcc(@NonNull String mcc) {
        this.mcc = mcc;
    }

    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public String getSlotImei() {
        return slotImei;
    }

    public void setSlotImei(String slotImei) {
        this.slotImei = slotImei;
    }

    @NonNull
    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(@NonNull String carrier) {
        this.carrier = carrier;
    }

    public boolean isRoaming() {
        return isRoaming;
    }

    public void setRoaming(boolean roaming) {
        isRoaming = roaming;
    }
}