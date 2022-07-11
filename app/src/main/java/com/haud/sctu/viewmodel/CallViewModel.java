package com.haud.sctu.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.haud.sctu.model.CallLog;
import com.haud.sctu.repository.LogsRepository;

import java.util.List;

public class CallViewModel extends AndroidViewModel {
    private LogsRepository repository;
    private LiveData<List<CallLog>> allCallLogs;

    public CallViewModel(@NonNull Application application) {
        super(application);
        repository = new LogsRepository(application);
        allCallLogs = repository.getAllCallLogs();
    }

    public void insert(CallLog callLog) {
        repository.insertCall(callLog);
    }

    public void update(CallLog callLog) {
        repository.updateCall(callLog);
    }

    public void delete(CallLog callLog) {
        repository.deleteCall(callLog);
    }

    public void deleteAllCallLogs() {
        repository.deleteAllCallLogs();
    }

    public LiveData<List<CallLog>> getAllCallLogs() {
        return allCallLogs;
    }
}
