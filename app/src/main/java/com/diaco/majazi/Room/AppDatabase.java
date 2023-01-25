package com.diaco.majazi.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {VirtualPhone.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract VirtualDao virtualDao();

    private static AppDatabase instance;
    public static AppDatabase getInstance(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(context, AppDatabase.class, "VirtualBuy").build();

        return instance ;
    }
}
