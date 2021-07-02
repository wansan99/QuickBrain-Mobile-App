package com.example.mdproject;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
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

public class gameMatching_Level3 extends AppCompatActivity {
    private static final long COUNTDOWN_IN_MILLIS = 55000;
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

    private ImageView viewCard11, viewCard12, viewCard13, viewCard14, viewCard15, viewCard16;
    private ImageView viewCard21, viewCard22, viewCard23, viewCard24, viewCard25, viewCard26;
    private ImageView viewCard31, viewCard32, viewCard33, viewCard34, viewCard35, viewCard36;
    private ImageView viewCard41, viewCard42, viewCard43, viewCard44, viewCard45, viewCard46;

    Integer[] cardsArray = {101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112,
            201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212};

    int cards101, cards102, cards103, cards104, cards105, cards106, cards107, cards108, cards109, cards110, cards111, cards112,
            cards201, cards202, cards203, cards204, cards205, cards206, cards207, cards208, cards209, cards210, cards211, cards212;

    int firstCard, secondCard;
    int clickedFirst, clickedSecond;
    int cardNumber = 1;

    int score;
    double scoreMultiplier = 1.0;
    int streak = 0;
    private CountDownTimer countDownTimer;
    private CountDownTimer countDownToGameStart;
    private long timeLeftToStart = COUNTDOWN_TO_GAME_START_IN_MILLIS;
    private long timeLeftInMillis = COUNTDOWN_IN_MILLIS;
    boolean pause_flg = false;
    boolean win = false;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_matching_level3);

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

        viewCard11 = findViewById(R.id.viewCard11); viewCard12 = findViewById(R.id.viewCard12); viewCard13 = findViewById(R.id.viewCard13); viewCard14 = findViewById(R.id.viewCard14); viewCard15 = findViewById(R.id.viewCard15); viewCard16 = findViewById(R.id.viewCard16);
        viewCard21 = findViewById(R.id.viewCard21); viewCard22 = findViewById(R.id.viewCard22); viewCard23 = findViewById(R.id.viewCard23); viewCard24 = findViewById(R.id.viewCard24); viewCard25 = findViewById(R.id.viewCard25); viewCard26 = findViewById(R.id.viewCard26);
        viewCard31 = findViewById(R.id.viewCard31); viewCard32 = findViewById(R.id.viewCard32); viewCard33 = findViewById(R.id.viewCard33); viewCard34 = findViewById(R.id.viewCard34); viewCard35 = findViewById(R.id.viewCard35); viewCard36 = findViewById(R.id.viewCard36);
        viewCard41 = findViewById(R.id.viewCard41); viewCard42 = findViewById(R.id.viewCard42); viewCard43 = findViewById(R.id.viewCard43); viewCard44 = findViewById(R.id.viewCard44); viewCard45 = findViewById(R.id.viewCard45); viewCard46 = findViewById(R.id.viewCard46);

        viewCard11.setTag("0"); viewCard12.setTag("1"); viewCard13.setTag("2"); viewCard14.setTag("3"); viewCard15.setTag("4"); viewCard16.setTag("5");
        viewCard21.setTag("6"); viewCard22.setTag("7"); viewCard23.setTag("8"); viewCard24.setTag("9"); viewCard25.setTag("10"); viewCard26.setTag("11");
        viewCard31.setTag("12"); viewCard32.setTag("13"); viewCard33.setTag("14"); viewCard34.setTag("15"); viewCard35.setTag("16"); viewCard36.setTag("17");
        viewCard41.setTag("18"); viewCard42.setTag("19"); viewCard43.setTag("20"); viewCard44.setTag("21"); viewCard45.setTag("22"); viewCard46.setTag("23");

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
                textViewGameDescription.setText("Match Them All!");
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

                frontOfCardsResources();
                Collections.shuffle(Arrays.asList(cardsArray));
                setViewClickable();
                startCountDown();
            }
        }.start();
    }

    private void startCountDown() {
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

    private void frontOfCardsResources() {
        cards101 = R.drawable.ic_image101;
        cards102 = R.drawable.ic_image102;
        cards103 = R.drawable.ic_image103;
        cards104 = R.drawable.ic_image104;
        cards105 = R.drawable.ic_image105;
        cards106 = R.drawable.ic_image106;
        cards107 = R.drawable.ic_image107;
        cards108 = R.drawable.ic_image108;
        cards109 = R.drawable.ic_image109;
        cards110 = R.drawable.ic_image110;
        cards111 = R.drawable.ic_image111;
        cards112 = R.drawable.ic_image112;
        cards201 = R.drawable.ic_image101;
        cards202 = R.drawable.ic_image102;
        cards203 = R.drawable.ic_image103;
        cards204 = R.drawable.ic_image104;
        cards205 = R.drawable.ic_image105;
        cards206 = R.drawable.ic_image106;
        cards207 = R.drawable.ic_image107;
        cards208 = R.drawable.ic_image108;
        cards209 = R.drawable.ic_image109;
        cards210 = R.drawable.ic_image110;
        cards211 = R.drawable.ic_image111;
        cards212 = R.drawable.ic_image112;
    }

    private void cardSelection(ImageView iv, int card) {
        switch(cardsArray[card]) {
            case 101:
                iv.setImageResource(cards101);
                break;
            case 102:
                iv.setImageResource(cards102);
                break;
            case 103:
                iv.setImageResource(cards103);
                break;
            case 104:
                iv.setImageResource(cards104);
                break;
            case 105:
                iv.setImageResource(cards105);
                break;
            case 106:
                iv.setImageResource(cards106);
                break;
            case 107:
                iv.setImageResource(cards107);
                break;
            case 108:
                iv.setImageResource(cards108);
                break;
            case 109:
                iv.setImageResource(cards109);
                break;
            case 110:
                iv.setImageResource(cards110);
                break;
            case 111:
                iv.setImageResource(cards111);
                break;
            case 112:
                iv.setImageResource(cards112);
                break;
            case 201:
                iv.setImageResource(cards201);
                break;
            case 202:
                iv.setImageResource(cards202);
                break;
            case 203:
                iv.setImageResource(cards203);
                break;
            case 204:
                iv.setImageResource(cards204);
                break;
            case 205:
                iv.setImageResource(cards205);
                break;
            case 206:
                iv.setImageResource(cards206);
                break;
            case 207:
                iv.setImageResource(cards207);
                break;
            case 208:
                iv.setImageResource(cards208);
                break;
            case 209:
                iv.setImageResource(cards209);
                break;
            case 210:
                iv.setImageResource(cards210);
                break;
            case 211:
                iv.setImageResource(cards211);
                break;
            case 212:
                iv.setImageResource(cards212);
                break;
        }

        if(cardNumber == 1) {
            firstCard = cardsArray[card];
            if(firstCard > 200) {
                firstCard = firstCard - 100;
            }
            cardNumber = 2;
            clickedFirst = card;

            iv.setEnabled(false);
        } else if(cardNumber == 2) {
            secondCard = cardsArray[card];
            if(secondCard > 200) {
                secondCard = secondCard - 100;
            }
            cardNumber = 1;
            clickedSecond = card;
            setViewTempNotClickable();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    calculate();
                }
            }, 500);
        }
    }

    private void calculate() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.wrong_correct_animation);
        if(firstCard == secondCard) {
            if(clickedFirst == 0) viewCard11.setVisibility(View.INVISIBLE);
            else if (clickedFirst == 1) viewCard12.setVisibility(View.INVISIBLE);
            else if (clickedFirst == 2) viewCard13.setVisibility(View.INVISIBLE);
            else if (clickedFirst == 3) viewCard14.setVisibility(View.INVISIBLE);
            else if (clickedFirst == 4) viewCard15.setVisibility(View.INVISIBLE);
            else if (clickedFirst == 5) viewCard16.setVisibility(View.INVISIBLE);
            else if (clickedFirst == 6) viewCard21.setVisibility(View.INVISIBLE);
            else if (clickedFirst == 7) viewCard22.setVisibility(View.INVISIBLE);
            else if (clickedFirst == 8) viewCard23.setVisibility(View.INVISIBLE);
            else if (clickedFirst == 9) viewCard24.setVisibility(View.INVISIBLE);
            else if (clickedFirst == 10) viewCard25.setVisibility(View.INVISIBLE);
            else if (clickedFirst == 11) viewCard26.setVisibility(View.INVISIBLE);
            else if (clickedFirst == 12) viewCard31.setVisibility(View.INVISIBLE);
            else if (clickedFirst == 13) viewCard32.setVisibility(View.INVISIBLE);
            else if (clickedFirst == 14) viewCard33.setVisibility(View.INVISIBLE);
            else if (clickedFirst == 15) viewCard34.setVisibility(View.INVISIBLE);
            else if (clickedFirst == 16) viewCard35.setVisibility(View.INVISIBLE);
            else if (clickedFirst == 17) viewCard36.setVisibility(View.INVISIBLE);
            else if (clickedFirst == 18) viewCard41.setVisibility(View.INVISIBLE);
            else if (clickedFirst == 19) viewCard42.setVisibility(View.INVISIBLE);
            else if (clickedFirst == 20) viewCard43.setVisibility(View.INVISIBLE);
            else if (clickedFirst == 21) viewCard44.setVisibility(View.INVISIBLE);
            else if (clickedFirst == 22) viewCard45.setVisibility(View.INVISIBLE);
            else if (clickedFirst == 23) viewCard46.setVisibility(View.INVISIBLE);

            if(clickedSecond == 0) viewCard11.setVisibility(View.INVISIBLE);
            else if (clickedSecond == 1) viewCard12.setVisibility(View.INVISIBLE);
            else if (clickedSecond == 2) viewCard13.setVisibility(View.INVISIBLE);
            else if (clickedSecond == 3) viewCard14.setVisibility(View.INVISIBLE);
            else if (clickedSecond == 4) viewCard15.setVisibility(View.INVISIBLE);
            else if (clickedSecond == 5) viewCard16.setVisibility(View.INVISIBLE);
            else if (clickedSecond == 6) viewCard21.setVisibility(View.INVISIBLE);
            else if (clickedSecond == 7) viewCard22.setVisibility(View.INVISIBLE);
            else if (clickedSecond == 8) viewCard23.setVisibility(View.INVISIBLE);
            else if (clickedSecond == 9) viewCard24.setVisibility(View.INVISIBLE);
            else if (clickedSecond == 10) viewCard25.setVisibility(View.INVISIBLE);
            else if (clickedSecond == 11) viewCard26.setVisibility(View.INVISIBLE);
            else if (clickedSecond == 12) viewCard31.setVisibility(View.INVISIBLE);
            else if (clickedSecond == 13) viewCard32.setVisibility(View.INVISIBLE);
            else if (clickedSecond == 14) viewCard33.setVisibility(View.INVISIBLE);
            else if (clickedSecond == 15) viewCard34.setVisibility(View.INVISIBLE);
            else if (clickedSecond == 16) viewCard35.setVisibility(View.INVISIBLE);
            else if (clickedSecond == 17) viewCard36.setVisibility(View.INVISIBLE);
            else if (clickedSecond == 18) viewCard41.setVisibility(View.INVISIBLE);
            else if (clickedSecond == 19) viewCard42.setVisibility(View.INVISIBLE);
            else if (clickedSecond == 20) viewCard43.setVisibility(View.INVISIBLE);
            else if (clickedSecond == 21) viewCard44.setVisibility(View.INVISIBLE);
            else if (clickedSecond == 22) viewCard45.setVisibility(View.INVISIBLE);
            else if (clickedSecond == 23) viewCard46.setVisibility(View.INVISIBLE);

            imgCorrect.setVisibility(View.VISIBLE);
            imgCorrect.startAnimation(animation);

            streak++;
            if(streak > 1) scoreMultiplier += 0.2;
            else scoreMultiplier = 1.0;
            score = (int) (score + (scoreMultiplier * 1000));
            textViewScore.setText("Score: " + score);
            MainActivity.correctSound.start();
        } else {
            viewCard11.setImageResource(R.drawable.ic_back);
            viewCard12.setImageResource(R.drawable.ic_back);
            viewCard13.setImageResource(R.drawable.ic_back);
            viewCard14.setImageResource(R.drawable.ic_back);
            viewCard15.setImageResource(R.drawable.ic_back);
            viewCard16.setImageResource(R.drawable.ic_back);
            viewCard21.setImageResource(R.drawable.ic_back);
            viewCard22.setImageResource(R.drawable.ic_back);
            viewCard23.setImageResource(R.drawable.ic_back);
            viewCard24.setImageResource(R.drawable.ic_back);
            viewCard25.setImageResource(R.drawable.ic_back);
            viewCard26.setImageResource(R.drawable.ic_back);
            viewCard31.setImageResource(R.drawable.ic_back);
            viewCard32.setImageResource(R.drawable.ic_back);
            viewCard33.setImageResource(R.drawable.ic_back);
            viewCard34.setImageResource(R.drawable.ic_back);
            viewCard35.setImageResource(R.drawable.ic_back);
            viewCard36.setImageResource(R.drawable.ic_back);
            viewCard41.setImageResource(R.drawable.ic_back);
            viewCard42.setImageResource(R.drawable.ic_back);
            viewCard43.setImageResource(R.drawable.ic_back);
            viewCard44.setImageResource(R.drawable.ic_back);
            viewCard45.setImageResource(R.drawable.ic_back);
            viewCard46.setImageResource(R.drawable.ic_back);
            streak = 0;
            imgWrong.setVisibility(View.VISIBLE);
            imgWrong.startAnimation(animation);
            MainActivity.wrongSound.start();
        }
        setViewTempClickable();

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
        Intent intent = new Intent(getApplicationContext(), gameMatching_Level3.class);
        startActivity(intent);
    }

    public void exitGame(View view) {
        MainActivity.gamePlaySound.stop();
        mediaPlayerHandler.optimizeBGM(getApplicationContext(), 1);
        Intent intent = new Intent(getApplicationContext(), Homepage.class);
        startActivity(intent);
    }

    private void checkEnd(boolean passed) {
        if(viewCard11.getVisibility() == View.INVISIBLE &&
                viewCard12.getVisibility() == View.INVISIBLE &&
                viewCard13.getVisibility() == View.INVISIBLE &&
                viewCard14.getVisibility() == View.INVISIBLE &&
                viewCard15.getVisibility() == View.INVISIBLE &&
                viewCard16.getVisibility() == View.INVISIBLE &&
                viewCard21.getVisibility() == View.INVISIBLE &&
                viewCard22.getVisibility() == View.INVISIBLE &&
                viewCard23.getVisibility() == View.INVISIBLE &&
                viewCard24.getVisibility() == View.INVISIBLE &&
                viewCard25.getVisibility() == View.INVISIBLE &&
                viewCard26.getVisibility() == View.INVISIBLE &&
                viewCard31.getVisibility() == View.INVISIBLE &&
                viewCard32.getVisibility() == View.INVISIBLE &&
                viewCard33.getVisibility() == View.INVISIBLE &&
                viewCard34.getVisibility() == View.INVISIBLE &&
                viewCard35.getVisibility() == View.INVISIBLE &&
                viewCard36.getVisibility() == View.INVISIBLE &&
                viewCard41.getVisibility() == View.INVISIBLE &&
                viewCard42.getVisibility() == View.INVISIBLE &&
                viewCard43.getVisibility() == View.INVISIBLE &&
                viewCard44.getVisibility() == View.INVISIBLE &&
                viewCard45.getVisibility() == View.INVISIBLE &&
                viewCard46.getVisibility() == View.INVISIBLE ) {
                passed = true;
                win = true;
                countDownTimer.cancel();
                score = (int) (score + timeLeftInMillis);
                textViewScore.setText("Score: " + score);
        }
        if(passed) {
            MainActivity.gamePlaySound.stop();
            countDownTimer.cancel();
            QBDataBase db = new QBDataBase(this);
            SessionManager sessionManager = new SessionManager(this);
            HashMap<String, String> userInfo = sessionManager.getUserDataFromSession();
            int tempScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MGS3));
            if(tempScore < score) {
                sessionManager.setKeyMgs3(score);
                boolean isUpdated = db.updateScore(12, score, userInfo.get(SessionManager.KEY_USERNAME));
                if(isUpdated) Toast.makeText(getApplicationContext(), "New HighScore", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(getApplicationContext(), gameResult.class);
            intent.putExtra("Level", "3");
            intent.putExtra("Score", Integer.toString(score));
            intent.putExtra("GameType", "Memory Game");
            intent.putExtra("Win", String.valueOf(win));
            startActivity(intent);
        }
    }

    private void setViewTempNotClickable() {
        viewCard11.setEnabled(false); viewCard12.setEnabled(false); viewCard13.setEnabled(false); viewCard14.setEnabled(false); viewCard15.setEnabled(false); viewCard16.setEnabled(false);
        viewCard21.setEnabled(false); viewCard22.setEnabled(false); viewCard23.setEnabled(false); viewCard24.setEnabled(false); viewCard25.setEnabled(false); viewCard26.setEnabled(false);
        viewCard31.setEnabled(false); viewCard32.setEnabled(false); viewCard33.setEnabled(false); viewCard34.setEnabled(false); viewCard35.setEnabled(false); viewCard36.setEnabled(false);
        viewCard41.setEnabled(false); viewCard42.setEnabled(false); viewCard43.setEnabled(false); viewCard44.setEnabled(false); viewCard45.setEnabled(false); viewCard46.setEnabled(false);
    }

    private void setViewTempClickable() {
        viewCard11.setEnabled(true);
        viewCard12.setEnabled(true);
        viewCard13.setEnabled(true);
        viewCard14.setEnabled(true);
        viewCard15.setEnabled(true);
        viewCard16.setEnabled(true);
        viewCard21.setEnabled(true);
        viewCard22.setEnabled(true);
        viewCard23.setEnabled(true);
        viewCard24.setEnabled(true);
        viewCard25.setEnabled(true);
        viewCard26.setEnabled(true);
        viewCard31.setEnabled(true);
        viewCard32.setEnabled(true);
        viewCard33.setEnabled(true);
        viewCard34.setEnabled(true);
        viewCard35.setEnabled(true);
        viewCard36.setEnabled(true);
        viewCard41.setEnabled(true);
        viewCard42.setEnabled(true);
        viewCard43.setEnabled(true);
        viewCard44.setEnabled(true);
        viewCard45.setEnabled(true);
        viewCard46.setEnabled(true);
    }

    private void setViewClickable() {
        viewCard11.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) viewCard11.getTag());
            cardSelection(viewCard11, theCard);
        });

        viewCard12.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) viewCard12.getTag());
            cardSelection(viewCard12, theCard);

        });

        viewCard13.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) viewCard13.getTag());
            cardSelection(viewCard13, theCard);

        });

        viewCard14.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) viewCard14.getTag());
            cardSelection(viewCard14, theCard);

        });

        viewCard15.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) viewCard15.getTag());
            cardSelection(viewCard15, theCard);

        });

        viewCard16.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) viewCard16.getTag());
            cardSelection(viewCard16, theCard);

        });

        viewCard21.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) viewCard21.getTag());
            cardSelection(viewCard21, theCard);

        });

        viewCard22.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) viewCard22.getTag());
            cardSelection(viewCard22, theCard);

        });

        viewCard23.setOnClickListener(v -> {

            int theCard = Integer.parseInt((String) viewCard23.getTag());
            cardSelection(viewCard23, theCard);
        });

        viewCard24.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) viewCard24.getTag());
            cardSelection(viewCard24, theCard);

        });

        viewCard25.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) viewCard25.getTag());
            cardSelection(viewCard25, theCard);

        });

        viewCard26.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) viewCard26.getTag());
            cardSelection(viewCard26, theCard);

        });

        viewCard31.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) viewCard31.getTag());
            cardSelection(viewCard31, theCard);

        });

        viewCard32.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) viewCard32.getTag());
            cardSelection(viewCard32, theCard);

        });

        viewCard33.setOnClickListener(v -> {

            int theCard = Integer.parseInt((String) viewCard33.getTag());
            cardSelection(viewCard33, theCard);
        });

        viewCard34.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) viewCard34.getTag());
            cardSelection(viewCard34, theCard);

        });

        viewCard35.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) viewCard35.getTag());
            cardSelection(viewCard35, theCard);

        });

        viewCard36.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) viewCard36.getTag());
            cardSelection(viewCard36, theCard);

        });

        viewCard41.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) viewCard41.getTag());
            cardSelection(viewCard41, theCard);

        });

        viewCard42.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) viewCard42.getTag());
            cardSelection(viewCard42, theCard);

        });

        viewCard43.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) viewCard43.getTag());
            cardSelection(viewCard43, theCard);

        });

        viewCard44.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) viewCard44.getTag());
            cardSelection(viewCard44, theCard);
        });

        viewCard45.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) viewCard45.getTag());
            cardSelection(viewCard45, theCard);
        });

        viewCard46.setOnClickListener(v -> {
            int theCard = Integer.parseInt((String) viewCard46.getTag());
            cardSelection(viewCard46, theCard);
        });
    }
}