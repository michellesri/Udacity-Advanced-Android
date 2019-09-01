package com.example.android.erawanthai.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.android.erawanthai.MenuItem;

@Entity
public class CartItem {
    @PrimaryKey @NonNull
    public final String name;

    @ColumnInfo (name = "price")
    public final long price;

    @ColumnInfo (name = "spice_level")
    public final MenuItem.SpiceLevel spiceLevel;

    @ColumnInfo (name = "meat")
    public final MenuItem.Meat meat;

    @ColumnInfo (name = "size")
    public final MenuItem.Size size;

    @ColumnInfo (name = "quantity")
    public final int quantity;

    public CartItem(String name, long price, MenuItem.SpiceLevel spiceLevel, MenuItem.Meat meat, MenuItem.Size size, int quantity) {
        this.name = name;
        this.price = price;
        this.spiceLevel = spiceLevel;
        this.meat = meat;
        this.size = size;
        this.quantity = quantity;
    }
}
