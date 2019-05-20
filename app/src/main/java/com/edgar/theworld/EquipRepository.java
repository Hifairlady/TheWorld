package com.edgar.theworld;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.edgar.theworld.WorldUtils.DATA_FILE_NAMES;
import static com.edgar.theworld.WorldUtils.PAGE_TITLES;
import static com.edgar.theworld.WorldUtils.getDescriptionString;
import static com.edgar.theworld.WorldUtils.getIconFilePath;
import static com.edgar.theworld.WorldUtils.getIndexString;
import static com.edgar.theworld.WorldUtils.getItemArtPath;
import static com.edgar.theworld.WorldUtils.getItemNameChs;
import static com.edgar.theworld.WorldUtils.getItemNameEng;
import static com.edgar.theworld.WorldUtils.getItemQuality;
import static com.edgar.theworld.WorldUtils.getLevelString;

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
        private List<String> allNames = new ArrayList<String>();

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

            allNames.addAll(weaponNames);
            allNames.add(PAGE_TITLES[0]);
            allNames.addAll(helmetNames);
            allNames.add(PAGE_TITLES[1]);
            allNames.addAll(clothNames);
            allNames.add(PAGE_TITLES[2]);
            allNames.addAll(accessoryNames);
            allNames.add(PAGE_TITLES[3]);
            allNames.addAll(wingNames);
            allNames.add(PAGE_TITLES[4]);
            allNames.addAll(bossIconNames);
            allNames.add(PAGE_TITLES[5]);
            allNames.addAll(miscNames);
            allNames.add(PAGE_TITLES[6]);

            parseAndInsert(mContext);

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

        private void parseAndInsert(Context context) {
            int itemId = 0;

            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(context.getAssets().open(DATA_FILE_NAMES[7]), StandardCharsets.UTF_8));
                String lineString;

                boolean isValidItem = true;
                String itemIndex = "Default";
                String nameChs = "Default";
                String nameEng = "Default";
                String itemArtPath = "Default";
                String itemType = "Default";
                String itemLevel = "Default";
                String itemQuality = "Default";
                String itemDescription = "Default";
                while ((lineString = reader.readLine()) != null) {

                    if (lineString.startsWith("[")) {
                        isValidItem = true;
                        itemIndex = getIndexString(lineString);
                        continue;
                    }

                    if (lineString.startsWith("Art=")) {
                        itemArtPath = getIconFilePath(lineString);
                        continue;
                    }

                    if (lineString.startsWith("Description=")) {
                        itemDescription = getDescriptionString(lineString);
                        itemLevel = getLevelString(lineString);
                        itemQuality = getItemQuality(lineString);
                        continue;
                    }

                    if (lineString.startsWith("Name=")) {
                        nameChs = getItemNameChs(lineString);
                        if (!allNames.contains(nameChs)) {
                            isValidItem = false;
                        } else {
                            itemArtPath = getItemArtPath(nameChs);
                            int index = allNames.indexOf(nameChs);
                            for (String pageTitle : PAGE_TITLES) {
                                if (index >= 0 && index < allNames.indexOf(pageTitle)) {
                                    itemType = pageTitle;
                                    break;
                                }
                            }
                        }
                        continue;
                    }

                    if (lineString.startsWith("EngName=")) {
                        nameEng = getItemNameEng(lineString);
                        continue;
                    }

                    if (lineString.startsWith("Tip=")) {
                        isValidItem = false;
                        continue;
                    }

                    if (lineString.startsWith("Ubertip=") && isValidItem) {
                        // int itemId, String itemIndex, String nameChs, String nameEng,
                        // String itemArtPath, String itemType, String itemLevel,
                        // String itemQuality, String itemDescription
                        EquipItem item = new EquipItem(itemId, itemIndex, nameChs, nameEng,
                                itemArtPath, itemType, itemLevel, itemQuality, itemDescription);
                        equipDao.insertEquipItem(item);
                        itemId++;
                    }

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
        }
    }

}
