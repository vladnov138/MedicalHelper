package com.example.medicalhelper;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface FoodDao {

    @Query("SELECT * FROM foodnotes")
    List<FoodNote> getNotes();

    @Insert
    void add(FoodNote note);
}
