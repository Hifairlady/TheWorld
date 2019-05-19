package com.edgar.theworld;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ItemsViewPagerAdapter extends FragmentPagerAdapter {

    private static final String[] PAGE_TITLES = {"WEAPONS", "HELMETS", "CLOTHES", "ACCESSORIES", "WINGS"};

    public ItemsViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position >= 0 && position < PAGE_TITLES.length) {
            return PAGE_TITLES[position];
        }
        return "UNDEFINED";
    }
}
