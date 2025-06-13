package com.example.mymental;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class LogActivity extends AppCompatActivity {

    Spinner spinnerActivities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        spinnerActivities = findViewById(R.id.spinner_activities);

        // Create dropdown list
        String[] activities = {"Walking", "Jogging", "Cycling", "Hiking", "Sightseeing"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, activities);
        spinnerActivities.setAdapter(adapter);
    }
}