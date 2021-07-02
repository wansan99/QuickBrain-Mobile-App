package com.example.mdproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class gameResult extends AppCompatActivity {

    private ImageButton back;
    private ImageView ic_resultStar;
    private Button nextLvl, playAgain;

    private String GameType;
    private String[] Level;
    private String Score;
    private String Win;
    private String tempLevel;

    private int score;
    private int storedScore;
    private boolean lockButton = false;

    private TextView textViewGameTitle, textViewGameLevel, textViewScore;
    private ImageView iv_GameImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);

        GameType = getIntent().getStringExtra("GameType");
        tempLevel = "Level " + getIntent().getStringExtra("Level");
        Score = getIntent().getStringExtra("Score");
        Win = getIntent().getStringExtra("Win");

        Level = tempLevel.split("\\.");
        textViewGameTitle = findViewById(R.id.tvGameTitle);
        textViewGameLevel = findViewById(R.id.tvGameLevel);
        textViewScore = findViewById(R.id.tvScore);
        iv_GameImage = findViewById(R.id.iv_GameImage);
        ic_resultStar = findViewById(R.id.ic_resultStar);
        ic_resultStar.setVisibility(View.VISIBLE);

        if(Level[0].equals("Level 6")) textViewGameLevel.setText(tempLevel);
        else textViewGameLevel.setText(Level[0]);
        textViewGameTitle.setText(GameType);
        textViewScore.setText("Score: " + Score);

        back = (ImageButton) findViewById(R.id.btnBack);

        nextLvl = (Button) findViewById(R.id.btnNextLvl);
        playAgain = (Button) findViewById(R.id.btnPlayAgain);

        score = Integer.parseInt(Score);
        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> userInfo = sessionManager.getUserDataFromSession();

        checkGameType(userInfo);
    }

    private void checkGameType(HashMap<String, String> userInfo) {
        if (GameType.equals("Memory Game")) {
            iv_GameImage.setImageResource(R.drawable.icon_matching_result);
            if (Level[0].equals("Level 1")) {
                storedScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MGS1));
                if(storedScore < 8000) lockButton = true;
                nextLvl.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), gameMatching_Level2.class);
                    startActivity(intent);
                });
            } else if (Level[0].equals("Level 2")) {
                storedScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MGS2));
                if(storedScore < 10000) lockButton = true;
                nextLvl.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), gameMatching_Level3.class);
                    startActivity(intent);
                });
            } else if (Level[0].equals("Level 3")) {
                storedScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MGS3));
                if(storedScore < 12000) lockButton = true;
                nextLvl.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), gameMatching_Level4.class);
                    startActivity(intent);
                });
            } else if (Level[0].equals("Level 4")) {
                storedScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MGS4));
                if(storedScore < 8000) lockButton = true;
                nextLvl.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), gameMatching_Level5.class);
                    startActivity(intent);
                });
            } else if (Level[0].equals("Level 5")) {
                storedScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MGS5));
                if(storedScore < 12000) lockButton = true;
                nextLvl.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), gameMatching_Level6.class);
                    startActivity(intent);
                });
            } else if (Level[0].equals("Level 6")) {
                storedScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MGS6));
                if(storedScore < 10000) lockButton = true;
                nextLvl.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), gameMatching_Level7.class);
                    startActivity(intent);
                });
            } else {
                nextLvl.setBackgroundResource(R.drawable.btn_bg_7);
            }
            if(lockButton) {
                nextLvl.setEnabled(false);
                nextLvl.setBackgroundResource(R.drawable.btn_bg_7);
            }
            if (Level[0].equals("Level 1")) {
                if(score < 8000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs0);
                } else if(score > 8000 && score < 13000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs1);
                } else if(score > 13000 && score < 16000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs2);
                } else if(score > 16000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs3);
                }
                playAgain.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), gameMatching_Level1.class);
                    startActivity(intent);
                });
            } else if (Level[0].equals("Level 2")) {
                if(score < 10000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs0);
                } else if(score > 10000 && score < 16000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs1);
                } else if(score > 16000 && score < 20000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs2);
                } else if(score > 20000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs3);
                }
                playAgain.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), gameMatching_Level2.class);
                    startActivity(intent);
                });
            } else if (Level[0].equals("Level 3")) {
                if(score < 12000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs0);
                } else if(score > 12000 && score < 18000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs1);
                } else if(score > 18000 && score < 22000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs2);
                } else if(score > 22000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs3);
                }
                playAgain.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), gameMatching_Level3.class);
                    startActivity(intent);
                });
            } else if (Level[0].equals("Level 4")) {
                if(score < 8000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs0);
                } else if(score > 8000 && score < 12000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs1);
                } else if(score > 12000 && score < 16000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs2);
                } else if(score > 16000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs3);
                }
                playAgain.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), gameMatching_Level4.class);
                    startActivity(intent);
                });
            } else if (Level[0].equals("Level 5")) {
                if(score < 12000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs0);
                } else if(score > 12000 && score < 18000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs1);
                } else if(score > 18000 && score < 24000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs2);
                } else if(score > 24000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs3);
                }
                playAgain.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), gameMatching_Level5.class);
                    startActivity(intent);
                });
            } else if (Level[0].equals("Level 6")) {
                if(score < 10000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs0);
                } else if(score > 10000 && score < 15000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs1);
                } else if(score > 15000 && score < 20000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs2);
                } else if(score > 20000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs3);
                }
                playAgain.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), gameMatching_Level6.class);
                    startActivity(intent);
                });
            } else if (Level[0].equals("Level 7")) {
                if(score < 10000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs0);
                } else if(score > 10000 && score < 15000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs1);
                } else if(score > 15000 && score < 20000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs2);
                } else if(score > 20000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs3);
                }
                playAgain.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), gameMatching_Level7.class);
                    startActivity(intent);
                });
            }
        }
        else if (GameType.equals("Tutorial")){
            iv_GameImage.setImageResource(R.drawable.icon_quickmath_result);
            textViewScore.setText("" + Score + " out of 5 Questions Correct");
            if(Level[0].equals("Level Add")) {
                textViewGameTitle.setText("Addition Tutorial");
                playAgain.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), TutorialQuiz.class);
                    intent.putExtra("questionType", "1");
                    startActivity(intent);
                });
            } else if(Level[0].equals("Level Sub")) {
                textViewGameTitle.setText("Subtraction Tutorial");
                playAgain.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), TutorialQuiz.class);
                    intent.putExtra("questionType", "2");
                    startActivity(intent);
                });
            } else if(Level[0].equals("Level Mul")) {
                textViewGameTitle.setText("Multiplication Tutorial");
                playAgain.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), TutorialQuiz.class);
                    intent.putExtra("questionType", "3");
                    startActivity(intent);
                });
            } else if(Level[0].equals("Level Div")) {
                textViewGameTitle.setText("Division Tutorial");
                playAgain.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), TutorialQuiz.class);
                    intent.putExtra("questionType", "4");
                    startActivity(intent);
                });
            }
            nextLvl.setText("Return");
            nextLvl.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), TutorialSelection.class);
                mediaPlayerHandler.optimizeBGM(getApplicationContext(), 1);
                startActivity(intent);
            });
            ic_resultStar.setVisibility(View.GONE);
        }
        else if (GameType.equals("Largest")){
            iv_GameImage.setImageResource(R.drawable.icon_ptg_result);
            textViewGameTitle.setText("Pick the Greatest");
            nextLvl.setText("Switch Mode");
            nextLvl.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), gamePTS.class);
                startActivity(intent);
            });
            playAgain.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), gamePTG.class);
                startActivity(intent);
            });
            if(score < 10000) {
                ic_resultStar.setImageResource(R.drawable.ic_rs0);
            } else if(score > 10000 && score < 18000) {
                ic_resultStar.setImageResource(R.drawable.ic_rs1);
            } else if(score > 18000 && score < 28000) {
                ic_resultStar.setImageResource(R.drawable.ic_rs2);
            } else if(score > 28000) {
                ic_resultStar.setImageResource(R.drawable.ic_rs3);
            }
        }
        else if (GameType.equals("Smallest")){
            iv_GameImage.setImageResource(R.drawable.icon_ptg_result);
            textViewGameTitle.setText("Pick the Smallest");
            nextLvl.setText("Switch Mode");
            nextLvl.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), gamePTG.class);
                startActivity(intent);
            });
            playAgain.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), gamePTS.class);
                startActivity(intent);
            });
            if(score < 10000) {
                ic_resultStar.setImageResource(R.drawable.ic_rs0);
            } else if(score > 10000 && score < 18000) {
                ic_resultStar.setImageResource(R.drawable.ic_rs1);
            } else if(score > 18000 && score < 28000) {
                ic_resultStar.setImageResource(R.drawable.ic_rs2);
            } else if(score > 28000) {
                ic_resultStar.setImageResource(R.drawable.ic_rs3);
            }
        }
        else if(GameType.equals("Math Quiz")) {
            iv_GameImage.setImageResource(R.drawable.icon_quickmath_result);
            textViewGameTitle.setText("Quick Math");
            if (Level[0].equals("Level 1")) {
                storedScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MQS1));
                if(storedScore < 15000) lockButton = true;
                nextLvl.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), MathQuiz.class);
                    intent.putExtra("questionType", "2");
                    startActivity(intent);
                });
            } else if (Level[0].equals("Level 2")) {
                storedScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MQS2));
                if(storedScore < 15000) lockButton = true;
                nextLvl.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), MathQuiz.class);
                    intent.putExtra("questionType", "3");
                    startActivity(intent);
                });
            } else if (Level[0].equals("Level 3")) {
                storedScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MQS3));
                if(storedScore < 12000) lockButton = true;
                nextLvl.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), MathQuiz.class);
                    intent.putExtra("questionType", "4");
                    startActivity(intent);
                });
            } else if (Level[0].equals("Level 4")) {
                storedScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MQS4));
                if(storedScore < 12000) lockButton = true;
                nextLvl.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), MathQuiz.class);
                    intent.putExtra("questionType", "5");
                    startActivity(intent);
                });
            } else if (Level[0].equals("Level 5")) {
                storedScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MQS5));
                if(storedScore < 8000) lockButton = true;
                nextLvl.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), MathQuiz.class);
                    intent.putExtra("questionType", "6");
                    startActivity(intent);
                });
            } else if (Level[0].equals("Level 6")) {
                storedScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MQS6));
                if(storedScore < 10000) lockButton = true;
                nextLvl.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), MathQuiz.class);
                    intent.putExtra("questionType", "7");
                    startActivity(intent);
                });
            } else {
                nextLvl.setBackgroundResource(R.drawable.btn_bg_7);
            }
            if(lockButton) {
                nextLvl.setEnabled(false);
                nextLvl.setBackgroundResource(R.drawable.btn_bg_7);
            }
            if (Level[0].equals("Level 1")) {
                if(score < 15000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs0);
                } else if(score > 15000 && score < 19000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs1);
                } else if(score > 19000 && score < 25000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs2);
                } else if(score > 25000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs3);
                }
                playAgain.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), MathQuiz.class);
                    intent.putExtra("questionType", "1");
                    startActivity(intent);
                });
            } else if (Level[0].equals("Level 2")) {
                if(score < 15000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs0);
                } else if(score > 15000 && score < 19000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs1);
                } else if(score > 19000 && score < 25000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs2);
                } else if(score > 25000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs3);
                }
                playAgain.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), MathQuiz.class);
                    intent.putExtra("questionType", "2");
                    startActivity(intent);
                });
            } else if (Level[0].equals("Level 3")) {
                if(score < 12000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs0);
                } else if(score > 12000 && score < 16000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs1);
                } else if(score > 16000 && score < 20000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs2);
                } else if(score > 20000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs3);
                }
                playAgain.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), MathQuiz.class);
                    intent.putExtra("questionType", "3");
                    startActivity(intent);
                });
            } else if (Level[0].equals("Level 4")) {
                if(score < 12000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs0);
                } else if(score > 12000 && score < 16000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs1);
                } else if(score > 16000 && score < 20000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs2);
                } else if(score > 20000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs3);
                }
                playAgain.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), MathQuiz.class);
                    intent.putExtra("questionType", "4");
                    startActivity(intent);
                });
            } else if (Level[0].equals("Level 5")) {
                if(score < 8000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs0);
                } else if(score > 8000 && score < 12000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs1);
                } else if(score > 12000 && score < 16000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs2);
                } else if(score > 16000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs3);
                }
                playAgain.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), MathQuiz.class);
                    intent.putExtra("questionType", "5");
                    startActivity(intent);
                });
            } else if (Level[0].equals("Level 6")) {
                if(score < 10000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs0);
                } else if(score > 10000 && score < 15000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs1);
                } else if(score > 15000 && score < 20000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs2);
                } else if(score > 20000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs3);
                }
                playAgain.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), MathQuiz.class);
                    intent.putExtra("questionType", "6");
                    startActivity(intent);
                });
            } else if (Level[0].equals("Level 7")) {
                if(score < 8000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs0);
                } else if(score > 8000 && score < 10000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs1);
                } else if(score > 10000 && score < 20000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs2);
                } else if(score > 20000) {
                    ic_resultStar.setImageResource(R.drawable.ic_rs3);
                }
                playAgain.setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), MathQuiz.class);
                    intent.putExtra("questionType", "7");
                    startActivity(intent);
                });
            }
        }

        back.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Homepage.class);
            mediaPlayerHandler.optimizeBGM(getApplicationContext(), 1);
            startActivity(intent);
        });
    }
}