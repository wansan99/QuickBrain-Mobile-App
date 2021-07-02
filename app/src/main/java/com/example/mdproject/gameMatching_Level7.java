package com.example.mdproject;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class gameMatching_Level7 extends AppCompatActivity{
    private static final long COUNTDOWN_TO_GAME_START_IN_MILLIS = 3000;

    private ImageButton pause;
    private TextView textViewLevel;
    private TextView textViewScore;
    private TextView textViewPausedLabel;
    private TextView textViewPausedScoreLabel;
    private TextView textViewCountDownToGameStart;
    private TextView textViewGameDescription;
    private Button btn_Resume, restart, exit;
    private ImageView imgCorrect;

    private View viewCard11, viewCard12, viewCard13;
    private View viewCard21, viewCard22, viewCard23;
    private View viewCard31, viewCard32, viewCard33;

    List<Integer> sequence = new ArrayList<>();
    int level = 1;
    int score = 0;
    int currentAtPosition;
    Handler handler = new Handler();
    boolean correct = false;

    final int maxLevel = 15;
    int streak = 0;
    final double scoreMultiplier = 1.2;
    boolean pause_flg = false;
    boolean win = false;
    private CountDownTimer countDownToGameStart;
    private long timeLeftToStart = COUNTDOWN_TO_GAME_START_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_matching_level6);

        pause = findViewById(R.id.btn_pause);
        restart = findViewById(R.id.btn_Restart);
        exit = findViewById(R.id.btn_Exit);
        textViewScore = findViewById(R.id.textViewScore);
        textViewPausedLabel = findViewById(R.id.textViewPausedLabel);
        textViewPausedScoreLabel = findViewById(R.id.textViewPausedScoreLabel);
        textViewCountDownToGameStart = findViewById(R.id.textViewCountDownToGameStart);
        textViewGameDescription = findViewById(R.id.textViewGameDescription);
        btn_Resume = findViewById(R.id.btn_Resume);
        imgCorrect = findViewById(R.id.ic_correct);

        textViewScore = findViewById(R.id.textViewScore);
        textViewLevel = findViewById(R.id.textViewLevel);
        viewCard11 = findViewById(R.id.view11); viewCard12 = findViewById(R.id.view12); viewCard13 = findViewById(R.id.view13);
        viewCard21 = findViewById(R.id.view21); viewCard22 = findViewById(R.id.view22); viewCard23 = findViewById(R.id.view23);
        viewCard31 = findViewById(R.id.view31); viewCard32 = findViewById(R.id.view32); viewCard33 = findViewById(R.id.view33);
        currentAtPosition = 0;

        startCountDownToGameStart();
    }

    private void startCountDownToGameStart() {
        countDownToGameStart = new CountDownTimer(timeLeftToStart, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                MainActivity.onTickSound.start();
                pause.setEnabled(false);
                timeLeftToStart = millisUntilFinished;
                int seconds = (int) (timeLeftToStart / 1000) % 60;
                textViewCountDownToGameStart.setText("" + seconds);
                textViewGameDescription.setText("Follow the Steps!");
            }

            @Override
            public void onFinish() {
                MainActivity.onFinishSound.start();
                mediaPlayerHandler.optimizeBGM(getApplicationContext(), 2);
                timeLeftToStart = 0;
                textViewCountDownToGameStart.setText("" + 0);
                textViewCountDownToGameStart.setVisibility(View.GONE);
                textViewGameDescription.setVisibility(View.GONE);
                pause.setEnabled(true);
                generateSequence();
                setTextViewClickable();
            }
        }.start();
    }

    private void generateSequence(){
        int tempNumber;
        for(int i = 0; i < level + 2; i++) {
            tempNumber = (int) Math.floor(Math.random() * (10 - 1) + 1);
            sequence.add(tempNumber);
        }
        level++;
        displaySequence();
    }

    private void addToSequence() {
        int tempNumber;
        for(int i = 0; i < 2; i++) {
            tempNumber = (int) Math.floor(Math.random() * (10 - 1) + 1);
            sequence.add(tempNumber);
        }
        displaySequence();
    }

    private void displaySequence(){
        setTempNotClickable();
        int currentHoldingTimer = 0;
        for(int i = 0; i < sequence.size(); i++) {
            if(sequence.get(i) == 1) {
                handler.postDelayed(() -> viewCard11.setBackgroundResource(R.drawable.text_view_circle4), currentHoldingTimer);
                currentHoldingTimer += 500;
                handler.postDelayed(() -> viewCard11.setBackgroundResource(R.drawable.text_view_circle3), currentHoldingTimer);
                currentHoldingTimer += 100;
            } else if(sequence.get(i) == 2) {
                handler.postDelayed(() -> viewCard12.setBackgroundResource(R.drawable.text_view_circle4), currentHoldingTimer);
                currentHoldingTimer += 500;
                handler.postDelayed(() -> viewCard12.setBackgroundResource(R.drawable.text_view_circle3), currentHoldingTimer);
                currentHoldingTimer += 100;
            } else if(sequence.get(i) == 3) {
                handler.postDelayed(() -> viewCard13.setBackgroundResource(R.drawable.text_view_circle4), currentHoldingTimer);
                currentHoldingTimer += 500;
                handler.postDelayed(() -> viewCard13.setBackgroundResource(R.drawable.text_view_circle3), currentHoldingTimer);
                currentHoldingTimer += 100;
            }
            else if(sequence.get(i) == 4) {
                handler.postDelayed(() -> viewCard21.setBackgroundResource(R.drawable.text_view_circle4), currentHoldingTimer);
                currentHoldingTimer += 500;
                handler.postDelayed(() -> viewCard21.setBackgroundResource(R.drawable.text_view_circle3), currentHoldingTimer);
                currentHoldingTimer += 100;
            }
            else if(sequence.get(i) == 5) {
                handler.postDelayed(() -> viewCard22.setBackgroundResource(R.drawable.text_view_circle4), currentHoldingTimer);
                currentHoldingTimer += 500;
                handler.postDelayed(() -> viewCard22.setBackgroundResource(R.drawable.text_view_circle3), currentHoldingTimer);
                currentHoldingTimer += 100;
            }
            else if(sequence.get(i) == 6) {
                handler.postDelayed(() -> viewCard23.setBackgroundResource(R.drawable.text_view_circle4), currentHoldingTimer);
                currentHoldingTimer += 500;
                handler.postDelayed(() -> viewCard23.setBackgroundResource(R.drawable.text_view_circle3), currentHoldingTimer);
                currentHoldingTimer += 100;
            }
            else if(sequence.get(i) == 7) {
                handler.postDelayed(() -> viewCard31.setBackgroundResource(R.drawable.text_view_circle4), currentHoldingTimer);
                currentHoldingTimer += 500;
                handler.postDelayed(() -> viewCard31.setBackgroundResource(R.drawable.text_view_circle3), currentHoldingTimer);
                currentHoldingTimer += 100;
            }
            else if(sequence.get(i) == 8) {
                handler.postDelayed(() -> viewCard32.setBackgroundResource(R.drawable.text_view_circle4), currentHoldingTimer);
                currentHoldingTimer += 500;
                handler.postDelayed(() -> viewCard32.setBackgroundResource(R.drawable.text_view_circle3), currentHoldingTimer);
                currentHoldingTimer += 100;
            }
            else if(sequence.get(i) == 9) {
                handler.postDelayed(() -> viewCard33.setBackgroundResource(R.drawable.text_view_circle4), currentHoldingTimer);
                currentHoldingTimer += 500;
                handler.postDelayed(() -> viewCard33.setBackgroundResource(R.drawable.text_view_circle3), currentHoldingTimer);
                currentHoldingTimer += 100;
            }
        }
        handler.postDelayed(() -> setTempClickable(), currentHoldingTimer);
    }

    private void checkAnswer(int inputtedAnswer) {
        if(inputtedAnswer == sequence.get(currentAtPosition)) {
            currentAtPosition++;
            correct = true;
        }
        else {
            correct = false;
            endGame();
        }
        checkEndSequence();
    }

    private void checkEndSequence() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.wrong_correct_animation);
        if(currentAtPosition == level + 1) {
            imgCorrect.setVisibility(View.VISIBLE);
            imgCorrect.startAnimation(animation);
            MainActivity.correctSound.start();
            currentAtPosition = 0;
            setTempNotClickable();
            streak++;
            score += 1000;
            if(streak % 3 == 0) {
                score *= scoreMultiplier;
            }
            textViewScore.setText("Score: " + score);
            textViewLevel.setText("Level: " + level);
            if(level < maxLevel) {
                level += 2;
                handler.postDelayed(() -> addToSequence(), 1500);
                handler.postDelayed(() -> imgCorrect.setVisibility(View.GONE), 1000);
                win = true;
            }
            else endGame();
        }
    }

    private void endGame() {
        MainActivity.gamePlaySound.stop();
        QBDataBase db = new QBDataBase(this);
        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> userInfo = sessionManager.getUserDataFromSession();
        int tempScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MGS7));
        if(tempScore < score) {
            sessionManager.setKeyMgs7(score);
            boolean isUpdated = db.updateScore(16, score, userInfo.get(SessionManager.KEY_USERNAME));
            if(isUpdated) Toast.makeText(getApplicationContext(), "New HighScore", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(getApplicationContext(), gameResult.class);
        intent.putExtra("Level", "7." + level);
        intent.putExtra("Score", Integer.toString(score));
        intent.putExtra("GameType", "Memory Game");
        intent.putExtra("Win", String.valueOf(win));
        startActivity(intent);
    }

    private void setTempNotClickable() {
        viewCard11.setEnabled(false);
        viewCard12.setEnabled(false);
        viewCard13.setEnabled(false);
        viewCard21.setEnabled(false);
        viewCard22.setEnabled(false);
        viewCard23.setEnabled(false);
        viewCard31.setEnabled(false);
        viewCard32.setEnabled(false);
        viewCard33.setEnabled(false);
    }

    private void setTempClickable() {
        viewCard11.setEnabled(true);
        viewCard12.setEnabled(true);
        viewCard13.setEnabled(true);
        viewCard21.setEnabled(true);
        viewCard22.setEnabled(true);
        viewCard23.setEnabled(true);
        viewCard31.setEnabled(true);
        viewCard32.setEnabled(true);
        viewCard33.setEnabled(true);
    }

    private void setTextViewClickable() {
        viewCard11.setOnClickListener(v -> {
            checkAnswer(1);
            viewCard11.setBackgroundResource(R.drawable.text_view_circle4);
            handler.postDelayed(() -> viewCard11.setBackgroundResource(R.drawable.text_view_circle3), 1000);
        });
        viewCard12.setOnClickListener(v -> {
            checkAnswer(2);
            viewCard12.setBackgroundResource(R.drawable.text_view_circle4);
            handler.postDelayed(() -> viewCard12.setBackgroundResource(R.drawable.text_view_circle3), 1000);
        });
        viewCard13.setOnClickListener(v -> {
            checkAnswer(3);
            viewCard13.setBackgroundResource(R.drawable.text_view_circle4);
            handler.postDelayed(() -> viewCard13.setBackgroundResource(R.drawable.text_view_circle3), 1000);
        });
        viewCard21.setOnClickListener(v -> {
            checkAnswer(4);
            viewCard21.setBackgroundResource(R.drawable.text_view_circle4);
            handler.postDelayed(() -> viewCard21.setBackgroundResource(R.drawable.text_view_circle3), 1000);
        });
        viewCard22.setOnClickListener(v -> {
            checkAnswer(5);
            viewCard22.setBackgroundResource(R.drawable.text_view_circle4);
            handler.postDelayed(() -> viewCard22.setBackgroundResource(R.drawable.text_view_circle3), 1000);
        });
        viewCard23.setOnClickListener(v -> {
            checkAnswer(6);
            viewCard23.setBackgroundResource(R.drawable.text_view_circle4);
            handler.postDelayed(() -> viewCard23.setBackgroundResource(R.drawable.text_view_circle3), 1000);
        });
        viewCard31.setOnClickListener(v -> {
            checkAnswer(7);
            viewCard31.setBackgroundResource(R.drawable.text_view_circle4);
            handler.postDelayed(() -> viewCard31.setBackgroundResource(R.drawable.text_view_circle3), 1000);
        });
        viewCard32.setOnClickListener(v -> {
            checkAnswer(8);
            viewCard32.setBackgroundResource(R.drawable.text_view_circle4);
            handler.postDelayed(() -> viewCard32.setBackgroundResource(R.drawable.text_view_circle3), 1000);
        });
        viewCard33.setOnClickListener(v -> {
            checkAnswer(9);
            viewCard33.setBackgroundResource(R.drawable.text_view_circle4);
            handler.postDelayed(() -> viewCard33.setBackgroundResource(R.drawable.text_view_circle3), 1000);
        });
    }


    public void pauseGame(View view) {
        if(!pause_flg) {
            pause_flg = true;
            setTempNotClickable();

            textViewPausedScoreLabel.setText("Score: " + score);

            textViewPausedLabel.setVisibility(View.VISIBLE);
            textViewPausedScoreLabel.setVisibility(View.VISIBLE);
            btn_Resume.setVisibility(View.VISIBLE);
            restart.setVisibility(View.VISIBLE);
            exit.setVisibility(View.VISIBLE);
        } else {
            pause_flg = false;
            setTempClickable();

            textViewPausedLabel.setVisibility(View.GONE);
            textViewPausedScoreLabel.setVisibility(View.GONE);
            btn_Resume.setVisibility(View.GONE);
            restart.setVisibility(View.GONE);
            exit.setVisibility(View.GONE);
        }
    }

    public void restartGame(View view) {
        MainActivity.gamePlaySound.stop();
        Intent intent = new Intent(getApplicationContext(), gameMatching_Level7.class);
        startActivity(intent);
    }

    public void exitGame(View view) {
        MainActivity.gamePlaySound.stop();
        mediaPlayerHandler.optimizeBGM(getApplicationContext(), 1);
        Intent intent = new Intent(getApplicationContext(), Homepage.class);
        startActivity(intent);
    }
}