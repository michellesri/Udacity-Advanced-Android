package com.example.android.erawanthai.db;

import androidx.room.TypeConverter;

import com.example.android.erawanthai.MenuItem;

public class SpiceLevelConverter {
    @TypeConverter
    public String spiceLevelToString(MenuItem.SpiceLevel spiceLevel) {
        if (spiceLevel == null) {
            return null;
        }
        return spiceLevel.toString();
    }

    @TypeConverter
    public MenuItem.SpiceLevel stringToSpiceLevel(String spiceLevelString) {
        if (spiceLevelString == null) {
            return null;
        }
        return MenuItem.SpiceLevel.valueOf(spiceLevelString);
    }
}
