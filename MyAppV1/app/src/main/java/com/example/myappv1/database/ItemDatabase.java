package com.example.myappv1.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myappv1.model.Item;

@Database(entities = {Item.class}, version = 1, exportSchema = false)
public abstract class ItemDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "my_data";
    public static ItemDatabase instance;
    public static  synchronized ItemDatabase getInstance(Context context){
        if(instance ==null){
//            context.getApplicationContext().deleteDatabase(DATABASE_NAME);
            instance = Room.databaseBuilder(context.getApplicationContext(), ItemDatabase.class, DATABASE_NAME)
            .allowMainThreadQueries().build();

        }
        return instance;
    }

    public abstract ItemDAO itemDAO();
}
