package com.edgar.theworld;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import static com.edgar.theworld.WorldUtils.MAX_ITEM_PAGE;
import static com.edgar.theworld.WorldUtils.PAGE_TITLES;

public class ItemsViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<ItemPageFragment> itemPageFragments = new ArrayList<>();

    public ItemsViewPagerAdapter(FragmentManager fm, ArrayList<ItemPageFragment> itemPageFragments) {
        super(fm);
        this.itemPageFragments = itemPageFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return itemPageFragments.get(position);
    }

    @Override
    public int getCount() {
        return (itemPageFragments == null ? 0 : itemPageFragments.size());
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position >= 0 && position < MAX_ITEM_PAGE) {
            return PAGE_TITLES[position];
        }
        return "UNDEFINED";
    }
}
