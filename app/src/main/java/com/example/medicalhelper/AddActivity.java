package com.example.medicalhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.medicalhelper.ui.dashboard.DashboardFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddActivity extends AppCompatActivity {

    private FloatingActionButton btn_save;
    private RadioButton radioButtonBreakfast;
    private RadioButton radioButtonLunch;
    private RadioButton radioButtonDinner;
    private RadioButton radioButtonOther;
    private TextView titleView;
    private TextView descriptionView;

    private Database db = Database.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initViews();
        btn_save.setOnClickListener(v -> {
            saveNotes();
        });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, AddActivity.class);
    }

    private void initViews() {
        btn_save = findViewById(R.id.saveNoteButton);
        radioButtonBreakfast = findViewById(R.id.radioButton);
        radioButtonLunch = findViewById(R.id.radioButton2);
        radioButtonDinner = findViewById(R.id.radioButton3);
        radioButtonOther = findViewById(R.id.radioButton4);
        titleView = findViewById(R.id.titleEditText);
        descriptionView = findViewById(R.id.contentEditText);
    }

    private void saveNotes() {
        String title = titleView.getText().toString();
        String description = descriptionView.getText().toString();

        int type = 1;
        if (radioButtonLunch.isChecked())
            type = 2;
        else if (radioButtonDinner.isChecked())
            type = 3;
        else if (radioButtonOther.isChecked())
            type = 4;

        int id = db.getNotes().size();
        Note note = new Note(id, title, description, 1, DashboardFragment.getDate());
        db.add(note);
        finish();
    }
}