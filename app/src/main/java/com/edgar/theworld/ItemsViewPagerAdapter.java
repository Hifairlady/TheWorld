package com.edgar.theworld;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ItemsViewPagerAdapter extends FragmentPagerAdapter {

    private static final String[] PAGE_TITLES = {"WEAPONS", "HELMETS", "CLOTHES", "ACCESSORIES", "WINGS"};

    private ArrayList<ItemFragment> itemFragments = new ArrayList<>();

    public ItemsViewPagerAdapter(FragmentManager fm, ArrayList<ItemFragment> itemFragments) {
        super(fm);
        this.itemFragments = itemFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return itemFragments.get(position);
    }

    @Override
    public int getCount() {
        return (itemFragments == null ? 0 : itemFragments.size());
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
