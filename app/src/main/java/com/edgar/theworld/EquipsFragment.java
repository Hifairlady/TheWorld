package com.edgar.theworld;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class EquipsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public EquipsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static EquipsFragment newInstance(String param1, String param2) {
        EquipsFragment fragment = new EquipsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_equips, container, false);

        ViewPager mViewPager = rootView.findViewById(R.id.view_pager_items);
        ArrayList<ItemPageFragment> itemPageFragments = new ArrayList<>();
        for (int i = 0; i < WorldUtils.MAX_ITEM_PAGE; i++) {
            ItemPageFragment itemPageFragment = ItemPageFragment.newInstance(WorldUtils.PAGE_TITLES[i]);
            itemPageFragments.add(itemPageFragment);
        }
        ItemsViewPagerAdapter pagerAdapter = new ItemsViewPagerAdapter(getChildFragmentManager(), itemPageFragments);
        mViewPager.setAdapter(pagerAdapter);
        TabLayout mTabLayout = rootView.findViewById(R.id.tab_layout_items);
        mTabLayout.setupWithViewPager(mViewPager);

        return rootView;
    }

}
