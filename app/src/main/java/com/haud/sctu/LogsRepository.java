package com.haud.sctu;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class LogsRepository {
    private PhoneDao phoneDao;
    private SmsDao smsDao;
    private LiveData<List<PhoneLog>> allPhoneLogs;
    private LiveData<List<SmsLog>> allSmsLogs;

    public LogsRepository(Application application) {
        LogsDatabase logsDatabase = LogsDatabase.getInstance(application);
        smsDao = logsDatabase.smsDao();
        phoneDao = logsDatabase.phoneDao();
        allSmsLogs = smsDao.getAllSmsLogs();
        allPhoneLogs = phoneDao.getAllPhoneLogs();
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

    public void deleteAllSmsLogs() {
        new DeleteAllSmsAsyncTask(smsDao).execute();
    }

    public LiveData<List<SmsLog>> getAllSmsLogs() {
        return allSmsLogs;
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

    private static class DeleteAllSmsAsyncTask extends AsyncTask<Void, Void, Void> {
        private SmsDao smsDao;

        private DeleteAllSmsAsyncTask(SmsDao smsDao)  {
            this.smsDao = smsDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            smsDao.deleteAllSmsLogs();
            return null;
        }
    }

    // phone logs
    public void insertPhone(PhoneLog phoneLog) {
        new InsertPhoneAsyncTask(phoneDao).execute(phoneLog);
    }

    public void updatePhone(PhoneLog phoneLog) {
        new UpdatePhoneAsyncTask(phoneDao).execute(phoneLog);
    }

    public void deletePhone(PhoneLog phoneLog) {
        new DeletePhoneAsyncTask(phoneDao).execute(phoneLog);
    }

    public void deleteAllPhoneLogs() {
        new DeleteAllPhoneAsyncTask(phoneDao).execute();
    }

    public LiveData<List<PhoneLog>> getAllPhoneLogs() {
        return allPhoneLogs;
    }

    private static class InsertPhoneAsyncTask extends AsyncTask<PhoneLog, Void, Void> {
        private PhoneDao phoneDao;

        private InsertPhoneAsyncTask(PhoneDao phoneDao) {
            this.phoneDao = phoneDao;
        }

        @Override
        protected Void doInBackground(PhoneLog... phoneLogs) {
            phoneDao.insert(phoneLogs[0]);
            return null;
        }
    }

    private static class UpdatePhoneAsyncTask extends AsyncTask<PhoneLog, Void, Void> {
        private PhoneDao phoneDao;

        private UpdatePhoneAsyncTask(PhoneDao phoneDao) {
            this.phoneDao = phoneDao;
        }

        @Override
        protected Void doInBackground(PhoneLog... phoneLogs) {
            phoneDao.update(phoneLogs[0]);
            return null;
        }
    }

    private static class DeletePhoneAsyncTask extends AsyncTask<PhoneLog, Void, Void> {
        private PhoneDao phoneDao;

        private DeletePhoneAsyncTask(PhoneDao phoneDao) {
            this.phoneDao = phoneDao;
        }

        @Override
        protected Void doInBackground(PhoneLog... phoneLogs) {
            phoneDao.delete(phoneLogs[0]);
            return null;
        }
    }

    private static class DeleteAllPhoneAsyncTask extends AsyncTask<Void, Void, Void> {
        private PhoneDao phoneDao;

        private DeleteAllPhoneAsyncTask(PhoneDao phoneDao)  {
            this.phoneDao = phoneDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            phoneDao.deleteAllPhoneLogs();
            return null;
        }
    }
}
