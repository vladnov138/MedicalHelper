package com.example.medicalhelper;

import com.example.medicalhelper.Note;
import com.example.medicalhelper.ui.dashboard.DashboardFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Database {
    private ArrayList<Note> notes = new ArrayList<>();
    private static Database instance = null;

    public static Database getInstance() {
        if (instance == null)
            instance = new Database();
        return instance;
    }

    public Database() {
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            Note note = new Note(i, "Note" + i, "Desc" + i,  random.nextInt(3),
                    DashboardFragment.getDate());
            notes.add(note);
        }
    }

    public void add(Note note) {
        notes.add(note);
    }

    public ArrayList<Note> getNotes() {
        return new ArrayList<>(notes);
    }

    public void remove(int id) {
        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            if (note.getId() == id)
                notes.remove(note);
        }
    }
}
