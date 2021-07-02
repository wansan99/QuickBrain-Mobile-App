package com.example.mdproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button registerActivity;
    private Button loginActivity;
    public static MediaPlayer correctSound, wrongSound, onTickSound, onFinishSound, gamePlaySound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        correctSound = MediaPlayer.create(this, R.raw.correct_sound);
        wrongSound = MediaPlayer.create(this, R.raw.wrong_sound);
        onTickSound = MediaPlayer.create(this, R.raw.ontick_sound);
        onFinishSound = MediaPlayer.create(this, R.raw.onfinish_sound);
        gamePlaySound = MediaPlayer.create(this, R.raw.game_bg);

        loginActivity = (Button) findViewById(R.id.btnLogin);
        loginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });

        registerActivity = (Button) findViewById(R.id.btnRegister);
        registerActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegister();
            }
        });

    }

    public void openLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("NewUser", "false");
        startActivity(intent);
    }

    public void openRegister(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}