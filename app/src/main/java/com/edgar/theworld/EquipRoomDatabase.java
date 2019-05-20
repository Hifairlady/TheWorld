package com.edgar.theworld;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {EquipItem.class}, version = 1, exportSchema = false)
public abstract class EquipRoomDatabase extends RoomDatabase {

    private static volatile EquipRoomDatabase INSTANCE;

    static EquipRoomDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (EquipRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            EquipRoomDatabase.class, "equips_database").build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract EquipDao equipDao();

}
