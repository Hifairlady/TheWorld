package com.edgar.theworld;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EquipDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEquipItem(EquipItem equipItem);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEquipItems(EquipItem... equipItems);

    @Query("DELETE FROM equips_table")
    void deleteAllEquips();

    @Query("SELECT * FROM equips_table ORDER BY itemId ASC")
    LiveData<List<EquipItem>> getAllEquips();

    @Query("SELECT * FROM equips_table WHERE itemType LIKE :itemType ORDER BY itemId ASC")
    LiveData<List<EquipItem>> getAllEquipsByType(String itemType);
}
