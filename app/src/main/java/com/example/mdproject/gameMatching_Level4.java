package com.example.mdproject;

import android.content.Intent;
import android.graphics.Color;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;

public class gameMatching_Level4 extends AppCompatActivity implements MemoryAnswerDialog.MemoryAnswerDialogListener {
    private static final long COUNTDOWN_IN_MILLIS = 45000;
    private static final long COUNTDOWN_FOR_MEMORIES_IN_MILLIS = 30000;
    private static final long COUNTDOWN_TO_GAME_START_IN_MILLIS = 3000;

    private ImageButton pause;
    private ProgressBar pbGame;
    private TextView timer;
    private TextView textViewScore;
    private TextView textViewPausedLabel;
    private TextView textViewPausedScoreLabel;
    private TextView textViewCountDownToGameStart;
    private TextView textViewGameDescription;
    private Button btn_Resume, restart, exit;
    private ImageView imgCorrect, imgWrong;

    private TextView textViewCard11, textViewCard12, textViewCard13, textViewCard14;
    private TextView textViewCard21, textViewCard22, textViewCard23, textViewCard24;
    private TextView textViewCard31, textViewCard32, textViewCard33, textViewCard34;
    private TextView textViewCard41, textViewCard42, textViewCard43, textViewCard44;

    Integer[] numberArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};

    int score;
    double scoreMultiplier = 1.0;
    int streak = 0;
    private CountDownTimer countDownTimer;
    private CountDownTimer countDownToGameStart;
    private long timeLeftToStart = COUNTDOWN_TO_GAME_START_IN_MILLIS;
    private long timeLeftInMillis = COUNTDOWN_FOR_MEMORIES_IN_MILLIS;
    boolean pause_flg = false;
    boolean win = false;
    boolean gameStart = false;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_matching_level4);

        pause = findViewById(R.id.btn_pause);
        restart = findViewById(R.id.btn_Restart);
        exit = findViewById(R.id.btn_Exit);
        pbGame = findViewById(R.id.progressBar);
        timer = findViewById(R.id.tv_timer);
        textViewScore = findViewById(R.id.textViewScore);
        textViewPausedLabel = findViewById(R.id.textViewPausedLabel);
        textViewPausedScoreLabel = findViewById(R.id.textViewPausedScoreLabel);
        textViewCountDownToGameStart = findViewById(R.id.textViewCountDownToGameStart);
        textViewGameDescription = findViewById(R.id.textViewGameDescription);
        btn_Resume = findViewById(R.id.btn_Resume);
        imgCorrect = findViewById(R.id.ic_correct);
        imgWrong = findViewById(R.id.ic_wrong);

        textViewCard11 = findViewById(R.id.textViewCard11); textViewCard12 = findViewById(R.id.textViewCard12); textViewCard13 = findViewById(R.id.textViewCard13); textViewCard14 = findViewById(R.id.textViewCard14);
        textViewCard21 = findViewById(R.id.textViewCard21); textViewCard22 = findViewById(R.id.textViewCard22); textViewCard23 = findViewById(R.id.textViewCard23); textViewCard24 = findViewById(R.id.textViewCard24);
        textViewCard31 = findViewById(R.id.textViewCard31); textViewCard32 = findViewById(R.id.textViewCard32); textViewCard33 = findViewById(R.id.textViewCard33); textViewCard34 = findViewById(R.id.textViewCard34);
        textViewCard41 = findViewById(R.id.textViewCard41); textViewCard42 = findViewById(R.id.textViewCard42); textViewCard43 = findViewById(R.id.textViewCard43); textViewCard44 = findViewById(R.id.textViewCard44);

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
                textViewGameDescription.setText("Memorise Them All!");
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
                Collections.shuffle(Arrays.asList(numberArray));
                assignTextViewAnswer();
            }
        }.start();
    }

    private void assignTextViewAnswer() {
        textViewCard11.setText(Integer.toString(numberArray[0])); textViewCard12.setText(Integer.toString(numberArray[1])); textViewCard13.setText(Integer.toString(numberArray[2])); textViewCard14.setText(Integer.toString(numberArray[3]));
        textViewCard21.setText(Integer.toString(numberArray[4])); textViewCard22.setText(Integer.toString(numberArray[5])); textViewCard23.setText(Integer.toString(numberArray[6])); textViewCard24.setText(Integer.toString(numberArray[7]));
        textViewCard31.setText(Integer.toString(numberArray[8])); textViewCard32.setText(Integer.toString(numberArray[9])); textViewCard33.setText(Integer.toString(numberArray[10])); textViewCard34.setText(Integer.toString(numberArray[11]));
        textViewCard41.setText(Integer.toString(numberArray[12])); textViewCard42.setText(Integer.toString(numberArray[13])); textViewCard43.setText(Integer.toString(numberArray[14])); textViewCard44.setText(Integer.toString(numberArray[15]));

        timeLeftInMillis = COUNTDOWN_FOR_MEMORIES_IN_MILLIS;
        startCountDown();
    }

    private void startCountDown() {
        if(!gameStart) {
            countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeftInMillis = millisUntilFinished;
                    pbGame.setProgress((int) (((double) timeLeftInMillis/ (double) COUNTDOWN_FOR_MEMORIES_IN_MILLIS)*100));
                    updateCountDownText();
                }

                @Override
                public void onFinish() {
                    timeLeftInMillis = 0;
                    pbGame.setProgress(0);
                    updateCountDownText();
                    gameStart = true;
                    hideAnswer();
                    setTextViewClickable();
                    timeLeftInMillis = COUNTDOWN_IN_MILLIS;
                    startCountDown();
                }
            }.start();
        } else {
            countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeftInMillis = millisUntilFinished;
                    pbGame.setProgress((int) (((double) timeLeftInMillis/ (double) COUNTDOWN_IN_MILLIS)*100));
                    updateCountDownText();
                }

                @Override
                public void onFinish() {
                    timeLeftInMillis = 0;
                    pbGame.setProgress(0);
                    updateCountDownText();
                    checkEnd(true);
                }
            }.start();
        }
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timer.setText(timeFormatted);

        if(timeLeftInMillis < 10000) {
            timer.setTextColor(Color.RED);
        } else {
            timer.setTextColor(Color.BLACK);
        }
    }

    private void hideAnswer() {
        textViewCard11.setText(""); textViewCard12.setText(""); textViewCard13.setText(""); textViewCard14.setText("");
        textViewCard21.setText(""); textViewCard22.setText(""); textViewCard23.setText(""); textViewCard24.setText("");
        textViewCard31.setText(""); textViewCard32.setText(""); textViewCard33.setText(""); textViewCard34.setText("");
        textViewCard41.setText(""); textViewCard42.setText(""); textViewCard43.setText(""); textViewCard44.setText("");

        textViewCard11.setBackgroundResource(R.drawable.text_view_circle2); textViewCard12.setBackgroundResource(R.drawable.text_view_circle3); textViewCard13.setBackgroundResource(R.drawable.text_view_circle2); textViewCard14.setBackgroundResource(R.drawable.text_view_circle3);
        textViewCard21.setBackgroundResource(R.drawable.text_view_circle3); textViewCard22.setBackgroundResource(R.drawable.text_view_circle2); textViewCard23.setBackgroundResource(R.drawable.text_view_circle3); textViewCard24.setBackgroundResource(R.drawable.text_view_circle2);
        textViewCard31.setBackgroundResource(R.drawable.text_view_circle2); textViewCard32.setBackgroundResource(R.drawable.text_view_circle3); textViewCard33.setBackgroundResource(R.drawable.text_view_circle2); textViewCard34.setBackgroundResource(R.drawable.text_view_circle3);
        textViewCard41.setBackgroundResource(R.drawable.text_view_circle3); textViewCard42.setBackgroundResource(R.drawable.text_view_circle2); textViewCard43.setBackgroundResource(R.drawable.text_view_circle3); textViewCard44.setBackgroundResource(R.drawable.text_view_circle2);
    }

    private void setTextViewClickable() {
        textViewCard11.setOnClickListener(v -> launchTypableDialog(0));
        textViewCard12.setOnClickListener(v -> launchTypableDialog(1));
        textViewCard13.setOnClickListener(v -> launchTypableDialog(2));
        textViewCard14.setOnClickListener(v -> launchTypableDialog(3));
        textViewCard21.setOnClickListener(v -> launchTypableDialog(4));
        textViewCard22.setOnClickListener(v -> launchTypableDialog(5));
        textViewCard23.setOnClickListener(v -> launchTypableDialog(6));
        textViewCard24.setOnClickListener(v -> launchTypableDialog(7));
        textViewCard31.setOnClickListener(v -> launchTypableDialog(8));
        textViewCard32.setOnClickListener(v -> launchTypableDialog(9));
        textViewCard33.setOnClickListener(v -> launchTypableDialog(10));
        textViewCard34.setOnClickListener(v -> launchTypableDialog(11));
        textViewCard41.setOnClickListener(v -> launchTypableDialog(12));
        textViewCard42.setOnClickListener(v -> launchTypableDialog(13));
        textViewCard43.setOnClickListener(v -> launchTypableDialog(14));
        textViewCard44.setOnClickListener(v -> launchTypableDialog(15));
    }

    private void launchTypableDialog(int cellAnswer) {
        MemoryAnswerDialog dialog = MemoryAnswerDialog.newInstance(cellAnswer);
        dialog.show(getSupportFragmentManager(), "Answer Dialog");
    }

    @Override
    public void checkAnswer(String answer, int cellPosition) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.wrong_correct_animation);
        int inputtedAnswer;
        if(answer.equals("") || answer.equals(null)){
            inputtedAnswer = 0;
        }else {
            inputtedAnswer = Integer.parseInt(answer);
        }
        if(numberArray[cellPosition] == inputtedAnswer) {
            if(cellPosition == 0) {
                textViewCard11.setText(answer);
                textViewCard11.setTextColor(Color.GREEN);
                textViewCard11.setEnabled(false);
            }
            else if (cellPosition == 1) {
                textViewCard12.setText(answer);
                textViewCard12.setTextColor(Color.GREEN);
                textViewCard12.setEnabled(false);
            }
            else if (cellPosition == 2) {
                textViewCard13.setText(answer);
                textViewCard13.setTextColor(Color.GREEN);
                textViewCard13.setEnabled(false);
            }
            else if (cellPosition == 3) {
                textViewCard14.setText(answer);
                textViewCard14.setTextColor(Color.GREEN);
                textViewCard14.setEnabled(false);
            }
            else if (cellPosition == 4) {
                textViewCard21.setText(answer);
                textViewCard21.setTextColor(Color.GREEN);
                textViewCard21.setEnabled(false);
            }
            else if (cellPosition == 5) {
                textViewCard22.setText(answer);
                textViewCard22.setTextColor(Color.GREEN);
                textViewCard22.setEnabled(false);
            }
            else if (cellPosition == 6) {
                textViewCard23.setText(answer);
                textViewCard23.setTextColor(Color.GREEN);
                textViewCard23.setEnabled(false);
            }
            else if (cellPosition == 7) {
                textViewCard24.setText(answer);
                textViewCard24.setTextColor(Color.GREEN);
                textViewCard24.setEnabled(false);
            }
            else if (cellPosition == 8) {
                textViewCard31.setText(answer);
                textViewCard31.setTextColor(Color.GREEN);
                textViewCard31.setEnabled(false);
            }
            else if (cellPosition == 9) {
                textViewCard32.setText(answer);
                textViewCard32.setTextColor(Color.GREEN);
                textViewCard32.setEnabled(false);
            }
            else if (cellPosition == 10) {
                textViewCard33.setText(answer);
                textViewCard33.setTextColor(Color.GREEN);
                textViewCard33.setEnabled(false);
            }
            else if (cellPosition == 11) {
                textViewCard34.setText(answer);
                textViewCard34.setTextColor(Color.GREEN);
                textViewCard34.setEnabled(false);
            }
            else if (cellPosition == 12) {
                textViewCard41.setText(answer);
                textViewCard41.setTextColor(Color.GREEN);
                textViewCard41.setEnabled(false);
            }
            else if (cellPosition == 13) {
                textViewCard42.setText(answer);
                textViewCard42.setTextColor(Color.GREEN);
                textViewCard42.setEnabled(false);
            }
            else if (cellPosition == 14) {
                textViewCard43.setText(answer);
                textViewCard43.setTextColor(Color.GREEN);
                textViewCard43.setEnabled(false);
            }
            else if (cellPosition == 15) {
                textViewCard44.setText(answer);
                textViewCard44.setTextColor(Color.GREEN);
                textViewCard44.setEnabled(false);
            }

            imgCorrect.setVisibility(View.VISIBLE);
            imgCorrect.startAnimation(animation);
            MainActivity.correctSound.start();
            streak++;
            if(streak > 1) scoreMultiplier += 0.2;
            else scoreMultiplier = 1.0;
            score = (int) (score + (scoreMultiplier * 1000));
            textViewScore.setText("Score: " + score);
        } else {
            if(cellPosition == 0) {
                textViewCard11.setText("" + numberArray[0]);
                textViewCard11.setTextColor(Color.RED);
                textViewCard11.setEnabled(false);
            }
            else if (cellPosition == 1) {
                textViewCard12.setText("" + numberArray[1]);
                textViewCard12.setTextColor(Color.RED);
                textViewCard12.setEnabled(false);
            }
            else if (cellPosition == 2) {
                textViewCard13.setText("" + numberArray[2]);
                textViewCard13.setTextColor(Color.RED);
                textViewCard13.setEnabled(false);
            }
            else if (cellPosition == 3) {
                textViewCard14.setText("" + numberArray[3]);
                textViewCard14.setTextColor(Color.RED);
                textViewCard14.setEnabled(false);
            }
            else if (cellPosition == 4) {
                textViewCard21.setText("" + numberArray[4]);
                textViewCard21.setTextColor(Color.RED);
                textViewCard21.setEnabled(false);
            }
            else if (cellPosition == 5) {
                textViewCard22.setText("" + numberArray[5]);
                textViewCard22.setTextColor(Color.RED);
                textViewCard22.setEnabled(false);
            }
            else if (cellPosition == 6) {
                textViewCard23.setText("" + numberArray[6]);
                textViewCard23.setTextColor(Color.RED);
                textViewCard23.setEnabled(false);
            }
            else if (cellPosition == 7) {
                textViewCard24.setText("" + numberArray[7]);
                textViewCard24.setTextColor(Color.RED);
                textViewCard24.setEnabled(false);
            }
            else if (cellPosition == 8) {
                textViewCard31.setText("" + numberArray[8]);
                textViewCard31.setTextColor(Color.RED);
                textViewCard31.setEnabled(false);
            }
            else if (cellPosition == 9) {
                textViewCard32.setText("" + numberArray[9]);
                textViewCard32.setTextColor(Color.RED);
                textViewCard32.setEnabled(false);
            }
            else if (cellPosition == 10) {
                textViewCard33.setText("" + numberArray[10]);
                textViewCard33.setTextColor(Color.RED);
                textViewCard33.setEnabled(false);
            }
            else if (cellPosition == 11) {
                textViewCard34.setText("" + numberArray[11]);
                textViewCard34.setTextColor(Color.RED);
                textViewCard34.setEnabled(false);
            }
            else if (cellPosition == 12) {
                textViewCard41.setText("" + numberArray[12]);
                textViewCard41.setTextColor(Color.RED);
                textViewCard41.setEnabled(false);
            }
            else if (cellPosition == 13) {
                textViewCard42.setText("" + numberArray[13]);
                textViewCard42.setTextColor(Color.RED);
                textViewCard42.setEnabled(false);
            }
            else if (cellPosition == 14) {
                textViewCard43.setText("" + numberArray[14]);
                textViewCard43.setTextColor(Color.RED);
                textViewCard43.setEnabled(false);
            }
            else if (cellPosition == 15) {
                textViewCard44.setText("" + numberArray[15]);
                textViewCard44.setTextColor(Color.RED);
                textViewCard44.setEnabled(false);
            }
            streak = 0;
            imgWrong.setVisibility(View.VISIBLE);
            imgWrong.startAnimation(animation);
            MainActivity.wrongSound.start();
        }
        checkEnd(false);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imgCorrect.setVisibility(View.GONE);
                imgWrong.setVisibility(View.GONE);
            }
        }, 1000);
    }

    public void pauseGame(View view) {
        if(!pause_flg) {
            pause_flg = true;
            setViewTempNotClickable();

            countDownTimer.cancel();
            countDownTimer = null;
            textViewPausedScoreLabel.setText("Score: " + score);

            textViewPausedLabel.setVisibility(View.VISIBLE);
            textViewPausedScoreLabel.setVisibility(View.VISIBLE);
            btn_Resume.setVisibility(View.VISIBLE);
            restart.setVisibility(View.VISIBLE);
            exit.setVisibility(View.VISIBLE);
        } else {
            pause_flg = false;
            setViewTempClickable();

            textViewPausedLabel.setVisibility(View.GONE);
            textViewPausedScoreLabel.setVisibility(View.GONE);
            btn_Resume.setVisibility(View.GONE);
            restart.setVisibility(View.GONE);
            exit.setVisibility(View.GONE);
            startCountDown();
        }
    }

    public void restartGame(View view) {
        MainActivity.gamePlaySound.stop();
        Intent intent = new Intent(getApplicationContext(), gameMatching_Level4.class);
        startActivity(intent);
    }

    public void exitGame(View view) {
        MainActivity.gamePlaySound.stop();
        mediaPlayerHandler.optimizeBGM(getApplicationContext(), 1);
        Intent intent = new Intent(getApplicationContext(), Homepage.class);
        startActivity(intent);
    }

    private void setViewTempNotClickable() {
        textViewCard11.setEnabled(false);
        textViewCard12.setEnabled(false);
        textViewCard13.setEnabled(false);
        textViewCard14.setEnabled(false);
        textViewCard21.setEnabled(false);
        textViewCard22.setEnabled(false);
        textViewCard23.setEnabled(false);
        textViewCard24.setEnabled(false);
        textViewCard31.setEnabled(false);
        textViewCard32.setEnabled(false);
        textViewCard33.setEnabled(false);
        textViewCard34.setEnabled(false);
        textViewCard41.setEnabled(false);
        textViewCard42.setEnabled(false);
        textViewCard43.setEnabled(false);
        textViewCard44.setEnabled(false);
    }

    private void setViewTempClickable() {
        textViewCard11.setEnabled(true);
        textViewCard12.setEnabled(true);
        textViewCard13.setEnabled(true);
        textViewCard14.setEnabled(true);
        textViewCard21.setEnabled(true);
        textViewCard22.setEnabled(true);
        textViewCard23.setEnabled(true);
        textViewCard24.setEnabled(true);
        textViewCard31.setEnabled(true);
        textViewCard32.setEnabled(true);
        textViewCard33.setEnabled(true);
        textViewCard34.setEnabled(true);
        textViewCard41.setEnabled(true);
        textViewCard42.setEnabled(true);
        textViewCard43.setEnabled(true);
        textViewCard44.setEnabled(true);
    }

    private void checkEnd(boolean passed) {
        if(!textViewCard11.getText().equals("") &&
                !textViewCard12.getText().equals("") &&
                !textViewCard13.getText().equals("") &&
                !textViewCard14.getText().equals("") &&
                !textViewCard21.getText().equals("") &&
                !textViewCard22.getText().equals("") &&
                !textViewCard23.getText().equals("") &&
                !textViewCard24.getText().equals("") &&
                !textViewCard31.getText().equals("") &&
                !textViewCard32.getText().equals("") &&
                !textViewCard33.getText().equals("") &&
                !textViewCard34.getText().equals("") &&
                !textViewCard41.getText().equals("") &&
                !textViewCard42.getText().equals("") &&
                !textViewCard43.getText().equals("") &&
                !textViewCard44.getText().equals("")) {
                passed = true;
                win = true;
                countDownTimer.cancel();
                textViewScore.setText("Score: " + score);
        }
        if(passed) {
            MainActivity.gamePlaySound.stop();
            countDownTimer.cancel();
            QBDataBase db = new QBDataBase(this);
            SessionManager sessionManager = new SessionManager(this);
            HashMap<String, String> userInfo = sessionManager.getUserDataFromSession();
            int tempScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MGS4));
            if(tempScore < score) {
                sessionManager.setKeyMgs4(score);
                boolean isUpdated = db.updateScore(13, score, userInfo.get(SessionManager.KEY_USERNAME));
                if(isUpdated) Toast.makeText(getApplicationContext(), "New HighScore", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(getApplicationContext(), gameResult.class);
            intent.putExtra("Level", "4");
            intent.putExtra("Score", Integer.toString(score));
            intent.putExtra("GameType", "Memory Game");
            intent.putExtra("Win", String.valueOf(win));
            startActivity(intent);
        }
    }
}