package com.example.mdproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.HashMap;

public class Profile extends AppCompatActivity {

    private ImageButton setting, back;
    private TextView textViewName, textViewUserName, textViewStarsCollected;
    private Button claimA1, claimA2, claimA3, claimA4, claimA5;
    private int stars;
    private String[] achievements = new String[4];
    private ProgressBar pbA1, pbA2, pbA3, pbA4, pbA5;
    RelativeLayout panel0, panel1, panel2, panel3, panel4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setting = findViewById(R.id.btnSetting);
        textViewName = findViewById(R.id.displayName);
        textViewUserName = findViewById(R.id.username);
        textViewStarsCollected = findViewById(R.id.starsText);
        back = findViewById(R.id.btnBack);
        claimA1 = findViewById(R.id.btnClaimA1);
        claimA2 = findViewById(R.id.btnClaimA2);
        claimA3 = findViewById(R.id.btnClaimA3);
        claimA4 = findViewById(R.id.btnClaimA4);
        claimA5 = findViewById(R.id.btnClaimA5);
        pbA1 = findViewById(R.id.pbA1);
        pbA2 = findViewById(R.id.pbA2);
        pbA3 = findViewById(R.id.pbA3);
        pbA4 = findViewById(R.id.pbA4);
        pbA5 = findViewById(R.id.pbA5);
        panel0 = findViewById(R.id.achievementPanel0);
        panel1 = findViewById(R.id.achievementPanel1);
        panel2 = findViewById(R.id.achievementPanel2);
        panel3 = findViewById(R.id.achievementPanel3);
        panel4 = findViewById(R.id.achievementPanel4);

        QBDataBase db = new QBDataBase(this);
        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> userInfo = sessionManager.getUserDataFromSession();
        textViewName.setText("" + userInfo.get(SessionManager.KEY_NAME));
        textViewUserName.setText("@" + userInfo.get(SessionManager.KEY_USERNAME));
        stars = Integer.parseInt(getIntent().getStringExtra("Stars"));
        achievements[0] = getIntent().getStringExtra("ASA");
        achievements[1] = getIntent().getStringExtra("LSA");
        achievements[2] = getIntent().getStringExtra("MGA");
        achievements[3] = getIntent().getStringExtra("MQA");
        textViewStarsCollected.setText("" + stars + " star(s)");

        claimA1.setEnabled(false);
        claimA2.setEnabled(false);
        claimA3.setEnabled(false);
        claimA4.setEnabled(false);
        claimA5.setEnabled(false);

