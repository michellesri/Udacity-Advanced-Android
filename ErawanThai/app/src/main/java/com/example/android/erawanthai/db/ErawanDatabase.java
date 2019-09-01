package com.example.android.erawanthai.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {CartItem.class}, version = 1)
@TypeConverters({MeatConverter.class, SizeConverter.class, SpiceLevelConverter.class})
public abstract class ErawanDatabase extends RoomDatabase {
    public abstract CartItemDao cartItemDao();

    public static ErawanDatabase getDatabase(Context context) {
        return Room.databaseBuilder(context,
                ErawanDatabase.class, "erawan-database-name").build();

    }
}
