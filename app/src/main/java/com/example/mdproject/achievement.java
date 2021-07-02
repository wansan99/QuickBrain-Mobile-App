package com.example.mdproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.HashMap;

public class achievement extends AppCompatActivity {

    private ImageButton back;
    RelativeLayout panel0, panel1, panel2, panel3, panel4;
    private ProgressBar pb1, pb2, pb3, pb4, pb5;
    String[] achievements = new String[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        back = findViewById(R.id.btnBack);
        panel0 = findViewById(R.id.achievementPanel0);
        panel1 = findViewById(R.id.achievementPanel1);
        panel2 = findViewById(R.id.achievementPanel2);
        panel3 = findViewById(R.id.achievementPanel3);
        panel4 = findViewById(R.id.achievementPanel4);
        pb1 = findViewById(R.id.pbAchievement1);
        pb2 = findViewById(R.id.pbAchievement2);
        pb3 = findViewById(R.id.pbAchievement3);
        pb4 = findViewById(R.id.pbAchievement4);
        pb5 = findViewById(R.id.pbAchievement5);
        achievements[0] = getIntent().getStringExtra("ASA");
        achievements[1] = getIntent().getStringExtra("LSA");
        achievements[2] = getIntent().getStringExtra("MGA");
        achievements[3] = getIntent().getStringExtra("MQA");

        QBDataBase db = new QBDataBase(this);
        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> userInfo = sessionManager.getUserDataFromSession();
        setupAchievement(db, userInfo);
        setClickable();
    }

    private void setupAchievement(QBDataBase db, HashMap<String, String> userInfo) {
        int temp = db.getTutorialProgress(userInfo.get(SessionManager.KEY_USERNAME));
        pb1.setProgress((int) ((double) temp/4 * 100));
        pb2.setProgress((int) ((double) Integer.parseInt(achievements[1])/6 *100));
        pb3.setProgress((int) ((double) Integer.parseInt(achievements[2])/21 *100));
        pb4.setProgress((int) ((double) Integer.parseInt(achievements[3])/21 *100));
        pb5.setProgress((int) ((double) Integer.parseInt(achievements[0])/48 *100));
        if(db.getAchievementRecord(5, userInfo.get(SessionManager.KEY_USERNAME)) == 1)
            panel0.setBackgroundResource(R.drawable.btn_bg_6);
        if(db.getAchievementRecord(1, userInfo.get(SessionManager.KEY_USERNAME)) == 1)
            panel4.setBackgroundResource(R.drawable.btn_bg_6);
        if(db.getAchievementRecord(2, userInfo.get(SessionManager.KEY_USERNAME)) == 1)
            panel1.setBackgroundResource(R.drawable.btn_bg_6);
        if(db.getAchievementRecord(3, userInfo.get(SessionManager.KEY_USERNAME)) == 1)
            panel2.setBackgroundResource(R.drawable.btn_bg_6);
        if(db.getAchievementRecord(4, userInfo.get(SessionManager.KEY_USERNAME)) == 1)
            panel3.setBackgroundResource(R.drawable.btn_bg_6);
    }

    private void setClickable() {
        back.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Homepage.class);
            startActivity(intent);
        });
    }
}