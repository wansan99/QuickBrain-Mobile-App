package com.example.mdproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashMap;

public class Homepage extends AppCompatActivity {
    private TextView textViewWelcome;

    private Button startTut, startGame;
    private ProgressBar expBar;
    private ImageButton leaderboard, profile, achievement;
    private TextView textViewStarsNumber;
    private TextView textViewPercentageCompleted;
    private TextView textViewTutorialCompleted;
    private boolean ASA = false;
    private boolean LSA = false;
    private boolean MGA = false;
    private boolean MQA = false;
    private int mgaStars = 0;
    private int asaStars = 0;
    private int mqaStars = 0;
    private int lsaStars = 0;
    
    private int stars = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        textViewWelcome = findViewById(R.id.tvWelcome);
        textViewStarsNumber = findViewById(R.id.tvLvl);
        textViewPercentageCompleted = findViewById(R.id.textViewPercentageCompleted);
        textViewTutorialCompleted = findViewById(R.id.textViewTutorialCompleted);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> userInfo = sessionManager.getUserDataFromSession();

        textViewWelcome.setText("Welcome Back! \n" + userInfo.get(SessionManager.KEY_NAME));

        profile = findViewById(R.id.btnProfile);
        leaderboard = findViewById(R.id.btnLeaderboard);
        expBar = findViewById(R.id.pbExperience);
        startTut = findViewById(R.id.startTutorial);
        startGame = findViewById(R.id.startGame);
        achievement = findViewById(R.id.btnAchievement);

        QBDataBase db = new QBDataBase(this);
        db.extractQuestionsToDB(this);
        calculateStar(userInfo, db);
        setClickable();
    }

    public void setClickable(){
        startTut.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TutorialSelection.class);
            startActivity(intent);
        });
        startGame.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), game_selection.class);
            intent.putExtra("LSA", String.valueOf(lsaStars));
            intent.putExtra("MGA", String.valueOf(mgaStars));
            intent.putExtra("MQA", String.valueOf(mqaStars));
            startActivity(intent);
        });
        profile.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Profile.class);
            intent.putExtra("Stars", "" + stars);
            intent.putExtra("ASA", String.valueOf(asaStars));
            intent.putExtra("LSA", String.valueOf(lsaStars));
            intent.putExtra("MGA", String.valueOf(mgaStars));
            intent.putExtra("MQA", String.valueOf(mqaStars));
            startActivity(intent);
        });
        leaderboard.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), leaderboard.class);
            startActivity(intent);
        });
        achievement.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), achievement.class);
            intent.putExtra("ASA", "" + asaStars);
            intent.putExtra("LSA", String.valueOf(lsaStars));
            intent.putExtra("MGA", String.valueOf(mgaStars));
            intent.putExtra("MQA", String.valueOf(mqaStars));
            startActivity(intent);
        });
    }

    private void calculateStar(HashMap<String, String> userInfo, QBDataBase db) {
        int checkingScore;
        checkingScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MGS1));
        if(checkingScore > 8000 && checkingScore < 13000) { stars++; } 
        else if(checkingScore > 13000 && checkingScore < 16000) { stars += 2; } 
        else if(checkingScore > 16000) { stars += 3; }
        
        checkingScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MGS2));
        if(checkingScore > 10000 && checkingScore < 16000) { stars++; } 
        else if(checkingScore > 16000 && checkingScore < 20000) { stars += 2; } 
        else if(checkingScore > 20000) { stars += 3; }
        
        checkingScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MGS3));
        if(checkingScore > 12000 && checkingScore < 18000) { stars++; } 
        else if(checkingScore > 18000 && checkingScore < 22000) { stars += 2; } 
        else if(checkingScore > 22000) { stars += 3; }
        
        checkingScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MGS4));
        if(checkingScore > 8000 && checkingScore < 12000) { stars++; } 
        else if(checkingScore > 12000 && checkingScore < 16000) { stars += 2; } 
        else if(checkingScore > 16000) { stars += 3; }
        
        checkingScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MGS5));
        if(checkingScore > 12000 && checkingScore < 18000) { stars++; } 
        else if(checkingScore > 18000 && checkingScore < 24000) { stars += 2; } 
        else if(checkingScore > 24000) { stars += 3; }

        checkingScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MGS6));
        if(checkingScore > 10000 && checkingScore < 15000) { stars++; }
        else if(checkingScore > 15000 && checkingScore < 20000) { stars += 2; }
        else if(checkingScore > 20000) { stars += 3; }

        checkingScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MGS7));
        if(checkingScore > 10000 && checkingScore < 15000) { stars++; }
        else if(checkingScore > 15000 && checkingScore < 20000) { stars += 2; }
        else if(checkingScore > 20000) { stars += 3; }

        mgaStars = stars;

        checkingScore = Integer.parseInt(userInfo.get(SessionManager.KEY_LSS1));
        if(checkingScore > 10000 && checkingScore < 18000) { stars++; }
        else if(checkingScore > 18000 && checkingScore < 28000) { stars += 2; }
        else if(checkingScore > 28000) { stars += 3; }

        checkingScore = Integer.parseInt(userInfo.get(SessionManager.KEY_LSS2));
        if(checkingScore > 10000 && checkingScore < 18000) { stars++; }
        else if(checkingScore > 18000 && checkingScore < 28000) { stars += 2; }
        else if(checkingScore > 28000) { stars += 3; }

        lsaStars = stars - mgaStars;

        checkingScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MQS1));
        if(checkingScore > 15000 && checkingScore < 19000) { stars++; }
        else if(checkingScore > 19000 && checkingScore < 25000) { stars += 2; }
        else if(checkingScore > 25000) { stars += 3; }

        checkingScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MQS2));
        if(checkingScore > 15000 && checkingScore < 19000) { stars++; }
        else if(checkingScore > 19000 && checkingScore < 25000) { stars += 2; }
        else if(checkingScore > 25000) { stars += 3; }


        checkingScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MQS3));
        if(checkingScore > 12000 && checkingScore < 16000) { stars++; }
        else if(checkingScore > 16000 && checkingScore < 20000) { stars += 2; }
        else if(checkingScore > 20000) { stars += 3; }

        checkingScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MQS4));
        if(checkingScore > 12000 && checkingScore < 16000) { stars++; }
        else if(checkingScore > 16000 && checkingScore < 20000) { stars += 2; }
        else if(checkingScore > 20000) { stars += 3; }

        checkingScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MQS5));
        if(checkingScore > 8000 && checkingScore < 12000) { stars++; }
        else if(checkingScore > 12000 && checkingScore < 16000) { stars += 2; }
        else if(checkingScore > 16000) { stars += 3; }

        checkingScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MQS6));
        if(checkingScore > 10000 && checkingScore < 15000) { stars++; }
        else if(checkingScore > 15000 && checkingScore < 20000) { stars += 2; }
        else if(checkingScore > 20000) { stars += 3; }

        checkingScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MQS7));
        if(checkingScore > 8000 && checkingScore < 10000) { stars++; }
        else if(checkingScore > 10000 && checkingScore < 20000) { stars += 2; }
        else if(checkingScore > 20000) { stars += 3; }

        mqaStars = stars - lsaStars - mgaStars;
        asaStars = stars;
        int i = (int) ((double) stars/48 *100);
        expBar.setProgress(i);
        textViewPercentageCompleted.setText("" + i + "% Completed");
        textViewStarsNumber.setText("" + stars);
        int temp = db.getTutorialProgress(userInfo.get(SessionManager.KEY_USERNAME));
        textViewTutorialCompleted.setText("" + ((int) ((double) temp/4 * 100)) + "% Completed");
    }
}