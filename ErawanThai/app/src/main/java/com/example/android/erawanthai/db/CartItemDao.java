package com.example.android.erawanthai.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CartItemDao {
    @Query("SELECT * FROM CartItem")
    LiveData<List<CartItem>> getAll();

    @Insert
    void insertAll(CartItem... cartItems);

    @Delete
    void delete(CartItem cartItem);

    @Query("SELECT * FROM CartItem")
    List<CartItem> getAllSync();
}
