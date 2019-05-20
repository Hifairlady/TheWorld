package com.edgar.theworld;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemPageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CUR_PAGE_TITLE = "CUR_PAGE_TITLE";

    // TODO: Rename and change types of parameters
    private String mPageTitle;

    private EquipsViewModel equipsViewModel;
    private RecyclerView mRecyclerview;
    private EquipItemsAdapter itemsAdapter;

    private ItemPageFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ItemPageFragment newInstance(String param1) {
        ItemPageFragment fragment = new ItemPageFragment();
        Bundle args = new Bundle();
        args.putString(CUR_PAGE_TITLE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPageTitle = getArguments().getString(CUR_PAGE_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_items_list, container, false);
        mRecyclerview = rootView.findViewById(R.id.recyclerview_items);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerview.setLayoutManager(layoutManager);
        itemsAdapter = new EquipItemsAdapter(getContext());
        mRecyclerview.setAdapter(itemsAdapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        equipsViewModel = ViewModelProviders.of(getActivity()).get(EquipsViewModel.class);
        equipsViewModel.getAllEquipItems().observe(getActivity(), new Observer<List<EquipItem>>() {
            @Override
            public void onChanged(List<EquipItem> equipItems) {
//                List<EquipItem> equipItemList = equipsViewModel.getAllEqupsByType(mPageTitle);
//                itemsAdapter.setAllEquips(equipItemList);
                itemsAdapter.setAllEquips(equipItems);
            }
        });
        equipsViewModel.insertAllEquips(getActivity());
    }
}
