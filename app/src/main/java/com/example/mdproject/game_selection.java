package com.example.mdproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class game_selection extends AppCompatActivity {

    private Button playPTG, playMatching, playQM;
    private ImageButton btnBack;
    private String[] achievements;
    private TextView NPp, MGp, QMp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selection);

        achievements = new String[3];
        achievements[0] = getIntent().getStringExtra("LSA");
        achievements[1] = getIntent().getStringExtra("MGA");
        achievements[2] = getIntent().getStringExtra("MQA");

        btnBack = findViewById(R.id.btnBack);
        NPp = findViewById(R.id.textViewNPPercentage);
        MGp = findViewById(R.id.textViewMGPercentage);
        QMp = findViewById(R.id.textViewQMPercentage);

        NPp.setText("" + ((int) ((double) Integer.parseInt(achievements[0])/6 * 100)) + "% Completed");
        MGp.setText("" + ((int) ((double) Integer.parseInt(achievements[1])/21 * 100)) + "% Completed");
        QMp.setText("" + ((int) ((double) Integer.parseInt(achievements[2])/21 * 100)) + "% Completed");

        playPTG = findViewById(R.id.playPTG);
        playMatching = findViewById(R.id.playMatch);
        playQM = findViewById(R.id.playQM);

        setClickable();
    }

    public void setClickable() {
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Homepage.class);
            startActivity(intent);
        });
        playQM.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), levelSelection.class);
            intent.putExtra("GameType", "Math Game");
            startActivity(intent);
        });
        playMatching.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), levelSelection.class);
            intent.putExtra("GameType", "Memory Game");
            startActivity(intent);
        });
        playPTG.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), levelSelection.class);
            intent.putExtra("GameType", "PTGS");
            startActivity(intent);
        });
    }
}