package com.example.mymental;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        RecyclerView recyclerView = findViewById(R.id.buttonRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> buttonLabels = Arrays.asList("Button 1", "Button 2", "Button 3", "Button 4");
        ButtonAdapter adapter = new ButtonAdapter(buttonLabels);
        recyclerView.setAdapter(adapter);
    }
}
