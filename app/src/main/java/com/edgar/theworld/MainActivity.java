package com.edgar.theworld;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.edgar.theworld.WorldUtils.DATA_FILE_NAMES;
import static com.edgar.theworld.WorldUtils.PAGE_TITLES;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    ArrayList<EquipItem> equipItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_item_search:
                break;

            case R.id.menu_item_switch:
                break;

            default:
                break;
        }
        return false;
    }

    private void initView() {

        Toolbar mToolbar = findViewById(R.id.main_toolbar);
        if (getSupportActionBar() == null) {
            setSupportActionBar(mToolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        EquipsFragment fragment = EquipsFragment.newInstance(null, null);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_fragments_container, fragment);
        transaction.commit();

    }

    private void initData() {
        boolean isFirstRun = true;
        if (isFirstRun) {
            new FirstRunTask().execute();
        }
    }

    private class FirstRunTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            List<String> weaponNames = readingNamesFromAsset(DATA_FILE_NAMES[0]);
            List<String> helmetNames = readingNamesFromAsset(DATA_FILE_NAMES[1]);
            List<String> clothNames = readingNamesFromAsset(DATA_FILE_NAMES[2]);
            List<String> accessoryNames = readingNamesFromAsset(DATA_FILE_NAMES[3]);
            List<String> wingNames = readingNamesFromAsset(DATA_FILE_NAMES[4]);
            List<String> bossIconNames = readingNamesFromAsset(DATA_FILE_NAMES[5]);
            List<String> miscNames = readingNamesFromAsset(DATA_FILE_NAMES[6]);

            String[] weaponsArray = weaponNames.toArray(new String[weaponNames.size()]);
            String[] helmetsArray = helmetNames.toArray(new String[helmetNames.size()]);
            String[] clothesArray = clothNames.toArray(new String[clothNames.size()]);
            String[] accessoriesArray = accessoryNames.toArray(new String[accessoryNames.size()]);
            String[] wingsArray = wingNames.toArray(new String[wingNames.size()]);
            String[] bossIconsArray = bossIconNames.toArray(new String[bossIconNames.size()]);
            String[] miscsArray = miscNames.toArray(new String[miscNames.size()]);

            appendData(PAGE_TITLES[0], weaponNames);
            appendData(PAGE_TITLES[1], helmetNames);
            appendData(PAGE_TITLES[2], clothNames);
            appendData(PAGE_TITLES[3], accessoryNames);
            appendData(PAGE_TITLES[4], wingNames);
            appendData(PAGE_TITLES[5], bossIconNames);
            appendData(PAGE_TITLES[6], miscNames);

            return null;
        }

        private List<String> readingNamesFromAsset(String filename) {
            List<String> result = new ArrayList<String>();

            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(getAssets()
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

        private void appendData(String itemType, List<String> itemNames) {
            for (String itemName : itemNames) {
                EquipItem equipItem = new EquipItem();
                equipItem.setItemType(itemType);
                equipItem.setNameChs(itemName);
                equipItem.setItemId(equipItems.size());
                equipItems.add(equipItem);
            }
        }
    }


}
