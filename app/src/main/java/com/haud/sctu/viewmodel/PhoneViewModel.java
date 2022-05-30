package com.haud.sctu.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.haud.sctu.model.PhoneLog;
import com.haud.sctu.repository.LogsRepository;

import java.util.List;

public class PhoneViewModel extends AndroidViewModel {
    private LogsRepository repository;
    private LiveData<List<PhoneLog>> allPhoneLogs;

    public PhoneViewModel(@NonNull Application application) {
        super(application);
        repository = new LogsRepository(application);
        allPhoneLogs = repository.getAllPhoneLogs();
    }

    public void insert(PhoneLog phoneLog) {
        repository.insertPhone(phoneLog);
    }

    public void update(PhoneLog phoneLog) {
        repository.updatePhone(phoneLog);
    }

    public void delete(PhoneLog phoneLog) {
        repository.deletePhone(phoneLog);
    }

    public void deleteAllPhoneLogs() {
        repository.deleteAllPhoneLogs();
    }

    public LiveData<List<PhoneLog>> getAllPhoneLogs() {
        return allPhoneLogs;
    }
}
