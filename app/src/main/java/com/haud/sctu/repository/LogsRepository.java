package com.haud.sctu.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.haud.sctu.dao.CallDao;
import com.haud.sctu.model.CallLog;
import com.haud.sctu.dao.SmsDao;
import com.haud.sctu.model.SmsLog;
import com.haud.sctu.db.LogsDatabase;

import java.util.ArrayList;
import java.util.List;

public class LogsRepository {
    private CallDao callDao;
    private SmsDao smsDao;
    private LiveData<List<CallLog>> allCallLogs;
    private LiveData<List<SmsLog>> allSmsLogs;


    public LogsRepository(Application application) {
        LogsDatabase logsDatabase = LogsDatabase.getInstance(application);
        smsDao = logsDatabase.smsDao();
        callDao = logsDatabase.callDao();
        allSmsLogs = smsDao.getAllSms();
        allCallLogs = callDao.getAllCallLogs();
    }

    // sms logs
    public void insertSms(SmsLog smsLog) {
        new InsertSmsAsyncTask(smsDao).execute(smsLog);
    }

    public void updateSms(SmsLog smsLog) {
        new UpdateSmsAsyncTask(smsDao).execute(smsLog);
    }

    public void deleteSms(SmsLog smsLog) {
        new DeleteSmsAsyncTask(smsDao).execute(smsLog);
    }

    public void deleteAllSmsLogsByOa(String selectedOa) {
        new DeleteAllSmsByOaAsyncTask(smsDao, selectedOa).execute();
    }

    public LiveData<List<SmsLog>> getAllSmsLogs() {
        return allSmsLogs;
    }

    public LiveData<List<SmsLog>> getAllSmsByOa(String selectedOa) {
        return smsDao.getAllSmsByOa(selectedOa);
    }

    public LiveData<List<SmsLog>> getSmsSearchResults(String input) {
        return smsDao.getSmsSearchResults(input);
    }

    public LiveData<Integer> getSmsByOaCount(String selectedOa) {
        return smsDao.getSmsByOaCount(selectedOa);
    }

    private static class InsertSmsAsyncTask extends AsyncTask<SmsLog, Void, Void> {
        private SmsDao smsDao;

        private InsertSmsAsyncTask(SmsDao smsDao) {
            this.smsDao = smsDao;
        }

        @Override
        protected Void doInBackground(SmsLog... smsLogs) {
            smsDao.insert(smsLogs[0]);
            return null;
        }
    }

    private static class UpdateSmsAsyncTask extends AsyncTask<SmsLog, Void, Void> {
        private SmsDao smsDao;

        private UpdateSmsAsyncTask(SmsDao smsDao) {
            this.smsDao = smsDao;
        }

        @Override
        protected Void doInBackground(SmsLog... smsLogs) {
            smsDao.update(smsLogs[0]);
            return null;
        }
    }

    private static class DeleteSmsAsyncTask extends AsyncTask<SmsLog, Void, Void> {
        private SmsDao smsDao;

        private DeleteSmsAsyncTask(SmsDao smsDao)  {
            this.smsDao = smsDao;
        }

        @Override
        protected Void doInBackground(SmsLog... smsLogs) {
            smsDao.delete(smsLogs[0]);
            return null;
        }
    }

    private static class DeleteAllSmsByOaAsyncTask extends AsyncTask<Void, Void, Void> {
        private SmsDao smsDao;
        private String selectedOa;

        private DeleteAllSmsByOaAsyncTask(SmsDao smsDao, String selectedOa)  {
            this.smsDao = smsDao;
            this.selectedOa = selectedOa;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            smsDao.deleteAllSmsLogsByOa(selectedOa);
            return null;
        }
    }

    // call logs
    public void insertCall(CallLog callLog) {
        new InsertCallAsyncTask(callDao).execute(callLog);
    }

    public void updateCall(CallLog callLog) {
        new UpdateCallAsyncTask(callDao).execute(callLog);
    }

    public void deleteCall(CallLog callLog) {
        new DeleteCallAsyncTask(callDao).execute(callLog);
    }

    public void deleteAllCallLogs() {
        new DeleteAllCallAsyncTask(callDao).execute();
    }

    public LiveData<List<CallLog>> getAllCallLogs() {
        return allCallLogs;
    }

    private static class InsertCallAsyncTask extends AsyncTask<CallLog, Void, Void> {
        private CallDao callDao;

        private InsertCallAsyncTask(CallDao callDao) {
            this.callDao = callDao;
        }

        @Override
        protected Void doInBackground(CallLog... callLogs) {
            callDao.insert(callLogs[0]);
            return null;
        }
    }

    private static class UpdateCallAsyncTask extends AsyncTask<CallLog, Void, Void> {
        private CallDao callDao;

        private UpdateCallAsyncTask(CallDao callDao) {
            this.callDao = callDao;
        }

        @Override
        protected Void doInBackground(CallLog... callLogs) {
            callDao.update(callLogs[0]);
            return null;
        }
    }

    private static class DeleteCallAsyncTask extends AsyncTask<CallLog, Void, Void> {
        private CallDao callDao;

        private DeleteCallAsyncTask(CallDao callDao) {
            this.callDao = callDao;
        }

        @Override
        protected Void doInBackground(CallLog... callLogs) {
            callDao.delete(callLogs[0]);
            return null;
        }
    }

    private static class DeleteAllCallAsyncTask extends AsyncTask<Void, Void, Void> {
        private CallDao callDao;

        private DeleteAllCallAsyncTask(CallDao callDao)  {
            this.callDao = callDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            callDao.deleteAllCallLogs();
            return null;
        }
    }
}
