package com.example.medicalhelper;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "foodnotes")
public class FoodNote {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String foodTitle;
    private String food;
    private int type; // 1 - breakfast, 2 - lunch, 3 - dinner, 4 - other
    private String date;

    public FoodNote(int id, String foodTitle, String food, int type, String date) {
        this.id = id;
        this.foodTitle = foodTitle;
        this.food = food;
        this.type = type;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getFoodTitle() {
        return foodTitle;
    }

    public String getFood() {
        return food;
    }

    public int getType() {
        return type;
    }

    public String getDate() {
        return date;
    }
}
