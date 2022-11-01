package com.example.ptassistant.Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Client.class, Workout.class}, version = 1)
public abstract class DataBase extends RoomDatabase {

    static DataBase db;
    public abstract MyDao manager();
    public static synchronized DataBase getInstance(Context context){
        if(db == null){
            db = Room.databaseBuilder(context, DataBase.class, "database")
                    .allowMainThreadQueries()
                    .build();
        }
        return db;
    }
}
