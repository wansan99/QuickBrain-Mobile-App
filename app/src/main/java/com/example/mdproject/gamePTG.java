package com.example.mdproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class gamePTG extends AppCompatActivity {
    private static final long COUNTDOWN_FOR_GAME_IN_MILLIS = 60000;
    private static final long COUNTDOWN_FOR_BONUS_SCORE_IN_MILLIS = 5000;
    private static final long COUNTDOWN_TO_GAME_START_IN_MILLIS = 3000;

    private ImageButton pause;
    private ProgressBar pbGame;
    private TextView textViewLargest1, textViewLargest2, textViewLargest3, textViewLargest4, textViewLargest5, textViewLargest6, textViewTimer;
    private TextView textViewScore;
    private FrameLayout frame;
    private TextView textViewPausedLabel;
    private TextView textViewPausedScoreLabel;
    private TextView textViewCountDownToGameStart;
    private TextView textViewGameDescription;
    private Button btn_Resume, restart, exit;
    private ImageView imgCorrect, imgWrong;
    
    private int frameWidth;
    private int frameHeight;
    
    private float _1X;
    private float _1Y;
    private float _2X;
    private float _2Y;
    private float _3X;
    private float _3Y;
    private float _4X;
    private float _4Y;
    private float _5X;
    private float _5Y;
    private float _6X;
    private float _6Y;

    private Handler handler = new Handler();
    private Timer timer = new Timer();

    private List<LargestQuestion> questionList;
    private int questionCounter;
    private int questionCountTotal;
    private LargestQuestion currentQuestion;

    private int score = 0;
    private int level = 0;
    double scoreMultiplier = 1.2;
    private CountDownTimer countDownTimer;
    private CountDownTimer bonusScoreTimer;
    private CountDownTimer countDownToGameStart;
    private long timeLeftToStart = COUNTDOWN_TO_GAME_START_IN_MILLIS;
    private long timeLeftInMillis = COUNTDOWN_FOR_GAME_IN_MILLIS;
    private long timeLeftForBonusScoreInMillis = COUNTDOWN_FOR_BONUS_SCORE_IN_MILLIS;
    boolean pause_flg = false;
    boolean bonusScore = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_p_t_g);

        pause = findViewById(R.id.btn_pause);
        restart = findViewById(R.id.btn_Restart);
        exit = findViewById(R.id.btn_Exit);
        pbGame = findViewById(R.id.progressBar);
        frame = findViewById(R.id.frameGameFrame);
        textViewScore = findViewById(R.id.tvScore);
        textViewTimer = (TextView) findViewById(R.id.tvTimer);
        textViewPausedLabel = findViewById(R.id.textViewPausedLabel);
        textViewPausedScoreLabel = findViewById(R.id.textViewPausedScoreLabel);
        textViewCountDownToGameStart = findViewById(R.id.textViewCountDownToGameStart);
        textViewGameDescription = findViewById(R.id.textViewGameDescription);
        imgCorrect = findViewById(R.id.ic_correct);
        imgWrong = findViewById(R.id.ic_wrong);
        btn_Resume = findViewById(R.id.btn_Resume);

        textViewLargest1 = (TextView) findViewById(R.id.largest1);
        textViewLargest2 = (TextView) findViewById(R.id.largest2);
        textViewLargest3 = (TextView) findViewById(R.id.largest3);
        textViewLargest4 = (TextView) findViewById(R.id.largest4);
        textViewLargest5 = (TextView) findViewById(R.id.largest5);
        textViewLargest6 = (TextView) findViewById(R.id.largest6);

        ViewTreeObserver vto = frame.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private FrameLayout frame = findViewById(R.id.frameGameFrame);
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    this.frame.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    this.frame.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                frameWidth = frame.getMeasuredWidth();
                frameHeight = frame.getMeasuredHeight();
            }
        });
        startCountDownToGameStart();

        questionList = generateQuestion();
        questionCountTotal = questionList.size();
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
                textViewGameDescription.setText("Find the Largest Number!");
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
                startGameCountDown();

                textViewLargest1.setX(200.0f);
                textViewLargest1.setY(250.0f);
                textViewLargest2.setX(900.0f);
                textViewLargest2.setY(500.0f);
                textViewLargest3.setX(750.0f);
                textViewLargest3.setY(950.0f);
                textViewLargest4.setX(500.0f);
                textViewLargest4.setY(900.0f);
                textViewLargest5.setX(150.0f);
                textViewLargest5.setY(1200.0f);
                textViewLargest6.setX(50.0f);
                textViewLargest6.setY(700.0f);

                //update the position of numbers
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(() -> changePos(currentQuestion.getDifficulty()));
                    }
                }, 0, 20);
            }
        }.start();
    }

    private void startGameCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                pbGame.setProgress((int) (((double) timeLeftInMillis/ (double) COUNTDOWN_FOR_GAME_IN_MILLIS)*100));
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                pbGame.setProgress(0);
                updateCountDownText();
                endGame();
            }
        }.start();
    }

    private void startBonusScoreCountdown() {
        bonusScoreTimer = new CountDownTimer(timeLeftForBonusScoreInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftForBonusScoreInMillis = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                timeLeftForBonusScoreInMillis = 0;
                bonusScore = false;
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        textViewTimer.setText(timeFormatted);

        if(timeLeftInMillis < 10000) {
            textViewTimer.setTextColor(Color.RED);
        } else {
            textViewTimer.setTextColor(Color.BLACK);
        }
    }

    public void changePos(int difficulty){
        _1X = textViewLargest1.getX();
        _1Y = textViewLargest1.getY();
        _2X = textViewLargest2.getX();
        _2Y = textViewLargest2.getY();
        _3X = textViewLargest3.getX();
        _3Y = textViewLargest3.getY();
        _4X = textViewLargest4.getX();
        _4Y = textViewLargest4.getY();
        _5X = textViewLargest5.getX();
        _5Y = textViewLargest5.getY();
        _6X = textViewLargest6.getX();
        _6Y = textViewLargest6.getY();
        
        //the game increases difficulty over question answered, with each difficulty we make new number movable
        if(difficulty > 2) {
            _1Y -= 10;
            //if moved out of frame we reset it to emerge from the other side of the frame
            if (textViewLargest1.getY() + textViewLargest1.getHeight() < 0) {
                _1X = (float) Math.floor(Math.random() * (frameWidth - textViewLargest1.getWidth()));
                _1Y = frameHeight + 100.0f;
            }
            textViewLargest1.setX(_1X);
            textViewLargest1.setY(_1Y);
        }

        if(difficulty > 3) {
            _2Y += 10;
            if (textViewLargest2.getY() > frameHeight) {
                _2X = (float) Math.floor(Math.random() * (frameWidth - textViewLargest2.getWidth()));
                _2Y = -275.0f;
            }
            textViewLargest2.setX(_2X);
            textViewLargest2.setY(_2Y);
        }

        if(difficulty > 4) {
            _3X += 10;
            if (textViewLargest3.getX() > frameWidth) {
                _3X = -275.0f;
                _3Y = (float) Math.floor(Math.random() * (frameHeight - textViewLargest3.getHeight()));
            }
            textViewLargest3.setX(_3X);
            textViewLargest3.setY(_3Y);
        }

        if(difficulty > 5) {
            _4X -= 10;
            if (textViewLargest4.getX() + textViewLargest4.getWidth() < 0) {
                _4X = frameWidth + 100.0f;
                _4Y = (float) Math.floor(Math.random() * (frameHeight - textViewLargest4.getHeight()));
            }
            textViewLargest4.setX(_4X);
            textViewLargest4.setY(_4Y);
        }

        if(difficulty > 6) {
            _5Y += 10;
            _5X += 10;
            if (textViewLargest5.getY() > frameHeight) {
                _5Y = -275.0f;
            }
            if (textViewLargest5.getX() > frameWidth) {
                _5X = -275.0f;
            }
            textViewLargest5.setX(_5X);
            textViewLargest5.setY(_5Y);
        }

        if(difficulty > 7) {
            _6X -= 10;
            _6Y -= 10;
            if (textViewLargest6.getX() + textViewLargest6.getWidth() < 0) {
                _6X = frameWidth + 100.0f;
            }
            if (textViewLargest6.getY() + textViewLargest6.getHeight() < 0) {
                _6Y = frameHeight + 100.0f;
            }
            textViewLargest6.setX(_6X);
            textViewLargest6.setY(_6Y);
        }
    }

    public List<LargestQuestion> generateQuestion() {
        List<LargestQuestion> largestQuestionsList = new ArrayList<>();
        //set initial difficulty to 1
        int difficulty=1;
        //there are 25 question so we iterate 25 times
        for(int i = 1; i < 36; i++) {
            //create a temporary question
            LargestQuestion question = new LargestQuestion();
            int answer = 0;
            int answerPosition = 0;
            int  j = i + 1;
            if(j > 6) { //after 6 question, we began to all each number to move every 2 question
                j = 6;
                if(i % 2 == 1) difficulty++;
            }
            for(int k = 0; k < j; k++) { // each question have a number of numbers to declare, so we iterate base on how many number the question has
                int[] tempNumberArray = new int[j];
                boolean repeat;
                int tempRandomNumber;
                do{
                    repeat = false;
                    tempRandomNumber = (int) Math.floor(Math.random() * (100 - 1) + 1); //random assignment of number
                    for(int m = 0; m < j; m++) {//make sure no duplicate number
                        if(tempRandomNumber == tempNumberArray[m]) {
                            repeat = true;
                        }
                    }
                }while(repeat);
                if(tempRandomNumber > answer) { //get the largest number as the answer
                    answer = tempRandomNumber;
                    answerPosition = k + 1;
                }
                switch (k) { //put each generated number into the question
                    case 0:
                        question.setNumber1(Integer.toString(tempRandomNumber));
                        break;
                    case 1:
                        question.setNumber2(Integer.toString(tempRandomNumber));
                        break;
                    case 2:
                        question.setNumber3(Integer.toString(tempRandomNumber));
                        break;
                    case 3:
                        question.setNumber4(Integer.toString(tempRandomNumber));
                        break;
                    case 4:
                        question.setNumber5(Integer.toString(tempRandomNumber));
                        break;
                    case 5:
                        question.setNumber6(Integer.toString(tempRandomNumber));
                        break;
                }
                question.setDifficulty(difficulty); //set the difficulty for the question
                question.setAnswer(answerPosition); //set the answer for the question
            }
            largestQuestionsList.add(question); //add the question into an array of question
        }
        return largestQuestionsList;
    }

    private void showNextQuestion(){
        bonusScore = true;
        timeLeftForBonusScoreInMillis = COUNTDOWN_FOR_BONUS_SCORE_IN_MILLIS;
        startBonusScoreCountdown();
        if(questionCounter < questionCountTotal) {
            //if we still have question to show we continue and get a question from the questionList
            currentQuestion = questionList.get(questionCounter);
            //set each number t accordingly
            textViewLargest1.setText(currentQuestion.getNumber1());
            textViewLargest2.setText(currentQuestion.getNumber2());
            //set every other number invisible
            textViewLargest3.setVisibility(View.GONE);
            textViewLargest4.setVisibility(View.GONE);
            textViewLargest5.setVisibility(View.GONE);
            textViewLargest6.setVisibility(View.GONE);
            //if the question moves on, more number will be added, so we make it visible and set its number accordingly
            if(questionCounter > 0) {
                textViewLargest3.setText(currentQuestion.getNumber3());
                textViewLargest3.setVisibility(View.VISIBLE);
            }
            if(questionCounter > 1) {
                textViewLargest4.setText(currentQuestion.getNumber4());
                textViewLargest4.setVisibility(View.VISIBLE);
            }
            if(questionCounter > 2) {
                textViewLargest5.setText(currentQuestion.getNumber5());
                textViewLargest5.setVisibility(View.VISIBLE);
            }
            if(questionCounter > 3) {
                textViewLargest6.setText(currentQuestion.getNumber6());
                textViewLargest6.setVisibility(View.VISIBLE);
            }
            //update the question counter
            questionCounter++;
        }
        else {
            //end quiz
            endGame();
        }
        //make sure each time the question is update, the number are also clickable
        setTextViewToSelectable(questionCounter);
    }

    private void setTextViewToSelectable(int questionCounter) {
        textViewLargest1.setOnClickListener(v -> {
            checkAnswer(1);
        });
        textViewLargest2.setOnClickListener(v -> {
            checkAnswer(2);
        });
        if(questionCounter > 0) {
            textViewLargest3.setOnClickListener(v -> {
                checkAnswer(3);
            });
        }
        if(questionCounter > 1) {
            textViewLargest4.setOnClickListener(v -> {
                checkAnswer(4);
            });
        }
        if(questionCounter > 2) {
            textViewLargest5.setOnClickListener(v -> {
                checkAnswer(5);
            });
        }
        if(questionCounter > 3) {
            textViewLargest6.setOnClickListener(v -> {
                checkAnswer(6);
            });
        }
    }

    private void checkAnswer(int selectedAnswer) {
        setViewTempNotClickable();
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.wrong_correct_animation);
        if(selectedAnswer == currentQuestion.getAnswer()) {
            imgCorrect.setVisibility(View.VISIBLE);
            imgCorrect.startAnimation(animation);
            if(bonusScore) {
                score = (int) (score + (scoreMultiplier*1000));
            }
            else score += 1000;
            level++;
            textViewScore.setText("Score: " + score);
            MainActivity.correctSound.start();
        } else {
            imgWrong.setVisibility(View.VISIBLE);
            imgWrong.startAnimation(animation);
            MainActivity.wrongSound.start();
        }
        bonusScoreTimer.cancel();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imgCorrect.setVisibility(View.GONE);
                imgWrong.setVisibility(View.GONE);
                setViewTempClickable();
                showNextQuestion();
            }
        }, 1000);
    }

    public void pauseGame(View view) {
        if(!pause_flg) {
            pause_flg = true;
            setViewTempNotClickable();

            countDownTimer.cancel();
            bonusScoreTimer.cancel();
            countDownTimer = null;
            bonusScoreTimer = null;
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
            startGameCountDown();
            startBonusScoreCountdown();
        }
    }

    public void restartGame(View view) {
        MainActivity.gamePlaySound.stop();
        Intent intent = new Intent(getApplicationContext(), gamePTG.class);
        startActivity(intent);
    }

    public void exitGame(View view) {
        MainActivity.gamePlaySound.stop();
        mediaPlayerHandler.optimizeBGM(getApplicationContext(), 1);
        Intent intent = new Intent(getApplicationContext(), Homepage.class);
        startActivity(intent);
    }

    private void setViewTempNotClickable() {
        textViewLargest1.setEnabled(false);
        textViewLargest2.setEnabled(false);
        textViewLargest3.setEnabled(false);
        textViewLargest4.setEnabled(false);
        textViewLargest5.setEnabled(false);
        textViewLargest6.setEnabled(false);
    }
    
    private void setViewTempClickable() {
        textViewLargest1.setEnabled(true);
        textViewLargest2.setEnabled(true);
        textViewLargest3.setEnabled(true);
        textViewLargest4.setEnabled(true);
        textViewLargest5.setEnabled(true);
        textViewLargest6.setEnabled(true);
    }
    
    private void endGame(){
        MainActivity.gamePlaySound.stop();
        countDownTimer.cancel();
        bonusScoreTimer.cancel();
        QBDataBase db = new QBDataBase(this);
        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> userInfo = sessionManager.getUserDataFromSession();
        int tempScore = Integer.parseInt(userInfo.get(SessionManager.KEY_LSS1));
        if(tempScore < score) {
            sessionManager.setKeyLss1(score);
            boolean isUpdated = db.updateScore(8, score, userInfo.get(SessionManager.KEY_USERNAME));
            if(isUpdated) Toast.makeText(getApplicationContext(), "New HighScore", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(getApplicationContext(), gameResult.class);
        intent.putExtra("Level", Integer.toString(level));
        intent.putExtra("Score", Integer.toString(score));
        intent.putExtra("GameType", "Largest");
        intent.putExtra("Win", "null");
        startActivity(intent);
    }
}