        setClickable(db, userInfo);
        setupAchievement(db, userInfo);
    }

    private void setupAchievement(QBDataBase db, HashMap<String, String> userInfo) {
        int achievementProgress0 = db.getTutorialProgress(userInfo.get(SessionManager.KEY_USERNAME));
        int achievementProgress1 = Integer.parseInt(achievements[0]);
        int achievementProgress2 = Integer.parseInt(achievements[1]);
        int achievementProgress3 = Integer.parseInt(achievements[2]);
        int achievementProgress4 = Integer.parseInt(achievements[3]);
        pbA1.setProgress((int) ((double) achievementProgress0/4 * 100));
        pbA2.setProgress((int) ((double) Integer.parseInt(achievements[1])/6 *100));
        pbA3.setProgress((int) ((double) Integer.parseInt(achievements[2])/21 *100));
        pbA4.setProgress((int) ((double) Integer.parseInt(achievements[3])/21 *100));
        pbA5.setProgress((int) ((double) Integer.parseInt(achievements[0])/48 *100));

        if(achievementProgress0 == 4) {
            if(db.getAchievementRecord(5, userInfo.get(SessionManager.KEY_USERNAME)) == 0) {
                claimA1.setEnabled(true);
                claimA1.setBackgroundResource(R.drawable.btn_bg_1);
                panel0.setBackgroundResource(R.drawable.btn_bg_6);
            }
            else if(db.getAchievementRecord(5, userInfo.get(SessionManager.KEY_USERNAME)) == 1){
                panel0.setBackgroundResource(R.drawable.btn_bg_6);
                claimA1.setBackgroundResource(R.drawable.btn_bg_7);
                claimA1.setTextColor(Color.parseColor("#ffffff"));
                claimA1.setText("Claimed");
            }
        }
        if(achievementProgress1 == 48) {
            if(db.getAchievementRecord(1, userInfo.get(SessionManager.KEY_USERNAME)) == 0) {
                claimA5.setEnabled(true);
                claimA5.setBackgroundResource(R.drawable.btn_bg_1);
                panel4.setBackgroundResource(R.drawable.btn_bg_6);
            }
            else if(db.getAchievementRecord(1, userInfo.get(SessionManager.KEY_USERNAME)) == 1){
                panel4.setBackgroundResource(R.drawable.btn_bg_6);
                claimA5.setBackgroundResource(R.drawable.btn_bg_7);
                claimA5.setTextColor(Color.parseColor("#ffffff"));
                claimA5.setText("Claimed");
            }
        }
        if(achievementProgress2 == 6) {
            if(db.getAchievementRecord(2, userInfo.get(SessionManager.KEY_USERNAME)) == 0) {
                claimA2.setEnabled(true);
                claimA2.setBackgroundResource(R.drawable.btn_bg_1);
                panel1.setBackgroundResource(R.drawable.btn_bg_6);
            }
            else if(db.getAchievementRecord(2, userInfo.get(SessionManager.KEY_USERNAME)) == 1){
                panel1.setBackgroundResource(R.drawable.btn_bg_6);
                claimA2.setBackgroundResource(R.drawable.btn_bg_7);
                claimA2.setTextColor(Color.parseColor("#ffffff"));
                claimA2.setText("Claimed");
            }
        }
        if(achievementProgress3 == 21) {
            if(db.getAchievementRecord(3, userInfo.get(SessionManager.KEY_USERNAME)) == 0) {
                claimA3.setEnabled(true);
                claimA3.setBackgroundResource(R.drawable.btn_bg_1);
                panel2.setBackgroundResource(R.drawable.btn_bg_6);
            }
            else if(db.getAchievementRecord(3, userInfo.get(SessionManager.KEY_USERNAME)) == 1){
                panel2.setBackgroundResource(R.drawable.btn_bg_6);
                claimA3.setBackgroundResource(R.drawable.btn_bg_7);
                claimA3.setTextColor(Color.parseColor("#ffffff"));
                claimA3.setText("Claimed");
            }
        }
        if(achievementProgress4 == 21) {
            if(db.getAchievementRecord(4, userInfo.get(SessionManager.KEY_USERNAME)) == 0) {
                claimA4.setEnabled(true);
                claimA4.setBackgroundResource(R.drawable.btn_bg_1);
                panel3.setBackgroundResource(R.drawable.btn_bg_6);
            }
            else if(db.getAchievementRecord(4, userInfo.get(SessionManager.KEY_USERNAME)) == 1){
                panel3.setBackgroundResource(R.drawable.btn_bg_6);
                claimA4.setBackgroundResource(R.drawable.btn_bg_7);
                claimA4.setTextColor(Color.parseColor("#ffffff"));
                claimA4.setText("Claimed");
            }
        }
    }

    public void setClickable(QBDataBase db, HashMap<String, String> userInfo){
        back.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Homepage.class);
            startActivity(intent);
        });
        setting.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ProfileSetting.class);
            intent.putExtra("Stars", "" + stars);
            intent.putExtra("ASA", achievements[0]);
            intent.putExtra("LSA", achievements[1]);
            intent.putExtra("MGA", achievements[2]);
            intent.putExtra("MQA", achievements[3]);
            startActivity(intent);
        });
        claimA1.setOnClickListener(v -> {
            boolean isUpdated = db.updateAchievement(5, userInfo.get(SessionManager.KEY_USERNAME));
            if(isUpdated) Toast.makeText(getApplicationContext(), "Achievement Claimed", Toast.LENGTH_SHORT).show();
            claimA1.setEnabled(false);
            claimA1.setBackgroundResource(R.drawable.btn_bg_7);
            claimA1.setText("Claimed");
        });
        claimA5.setOnClickListener(v -> {
            boolean isUpdated = db.updateAchievement(1, userInfo.get(SessionManager.KEY_USERNAME));
            if(isUpdated) Toast.makeText(getApplicationContext(), "Achievement Claimed", Toast.LENGTH_SHORT).show();
            claimA5.setEnabled(false);
            claimA5.setBackgroundResource(R.drawable.btn_bg_7);
            claimA5.setText("Claimed");
        });
        claimA2.setOnClickListener(v -> {
            boolean isUpdated = db.updateAchievement(2, userInfo.get(SessionManager.KEY_USERNAME));
            if(isUpdated) Toast.makeText(getApplicationContext(), "Achievement Claimed", Toast.LENGTH_SHORT).show();
            claimA2.setEnabled(false);
            claimA2.setBackgroundResource(R.drawable.btn_bg_7);
            claimA2.setText("Claimed");
        });
        claimA3.setOnClickListener(v -> {
            boolean isUpdated = db.updateAchievement(3, userInfo.get(SessionManager.KEY_USERNAME));
            if(isUpdated) Toast.makeText(getApplicationContext(), "Achievement Claimed", Toast.LENGTH_SHORT).show();
            claimA3.setEnabled(false);
            claimA3.setBackgroundResource(R.drawable.btn_bg_7);
            claimA3.setText("Claimed");
        });
        claimA4.setOnClickListener(v -> {
            boolean isUpdated = db.updateAchievement(4, userInfo.get(SessionManager.KEY_USERNAME));
            if(isUpdated) Toast.makeText(getApplicationContext(), "Achievement Claimed", Toast.LENGTH_SHORT).show();
            claimA4.setEnabled(false);
            claimA4.setBackgroundResource(R.drawable.btn_bg_7);
            claimA4.setText("Claimed");
        });
    }
}