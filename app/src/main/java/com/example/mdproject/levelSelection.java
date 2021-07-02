package com.example.mdproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.HashMap;

public class levelSelection extends AppCompatActivity {
    private Button level1, level2, level3, level4, level5, level6, level7;
    private ImageView starResult1, starResult2, starResult3, starResult4, starResult5, starResult6, starResult7;
    String GameType;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selection);

        level1 = findViewById(R.id.level1);
        level2 = findViewById(R.id.level2);
        level3 = findViewById(R.id.level3);
        level4 = findViewById(R.id.level4);
        level5 = findViewById(R.id.level5);
        level6 = findViewById(R.id.level6);
        level7 = findViewById(R.id.level7);
        starResult1 = findViewById(R.id.ivResultLv1);
        starResult2 = findViewById(R.id.ivResultLv2);
        starResult3 = findViewById(R.id.ivResultLv3);
        starResult4 = findViewById(R.id.ivResultLv4);
        starResult5 = findViewById(R.id.ivResultLv5);
        starResult6 = findViewById(R.id.ivResultLv6);
        starResult7 = findViewById(R.id.ivResultLv7);
        btnBack = findViewById(R.id.btnBack);
        
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Homepage.class);
            startActivity(intent);
        });

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> userInfo = sessionManager.getUserDataFromSession();

        GameType = getIntent().getStringExtra("GameType");
        manageGameLevel();
        manageStar(userInfo);
    }
    
    private void manageGameLevel() {
        if(GameType.equals("Memory Game")) {
            level1.setOnClickListener(v -> {
                LoginActivity.main.stop();
                Intent intent = new Intent(getApplicationContext(), gameMatching_Level1.class);
                startActivity(intent);
            });
            level2.setOnClickListener(v -> {
                LoginActivity.main.stop();
                Intent intent = new Intent(getApplicationContext(), gameMatching_Level2.class);
                startActivity(intent);
            });
            level3.setOnClickListener(v -> {
                LoginActivity.main.stop();
                Intent intent = new Intent(getApplicationContext(), gameMatching_Level3.class);
                startActivity(intent);
            });
            level4.setOnClickListener(v -> {
                LoginActivity.main.stop();
                Intent intent = new Intent(getApplicationContext(), gameMatching_Level4.class);
                startActivity(intent);
            });
            level5.setOnClickListener(v -> {
                LoginActivity.main.stop();
                Intent intent = new Intent(getApplicationContext(), gameMatching_Level5.class);
                startActivity(intent);
            });
            level6.setOnClickListener(v -> {
                LoginActivity.main.stop();
                Intent intent = new Intent(getApplicationContext(), gameMatching_Level6.class);
                startActivity(intent);
            });
            level7.setOnClickListener(v -> {
                LoginActivity.main.stop();
                Intent intent = new Intent(getApplicationContext(), gameMatching_Level7.class);
                startActivity(intent);
            });
        }
        else if(GameType.equals("PTGS")) {
            level1.setOnClickListener(v -> {
                LoginActivity.main.stop();
                Intent intent = new Intent(getApplicationContext(), gamePTG.class);
                startActivity(intent);
            });
            level2.setOnClickListener(v -> {
                LoginActivity.main.stop();
                Intent intent = new Intent(getApplicationContext(), gamePTS.class);
                startActivity(intent);
            });
            level3.setVisibility(View.INVISIBLE);
            level4.setVisibility(View.INVISIBLE);
            level5.setVisibility(View.INVISIBLE);
            level6.setVisibility(View.INVISIBLE);
            level7.setVisibility(View.INVISIBLE);
            starResult3.setVisibility(View.INVISIBLE);
            starResult4.setVisibility(View.INVISIBLE);
            starResult5.setVisibility(View.INVISIBLE);
            starResult6.setVisibility(View.INVISIBLE);
            starResult7.setVisibility(View.INVISIBLE);
        }
        else if(GameType.equals("Math Game")) {
            level1.setOnClickListener(v -> {
                LoginActivity.main.stop();
                Intent intent = new Intent(getApplicationContext(), MathQuiz.class);
                intent.putExtra("questionType", "1");
                startActivity(intent);
            });
            level2.setOnClickListener(v -> {
                LoginActivity.main.stop();
                Intent intent = new Intent(getApplicationContext(), MathQuiz.class);
                intent.putExtra("questionType", "2");
                startActivity(intent);
            });
            level3.setOnClickListener(v -> {
                LoginActivity.main.stop();
                Intent intent = new Intent(getApplicationContext(), MathQuiz.class);
                intent.putExtra("questionType", "3");
                startActivity(intent);
            });
            level4.setOnClickListener(v -> {
                LoginActivity.main.stop();
                Intent intent = new Intent(getApplicationContext(), MathQuiz.class);
                intent.putExtra("questionType", "4");
                startActivity(intent);
            });
            level5.setOnClickListener(v -> {
                LoginActivity.main.stop();
                Intent intent = new Intent(getApplicationContext(), MathQuiz.class);
                intent.putExtra("questionType", "5");
                startActivity(intent);
            });
            level6.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), MathQuiz.class);
                intent.putExtra("questionType", "6");
                startActivity(intent);
            });
            level7.setOnClickListener(v -> {
                LoginActivity.main.stop();
                Intent intent = new Intent(getApplicationContext(), MathQuiz.class);
                intent.putExtra("questionType", "7");
                startActivity(intent);
            });
        }
    }
    
    private void manageStar(HashMap<String, String> userInfo) {
        int checkingScore;
        level2.setEnabled(false); level3.setEnabled(false); level4.setEnabled(false); level5.setEnabled(false); level6.setEnabled(false); level7.setEnabled(false);
        starResult2.setImageResource(R.drawable.icon_stars_lock);
        starResult3.setImageResource(R.drawable.icon_stars_lock);
        starResult4.setImageResource(R.drawable.icon_stars_lock);
        starResult5.setImageResource(R.drawable.icon_stars_lock);
        starResult6.setImageResource(R.drawable.icon_stars_lock);
        starResult7.setImageResource(R.drawable.icon_stars_lock);
        if(GameType.equals("Memory Game")) {
            checkingScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MGS1));
            if(checkingScore < 8000) { starResult1.setImageResource(R.drawable.icon_stars0); }
            else {
                if(checkingScore > 8000 && checkingScore < 13000) { starResult1.setImageResource(R.drawable.icon_stars1); }
                else if(checkingScore > 13000 && checkingScore < 16000) { starResult1.setImageResource(R.drawable.icon_stars2); }
                else if(checkingScore > 16000) { starResult1.setImageResource(R.drawable.icon_stars3); }

                level2.setEnabled(true);
                checkingScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MGS2));
                if(checkingScore < 8000) { starResult2.setImageResource(R.drawable.icon_stars0); }
                else {
                    if(checkingScore > 10000 && checkingScore < 16000) { starResult2.setImageResource(R.drawable.icon_stars1); }
                    else if(checkingScore > 16000 && checkingScore < 20000) { starResult2.setImageResource(R.drawable.icon_stars2); }
                    else if(checkingScore > 20000) { starResult2.setImageResource(R.drawable.icon_stars3); }

                    level3.setEnabled(true);
                    checkingScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MGS3));
                    if(checkingScore < 12000) { starResult3.setImageResource(R.drawable.icon_stars0); }
                    else {
                        if(checkingScore > 12000 && checkingScore < 18000) { starResult3.setImageResource(R.drawable.icon_stars1); }
                        else if(checkingScore > 18000 && checkingScore < 22000) { starResult3.setImageResource(R.drawable.icon_stars2); }
                        else if(checkingScore > 22000) { starResult3.setImageResource(R.drawable.icon_stars3);
                        }

                        level4.setEnabled(true);
                        checkingScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MGS4));
                        if(checkingScore < 8000) { starResult4.setImageResource(R.drawable.icon_stars0); }
                        else {
                            if(checkingScore > 8000 && checkingScore < 12000) { starResult4.setImageResource(R.drawable.icon_stars1); }
                            else if(checkingScore > 12000 && checkingScore < 16000) { starResult4.setImageResource(R.drawable.icon_stars2); }
                            else if(checkingScore > 16000) { starResult4.setImageResource(R.drawable.icon_stars3); }

                            level5.setEnabled(true);
                            checkingScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MGS5));
                            if(checkingScore < 12000) { starResult5.setImageResource(R.drawable.icon_stars0); }
                            else {
                                if(checkingScore > 12000 && checkingScore < 18000) { starResult5.setImageResource(R.drawable.icon_stars1); }
                                else if(checkingScore > 18000 && checkingScore < 24000) { starResult5.setImageResource(R.drawable.icon_stars2); }
                                else if(checkingScore > 24000) { starResult5.setImageResource(R.drawable.icon_stars3); }

                                level6.setEnabled(true);
                                checkingScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MGS6));
                                if(checkingScore < 10000) { starResult6.setImageResource(R.drawable.icon_stars0); }
                                else {
                                    if(checkingScore > 10000 && checkingScore < 15000) { starResult6.setImageResource(R.drawable.icon_stars1); }
                                    else if(checkingScore > 15000 && checkingScore < 20000) { starResult6.setImageResource(R.drawable.icon_stars2); }
                                    else if(checkingScore > 20000) { starResult6.setImageResource(R.drawable.icon_stars3); }

                                    level7.setEnabled(true);
                                    checkingScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MGS7));
                                    if(checkingScore < 12000) { starResult7.setImageResource(R.drawable.icon_stars0); }
                                    else {
                                        if(checkingScore > 10000 && checkingScore < 15000) { starResult7.setImageResource(R.drawable.icon_stars1); }
                                        else if(checkingScore > 15000 && checkingScore < 20000) { starResult7.setImageResource(R.drawable.icon_stars2); }
                                        else if(checkingScore > 20000) { starResult7.setImageResource(R.drawable.icon_stars3); }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        else if(GameType.equals("PTGS")) {
            level2.setEnabled(true);
            checkingScore = Integer.parseInt(userInfo.get(SessionManager.KEY_LSS1));
            if(checkingScore < 12000) { starResult1.setImageResource(R.drawable.icon_stars0); }
            else {
                if(checkingScore > 10000 && checkingScore < 18000) { starResult1.setImageResource(R.drawable.icon_stars1); }
                else if(checkingScore > 18000 && checkingScore < 28000) { starResult1.setImageResource(R.drawable.icon_stars2); }
                else if(checkingScore > 28000) { starResult1.setImageResource(R.drawable.icon_stars3); }
            }

            checkingScore = Integer.parseInt(userInfo.get(SessionManager.KEY_LSS2));
            if(checkingScore < 12000) { starResult2.setImageResource(R.drawable.icon_stars0); }
            else {
                if(checkingScore > 10000 && checkingScore < 18000) { starResult2.setImageResource(R.drawable.icon_stars1); }
                else if(checkingScore > 18000 && checkingScore < 28000) { starResult2.setImageResource(R.drawable.icon_stars2); }
                else if(checkingScore > 28000) { starResult2.setImageResource(R.drawable.icon_stars3); }
            }
        }
        else if(GameType.equals("Math Game")) {
            checkingScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MQS1));
            if(checkingScore < 15000) { starResult1.setImageResource(R.drawable.icon_stars0); }
            else {
                if(checkingScore > 15000 && checkingScore < 19000) { starResult1.setImageResource(R.drawable.icon_stars1); }
                else if(checkingScore > 19000 && checkingScore < 25000) { starResult1.setImageResource(R.drawable.icon_stars2); }
                else if(checkingScore > 25000) { starResult1.setImageResource(R.drawable.icon_stars3); }

                level2.setEnabled(true);
                checkingScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MQS2));
                if(checkingScore < 15000) { starResult2.setImageResource(R.drawable.icon_stars0); }
                else {
                    if(checkingScore > 15000 && checkingScore < 19000) { starResult2.setImageResource(R.drawable.icon_stars1); }
                    else if(checkingScore > 19000 && checkingScore < 25000) { starResult2.setImageResource(R.drawable.icon_stars2); }
                    else if(checkingScore > 25000) { starResult2.setImageResource(R.drawable.icon_stars3); }

                    level3.setEnabled(true);
                    checkingScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MQS3));
                    if(checkingScore < 12000) { starResult3.setImageResource(R.drawable.icon_stars0); }
                    else {
                        if(checkingScore > 12000 && checkingScore < 16000) { starResult3.setImageResource(R.drawable.icon_stars1); }
                        else if(checkingScore > 16000 && checkingScore < 20000) { starResult3.setImageResource(R.drawable.icon_stars2); }
                        else if(checkingScore > 20000) { starResult3.setImageResource(R.drawable.icon_stars3); }

                        level4.setEnabled(true);
                        checkingScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MQS4));
                        if(checkingScore < 12000) { starResult4.setImageResource(R.drawable.icon_stars0); }
                        else {
                            if(checkingScore > 12000 && checkingScore < 16000) { starResult4.setImageResource(R.drawable.icon_stars1); }
                            else if(checkingScore > 16000 && checkingScore < 20000) { starResult4.setImageResource(R.drawable.icon_stars2); }
                            else if(checkingScore > 20000) { starResult4.setImageResource(R.drawable.icon_stars3); }

                            level5.setEnabled(true);
                            checkingScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MQS5));
                            if(checkingScore < 8000) { starResult5.setImageResource(R.drawable.icon_stars0); }
                            else {
                                if(checkingScore > 8000 && checkingScore < 12000) { starResult5.setImageResource(R.drawable.icon_stars1); }
                                else if(checkingScore > 12000 && checkingScore < 16000) { starResult5.setImageResource(R.drawable.icon_stars2); }
                                else if(checkingScore > 16000) { starResult5.setImageResource(R.drawable.icon_stars3); }

                                level6.setEnabled(true);
                                checkingScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MQS6));
                                if(checkingScore < 10000) { starResult6.setImageResource(R.drawable.icon_stars0); }
                                else {
                                    if(checkingScore > 10000 && checkingScore < 15000) { starResult6.setImageResource(R.drawable.icon_stars1); }
                                    else if(checkingScore > 15000 && checkingScore < 20000) { starResult6.setImageResource(R.drawable.icon_stars2); }
                                    else if(checkingScore > 20000) { starResult6.setImageResource(R.drawable.icon_stars3); }

                                    level7.setEnabled(true);
                                    checkingScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MQS7));
                                    if(checkingScore < 8000) { starResult7.setImageResource(R.drawable.icon_stars0); }
                                    else {
                                        if(checkingScore > 8000 && checkingScore < 10000) { starResult7.setImageResource(R.drawable.icon_stars1); }
                                        else if(checkingScore > 10000 && checkingScore < 20000) { starResult7.setImageResource(R.drawable.icon_stars2); }
                                        else if(checkingScore > 20000) { starResult7.setImageResource(R.drawable.icon_stars3); }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}