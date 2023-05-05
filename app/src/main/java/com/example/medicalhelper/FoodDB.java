package com.example.medicalhelper;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FoodNote.class}, version = 1)
public abstract class FoodDB extends RoomDatabase {
    private static FoodDB instance = null;
    private static final String DB_NAME = "notes.db";

    public static FoodDB getInstance(Application application) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                application, FoodDB.class, DB_NAME
            ).build();
        }
        return instance;
    }

    public abstract FoodDao notesDao();
}
