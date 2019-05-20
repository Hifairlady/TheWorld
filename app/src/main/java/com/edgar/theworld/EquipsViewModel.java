package com.edgar.theworld;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EquipsViewModel extends AndroidViewModel {

    private EquipRepository mEquipRepo;

    private LiveData<List<EquipItem>> mAllEquipItems;

    public EquipsViewModel(@NonNull Application application) {
        super(application);
        mEquipRepo = new EquipRepository(application);
        mAllEquipItems = mEquipRepo.getAllEquipItems();
    }

    public LiveData<List<EquipItem>> getAllEquipItems() {
        return mAllEquipItems;
    }

    public void filterEquipsByType(String itemType) {
        mAllEquipItems = mEquipRepo.getAllEquipsByType(itemType);
    }

    public List<EquipItem> getAllEqupsByType(String itemType) {
        return mEquipRepo.getAllEquipsByType(itemType).getValue();
    }

    //start to parse the item files and insert data into database
    public void insertAllEquips() {
        mEquipRepo.insertAllEquips();
    }
}
