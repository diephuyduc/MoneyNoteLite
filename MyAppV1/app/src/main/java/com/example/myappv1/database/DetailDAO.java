package com.example.myappv1.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myappv1.model.Detail;

import java.util.List;

@Dao
public interface DetailDAO {
    @Insert
    public void insertDetail(Detail detail);
    @Delete
    public void deleteDetail(Detail detail);
    @Update
    public void updateDetail(Detail detail);
    @Query("select * from detail where itemId =:itemId order by lastUpdate DESC")
    public List<Detail> getDetailList(int itemId);
    @Query("delete from detail where itemId=:itemId")
    public void deletedItemFather(int itemId);
    @Query("delete from detail")
    public void deleteAll();
}
