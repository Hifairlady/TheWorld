package com.edgar.theworld;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.edgar.theworld.WorldUtils.DATA_FILE_NAMES;

public class EquipRepository {

    private static final String TAG = EquipRepository.class.getName();
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

    public void insertAllEquips(Context context) {
        new InsertAllTask(context, mEquipDao).execute();
    }

    private class InsertAllTask extends AsyncTask<Void, Void, Void> {

        private EquipDao equipDao;
        private Context mContext;

        public InsertAllTask(Context context, EquipDao equipDao) {
            this.mContext = context;
            this.equipDao = equipDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            List<String> weaponNames = readingNamesFromAsset(DATA_FILE_NAMES[0]);
            List<String> helmetNames = readingNamesFromAsset(DATA_FILE_NAMES[1]);
            List<String> clothNames = readingNamesFromAsset(DATA_FILE_NAMES[2]);
            List<String> accessoryNames = readingNamesFromAsset(DATA_FILE_NAMES[3]);
            List<String> wingNames = readingNamesFromAsset(DATA_FILE_NAMES[4]);
            List<String> bossIconNames = readingNamesFromAsset(DATA_FILE_NAMES[5]);
            List<String> miscNames = readingNamesFromAsset(DATA_FILE_NAMES[6]);
            return null;
        }

        private List<String> readingNamesFromAsset(String filename) {
            List<String> result = new ArrayList<String>();

            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(mContext.getAssets()
                        .open(filename), StandardCharsets.UTF_8));
                String lineString = "";
                while ((lineString = reader.readLine()) != null) {

                    lineString = lineString.replaceAll(" ", "");
                    result.add(lineString);
                    Log.d(TAG, "doInBackground: " + lineString);

                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException ioe2) {
                        ioe2.printStackTrace();
                    }
                }
            }

            return result;
        }
    }

}
