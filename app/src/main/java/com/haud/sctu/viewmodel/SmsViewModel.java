package com.haud.sctu.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.haud.sctu.model.SmsLog;
import com.haud.sctu.repository.LogsRepository;

import java.util.ArrayList;
import java.util.List;

public class SmsViewModel extends AndroidViewModel {
    private LogsRepository repository;
    private LiveData<List<SmsLog>> allSmsLogs;

    public SmsViewModel(@NonNull Application application) {
        super(application);
        repository = new LogsRepository(application);
        allSmsLogs = repository.getAllSmsLogs();
    }

    public void insert(SmsLog smsLog) {
        repository.insertSms(smsLog);
    }

    public void update(SmsLog smsLog) {
        repository.updateSms(smsLog);
    }

    public void delete(SmsLog smsLog) {
        repository.deleteSms(smsLog);
    }

    public void deleteAllSmsLogsByOa(String selectedOa) {
        repository.deleteAllSmsLogsByOa(selectedOa);
    }

    public LiveData<List<SmsLog>> getAllSmsLogs() {
        return allSmsLogs;
    }

    public LiveData<List<SmsLog>> getAllSmsByOa(String selectedOa) {
        return repository.getAllSmsByOa(selectedOa);
    }

    public LiveData<Integer> getSmsByOaCount(String selectedOa){
        return repository.getSmsByOaCount(selectedOa);
    }

    public LiveData<List<SmsLog>> getSmsSearchResults(String input) {
        return repository.getSmsSearchResults(input);
    }
}
