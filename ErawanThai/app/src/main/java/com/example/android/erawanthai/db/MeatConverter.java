package com.example.android.erawanthai.db;

import androidx.room.TypeConverter;

import com.example.android.erawanthai.MenuItem;

public class MeatConverter {

    @TypeConverter
    public String meatToString(MenuItem.Meat meat) {
        if (meat == null) {
            return null;
        }
        return meat.toString();
    }

    @TypeConverter
    public MenuItem.Meat stringToMeat(String meat) {
        if (meat == null) {
            return null;
        }
        return MenuItem.Meat.valueOf(meat);
    }
}
