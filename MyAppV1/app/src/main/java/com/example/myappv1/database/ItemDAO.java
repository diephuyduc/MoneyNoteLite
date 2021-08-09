package com.example.myappv1.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myappv1.model.Item;

import java.util.List;

@Dao
public interface ItemDAO {
    @Insert
    void insertItem(Item item);
    @Query("select * from item")
    List<Item> getAllItem();
    @Update
    void updateItem(Item item);
    @Delete
    void deleteItem(Item item);
    @Query("update item set total = :new_total where id = :p_id")
    void updateTotal(double new_total, int p_id);

    @Query("delete from item")
    public void  deleteAll();
}
