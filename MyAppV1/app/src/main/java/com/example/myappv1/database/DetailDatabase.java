package com.example.myappv1.database;


import android.content.Context;


import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.myappv1.model.Detail;


@Database(entities = {Detail.class}, version = 1, exportSchema = false)
public abstract class DetailDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "my_detail";
    public static DetailDatabase instance;

    //    static final Migration MIGRATION_1_3 = new Migration(1, 4) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//
//            database.execSQL("BEGIN TRANSACTION;");
//            database.execSQL("DROP TABLE if exists detail;");
//            database.execSQL("CREATE TABLE detail('id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
//                    "'itemId' INTEGER NOT NULL ," +
//                    "'title' TEXT," +
//                    "'money' REAL NOT NULL ," +
//                    "'isPayed' INTEGER NOT NULL," +
//                    "'lastUpdate' INTEGER," +
//                    "'firstNote' INTEGER" +
//                    ")");
//        //    database.execSQL("INSERT INTO detail_new(id,itemId,title,money,isPayed,lastUpdate, firstNote ) SELECT id,itemId,title,money,isPayed,lastUpdate, firstNote FROM detail;");
//
//
//          //  database.execSQL("ALTER TABLE 'detail_new' RENAME TO 'detail';");
//
//            database.execSQL("COMMIT;");
//        }
//
//
//    };
    public static synchronized DetailDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), DetailDatabase.class, DATABASE_NAME).allowMainThreadQueries()
//                    .fallbackToDestructiveMigration()
//                    .fallbackToDestructiveMigrationOnDowngrade()
//                    .fallbackToDestructiveMigrationFrom(16, 17, 18, 19)
                    .build();
        }
        return instance;
    }

    public abstract DetailDAO detailDAO();
}
