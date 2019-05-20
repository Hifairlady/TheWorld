package com.edgar.theworld;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class EquipRepository {

    private EquipDao mEquipDao;
    private LiveData<List<EquipItem>> mAllEquipItems;

    public EquipRepository(Application application) {
        EquipRoomDatabase database = EquipRoomDatabase.getInstance(application);
        mEquipDao = database.equipDao();
        mAllEquipItems = mEquipDao.getAllEquips();
    }

    public LiveData<List<EquipItem>> getAllEquipItems() {
        return mAllEquipItems;
    }

    public LiveData<List<EquipItem>> getAllEquipsByType(String itemType) {
        return mEquipDao.getAllEquipsByType(itemType);
    }

    public void insertEquipItems(EquipItem[] equipItems) {

    }

    public void insertAllEquips() {
        new InsertAllTask(mEquipDao).execute();
    }

    private class InsertAllTask extends AsyncTask<Void, Void, Void> {

        private EquipDao equipDao;

        public InsertAllTask(EquipDao equipDao) {
            this.equipDao = equipDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }

}
