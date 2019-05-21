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
import static com.edgar.theworld.WorldUtils.PAGE_TITLES;
import static com.edgar.theworld.WorldUtils.getDescriptionString;
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
        private int[] itemAmounts = new int[PAGE_TITLES.length];

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
            itemAmounts[0] = allNames.size();
            allNames.addAll(helmetNames);
            itemAmounts[1] = allNames.size();
            allNames.addAll(clothNames);
            itemAmounts[2] = allNames.size();
            allNames.addAll(accessoryNames);
            itemAmounts[3] = allNames.size();
            allNames.addAll(wingNames);
            itemAmounts[4] = allNames.size();
            allNames.addAll(bossIconNames);
            itemAmounts[5] = allNames.size();
            allNames.addAll(miscNames);
            itemAmounts[6] = allNames.size();

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
                    lineString = lineString.replace(" ", "");
                    lineString = lineString.replace("+", " +");
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
            EquipItem[] equipItems = new EquipItem[allNames.size()];

            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(context.getAssets().open(DATA_FILE_NAMES[7]), StandardCharsets.UTF_8));
                String lineString = null;

                boolean isValidItem = true;
                int itemId = 0;
                String itemIndex = "Default";
                String nameChs = "Default";
                String nameEng = "Default";
                String itemArtPath = "Default";
                String itemType = "Default";
                String itemLevel = "Default";
                String itemQuality = "Default";
                String itemDescription = "Default";
                while ((lineString = reader.readLine()) != null) {

                    if (lineString.equals("[END]")) {
                        Log.d(TAG, "parseAndInsert: " + lineString);
                        break;
                    }

                    if (lineString.startsWith("[")) {
                        isValidItem = true;
                        itemId = 0;
                        itemIndex = getIndexString(lineString);
                        nameChs = "Default";
                        nameEng = "Default";
                        itemArtPath = "Default";
                        itemType = "Default";
                        itemLevel = "Default";
                        itemQuality = "Default";
                        itemDescription = "Default";
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
                            itemId = allNames.indexOf(nameChs);
                            itemArtPath = getItemArtPath(nameChs);

                            for (int i = 0; i < itemAmounts.length; i++) {
                                if (itemId >= 0 && itemId < itemAmounts[i]) {
                                    itemType = PAGE_TITLES[i];
//                                    Log.d(TAG, "itemId: " + itemId + " " + nameChs + " is a " + itemType);
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

                    if (lineString.startsWith("Ubertip=") && isValidItem) {
                        // int itemId, String itemIndex, String nameChs, String nameEng,
                        // String itemArtPath, String itemType, String itemLevel,
                        // String itemQuality, String itemDescription
                        EquipItem item = new EquipItem(itemId, itemIndex, nameChs, nameEng,
                                itemArtPath, itemType, itemLevel, itemQuality, itemDescription);
                        equipItems[itemId] = item;
                    }

                }
                equipDao.insertEquipItems(equipItems);

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
