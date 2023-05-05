package com.example.medicalhelper.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.medicalhelper.AddActivity;
import com.example.medicalhelper.Database;
import com.example.medicalhelper.FoodNote;
import com.example.medicalhelper.MainActivity;
import com.example.medicalhelper.Note;
import com.example.medicalhelper.R;
import com.example.medicalhelper.databinding.FragmentDashboardBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    private View root;
    private Button btn;
//    private ArrayList<FoodNote> foodNotes = new ArrayList<>();
    private Database db = Database.getInstance();
    private LinearLayout linearLayout;
    private static String date = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        root = binding.getRoot();


        btn = root.findViewById(R.id.button);
        linearLayout = root.findViewById(R.id.breakfast_linearlayout);

        CalendarView calendar = root.findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(((MainActivity) requireActivity()), date, Toast.LENGTH_LONG).show();
                date = year + "-" + month + "-" + dayOfMonth;
                Toast.makeText(((MainActivity) requireActivity()), date, Toast.LENGTH_LONG).show();
                showNotes();
            }
        });

        btn.setOnClickListener(v -> {
            Intent intent = AddActivity.newIntent(((MainActivity) requireActivity()));
            startActivity(intent);
        });
        showNotes();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();

        showNotes();
    }

    public static String getDate() {
        if (date == null) {
            Calendar calendar = Calendar.getInstance(); // создаем объект Calendar с текущей датой

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            date = year + "-" + month + "-" + dayOfMonth;
        }
        return date;
    }

    private void showNotes() {
        linearLayout.removeAllViews();
        for (Note note:
                db.getNotes()) {
            if (note.getDate().equals(getDate())) {
                View v = getLayoutInflater().inflate(R.layout.foodnote_item, linearLayout, false);
                TextView textViewTitle = v.findViewById(R.id.textTitle);
                TextView textViewDescription = v.findViewById(R.id.textContent);
                textViewTitle.setText(note.getTitle());
                textViewDescription.setText(note.getDescription());

                int colorResId;
                switch (note.getType()) {
                    case 0:
                        colorResId = android.R.color.holo_green_light;
                        break;
                    case 1:
                        colorResId = android.R.color.holo_orange_light;
                        break;
                    default:
                        colorResId = android.R.color.holo_red_light;
                        break;
                }
                int color = ContextCompat.getColor(((MainActivity) requireActivity()), colorResId);
                textViewTitle.setBackgroundColor(color);
                textViewDescription.setBackgroundColor(color);
                linearLayout.addView(v);
            }
        }
    }
}