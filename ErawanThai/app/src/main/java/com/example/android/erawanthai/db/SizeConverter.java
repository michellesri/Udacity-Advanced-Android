package com.example.android.erawanthai.db;

import androidx.room.TypeConverter;

import com.example.android.erawanthai.MenuItem;

public class SizeConverter {
    @TypeConverter
    public String sizeToString(MenuItem.Size size) {
        if (size == null) {
            return null;
        }
        return size.toString();
    }

    @TypeConverter
    public MenuItem.Size stringToSize(String size) {
        if (size == null) {
            return null;
        }
        return MenuItem.Size.valueOf(size);
    }
}
