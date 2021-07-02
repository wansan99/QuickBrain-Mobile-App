package com.example.mdproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class RegisterCompleteActivity extends AppCompatActivity {

    TextView text1, text2;
    ImageView logo;
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_complete);

        text1 = findViewById(R.id.tv1);
        text2 = findViewById(R.id.text2);
        logo = findViewById(R.id.iv_appLogo);
        startButton = findViewById(R.id.btnStart);

        startButton.setOnClickListener(v -> openHomepage());
    }

    public void openHomepage(){
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);
    }
}