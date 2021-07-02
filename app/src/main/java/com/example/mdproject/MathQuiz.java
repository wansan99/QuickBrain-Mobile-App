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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MathQuiz extends AppCompatActivity {
    private static final long COUNTDOWN_IN_MILLIS = 120000;
    private static final long  COUNTDOWN_FOR_SCORE = 5000;
    private static final long COUNTDOWN_TO_GAME_START_IN_MILLIS = 3000;

    private ImageButton pause;
    private TextView textViewQuestion;
    private TextView textViewQuestionCounter;
    private Button btnChoice1, btnChoice2, btnChoice3, btnChoice4;
    private ProgressBar pbQuiz;
    private TextView timer;
    private TextView textViewPausedLabel;
    private TextView textViewCountDownToGameStart;
    private TextView textViewGameDescription;
    private Button btn_Resume, restart, exit;
    private ImageView imgCorrect, imgWrong;
    
    private List<MathQuestion> questionList;
    private int questionCounter;
    private int questionCountTotal;
    private MathQuestion currentQuestion;
    private int questionType;

    private int score;
    private CountDownTimer countDownTimer;
    private CountDownTimer countDownForScore;
    private CountDownTimer countDownToGameStart;
    private long timeLeftToStart = COUNTDOWN_TO_GAME_START_IN_MILLIS;
    private long timeLeftForScore = COUNTDOWN_FOR_SCORE;
    private long timeLeftInMillis = COUNTDOWN_IN_MILLIS;
    boolean pause_flg = false;
    boolean win = false;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_quiz);

        pause = findViewById(R.id.btn_pause);
        restart = findViewById(R.id.btn_Restart);
        exit = findViewById(R.id.btn_Exit);
        timer = findViewById(R.id.tvTimer);
        pbQuiz = findViewById(R.id.pbQuiz);
        textViewPausedLabel = findViewById(R.id.textViewPausedLabel);
        textViewCountDownToGameStart = findViewById(R.id.textViewCountDownToGameStart);
        textViewGameDescription = findViewById(R.id.textViewGameDescription);
        btn_Resume = findViewById(R.id.btn_Resume);
        imgCorrect = findViewById(R.id.ic_correct);
        imgWrong = findViewById(R.id.ic_wrong);
        
        textViewQuestion = findViewById(R.id.tv_question);
        textViewQuestionCounter = findViewById(R.id.tv_questionNo);
        btnChoice1 = findViewById(R.id.ansA);
        btnChoice2 = findViewById(R.id.ansB);
        btnChoice3 = findViewById(R.id.ansC);
        btnChoice4 = findViewById(R.id.ansD);
        //database have different question with different type, so we get the selected type by user from previous activity
        questionType = Integer.parseInt(getIntent().getStringExtra("questionType"));

        QBDataBase dbHelper = new QBDataBase(this);
        questionList = dbHelper.getAllMathQuestions(questionType, 2); //get the questions from db
        questionCountTotal = questionList.size();
        Collections.shuffle(questionList);

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
                textViewGameDescription.setText("Answer Them All!");
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

                showNextQuestion();
                startCountDown();
                setChoiceToSelectable();
            }
        }.start();
    }

    private void setChoiceToSelectable(){
        btnChoice1.setOnClickListener(v -> {
            checkAnswer(1);
        });
        btnChoice2.setOnClickListener(v -> {
            checkAnswer(2);
        });
        btnChoice3.setOnClickListener(v -> {
            checkAnswer(3);
        });
        btnChoice4.setOnClickListener(v -> {
            checkAnswer(4);
        });
    }

    private void showNextQuestion() {
        timeLeftForScore = COUNTDOWN_FOR_SCORE;
        if(questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());
           btnChoice1.setText(currentQuestion.getChoice1());
            btnChoice2.setText(currentQuestion.getChoice2());
            btnChoice3.setText(currentQuestion.getChoice3());
            btnChoice4.setText(currentQuestion.getChoice4());

            questionCounter++;
            textViewQuestionCounter.setText(questionCounter + "/" + questionCountTotal);
        }
        else {
            finishQuiz();
        }
        startScoreCountDown();
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                pbQuiz.setProgress((int) (((double) timeLeftInMillis/ (double) COUNTDOWN_IN_MILLIS)*100));
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                pbQuiz.setProgress(0);
                updateCountDownText();
                finishQuiz();
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timer.setText(timeFormatted);

        if(timeLeftInMillis < 5000) {
            timer.setTextColor(Color.RED);
        } else {
            timer.setTextColor(Color.BLACK);
        }
    }

    private void startScoreCountDown() {
        countDownForScore = new CountDownTimer(timeLeftForScore, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftForScore = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                timeLeftForScore = 0;
            }
        }.start();
    }

    public void pauseGame(View view) {
        if(!pause_flg) {
            pause_flg = true;
            setViewTempNotClickable();

            countDownTimer.cancel();
            countDownForScore.cancel();
            countDownTimer = null;
            countDownForScore = null;

            textViewPausedLabel.setVisibility(View.VISIBLE);
            btn_Resume.setVisibility(View.VISIBLE);
            restart.setVisibility(View.VISIBLE);
            exit.setVisibility(View.VISIBLE);
        } else {
            pause_flg = false;
            setViewTempClickable();

            textViewPausedLabel.setVisibility(View.GONE);
            btn_Resume.setVisibility(View.GONE);
            restart.setVisibility(View.GONE);
            exit.setVisibility(View.GONE);
            startCountDown();
            startScoreCountDown();
        }
    }

    public void restartGame(View view) {
        MainActivity.gamePlaySound.stop();
        Intent intent = new Intent(getApplicationContext(), MathQuiz.class);
        if(questionType == 1) intent.putExtra("questionType", "1");
        else if(questionType == 2) intent.putExtra("questionType", "2");
        else if(questionType == 3) intent.putExtra("questionType", "3");
        else if(questionType == 4) intent.putExtra("questionType", "4");
        else if(questionType == 5) intent.putExtra("questionType", "5");
        else if(questionType == 6) intent.putExtra("questionType", "6");
        else if(questionType == 7) intent.putExtra("questionType", "7");
        startActivity(intent);
    }

    public void exitGame(View view) {
        MainActivity.gamePlaySound.stop();
        mediaPlayerHandler.optimizeBGM(getApplicationContext(), 1);
        Intent intent = new Intent(getApplicationContext(), Homepage.class);
        startActivity(intent);
    }

    private void setViewTempNotClickable() {
        btnChoice1.setEnabled(false);
        btnChoice2.setEnabled(false);
        btnChoice3.setEnabled(false);
        btnChoice4.setEnabled(false);
    }

    private void setViewTempClickable() {
        btnChoice1.setEnabled(true);
        btnChoice2.setEnabled(true);
        btnChoice3.setEnabled(true);
        btnChoice4.setEnabled(true);
    }

    private void checkAnswer(int selectedAnswer) {
        setViewTempNotClickable();
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.wrong_correct_animation);
        countDownForScore.cancel();
        if(selectedAnswer == currentQuestion.getAnswer()) {
            score = score + 1000 + (int) timeLeftForScore;
            imgCorrect.setVisibility(View.VISIBLE);
            imgCorrect.startAnimation(animation);
            MainActivity.correctSound.start();
        } else {
            imgWrong.setVisibility(View.VISIBLE);
            imgWrong.startAnimation(animation);
            MainActivity.wrongSound.start();
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imgCorrect.setVisibility(View.GONE);
                imgWrong.setVisibility(View.GONE);
                showNextQuestion();
                setViewTempClickable();
            }
        }, 1000);
    }

    private void finishQuiz() {
        MainActivity.gamePlaySound.stop();
        countDownTimer.cancel();
        countDownForScore.cancel();
        QBDataBase db = new QBDataBase(this);
        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> userInfo = sessionManager.getUserDataFromSession();
        Intent intent = new Intent(getApplicationContext(), gameResult.class);
        if (questionType == 1) {
            intent.putExtra("Level", "1");
            int tempScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MQS1));
            if(tempScore < score) {
                sessionManager.setKeyMqs1(score);
                boolean isUpdated = db.updateScore(1, score, userInfo.get(SessionManager.KEY_USERNAME));
                if(isUpdated) Toast.makeText(getApplicationContext(), "New HighScore", Toast.LENGTH_SHORT).show();
            }
        }
        if (questionType == 2) {
            intent.putExtra("Level", "2");
            int tempScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MQS2));
            if(tempScore < score) {
                sessionManager.setKeyMqs2(score);
                boolean isUpdated = db.updateScore(2, score, userInfo.get(SessionManager.KEY_USERNAME));
                if(isUpdated) Toast.makeText(getApplicationContext(), "New HighScore", Toast.LENGTH_SHORT).show();
            }
        }
        if (questionType == 3) {
            intent.putExtra("Level", "3");
            int tempScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MQS3));
            if(tempScore < score) {
                sessionManager.setKeyMqs3(score);
                boolean isUpdated = db.updateScore(3, score, userInfo.get(SessionManager.KEY_USERNAME));
                if(isUpdated) Toast.makeText(getApplicationContext(), "New HighScore", Toast.LENGTH_SHORT).show();
            }
        }
        if (questionType == 4) {
            intent.putExtra("Level", "4");
            int tempScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MQS4));
            if(tempScore < score) {
                sessionManager.setKeyMqs4(score);
                boolean isUpdated = db.updateScore(4, score, userInfo.get(SessionManager.KEY_USERNAME));
                if(isUpdated) Toast.makeText(getApplicationContext(), "New HighScore", Toast.LENGTH_SHORT).show();
            }
        }
        if (questionType == 5) {
            intent.putExtra("Level", "5");
            int tempScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MQS5));
            if(tempScore < score) {
                sessionManager.setKeyMqs5(score);
                boolean isUpdated = db.updateScore(5, score, userInfo.get(SessionManager.KEY_USERNAME));
                if(isUpdated) Toast.makeText(getApplicationContext(), "New HighScore", Toast.LENGTH_SHORT).show();
            }
        }
        if (questionType == 6) {
            intent.putExtra("Level", "6");
            int tempScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MQS6));
            if(tempScore < score) {
                sessionManager.setKeyMqs6(score);
                boolean isUpdated = db.updateScore(6, score, userInfo.get(SessionManager.KEY_USERNAME));
                if(isUpdated) Toast.makeText(getApplicationContext(), "New HighScore", Toast.LENGTH_SHORT).show();
            }
        }
        if (questionType == 7) {
            intent.putExtra("Level", "7");
            int tempScore = Integer.parseInt(userInfo.get(SessionManager.KEY_MQS7));
            if(tempScore < score) {
                sessionManager.setKeyMqs7(score);
                boolean isUpdated = db.updateScore(7, score, userInfo.get(SessionManager.KEY_USERNAME));
                if(isUpdated) Toast.makeText(getApplicationContext(), "New HighScore", Toast.LENGTH_SHORT).show();
            }
        }
        intent.putExtra("Score", Integer.toString(score));
        intent.putExtra("GameType", "Math Quiz");
        intent.putExtra("Win", "null");
        startActivity(intent);
    }
}