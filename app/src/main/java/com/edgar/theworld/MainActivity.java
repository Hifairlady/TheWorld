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

            List<String> weaponNames = readingNamesFromAsset("items_names/weapons_names.txt");
            List<String> helmetNames = readingNamesFromAsset("items_names/helmets_names.txt");
            List<String> clothNames = readingNamesFromAsset("items_names/clothes_names.txt");
            List<String> accessoryNames = readingNamesFromAsset("items_names/accessories_names.txt");
            List<String> wingNames = readingNamesFromAsset("items_names/wings_names.txt");
            List<String> bossIconNames = readingNamesFromAsset("items_names/boss_icons_names.txt");
            List<String> miscNames = readingNamesFromAsset("items_names/miscs_names.txt");

            String[] weaponsArray = weaponNames.toArray(new String[weaponNames.size()]);
            String[] helmetsArray = helmetNames.toArray(new String[helmetNames.size()]);
            String[] clothesArray = clothNames.toArray(new String[clothNames.size()]);
            String[] accessoriesArray = accessoryNames.toArray(new String[accessoryNames.size()]);
            String[] wingsArray = wingNames.toArray(new String[wingNames.size()]);
            String[] bossIconsArray = bossIconNames.toArray(new String[bossIconNames.size()]);
            String[] miscsArray = miscNames.toArray(new String[miscNames.size()]);

            appendData("WEAPONS", weaponNames);
            appendData("HELMETS", helmetNames);
            appendData("CLOTHES", clothNames);
            appendData("ACCESSORIES", accessoryNames);
            appendData("WINGS", wingNames);
            appendData("BOSS_ICONS", bossIconNames);
            appendData("MISCS", miscNames);

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
