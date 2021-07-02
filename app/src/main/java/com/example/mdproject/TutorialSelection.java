package com.example.mdproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.BitSet;

public class TutorialSelection extends AppCompatActivity {

    private Button tut1, tut2, tut3, tut4;
    private ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_selection);
        
        back = findViewById(R.id.btnBack);
        tut1 = (Button) findViewById (R.id.btnTut1);
        tut2 = (Button) findViewById (R.id.btnTut2);
        tut3 = (Button) findViewById (R.id.btnTut3);
        tut4 = (Button) findViewById (R.id.btnTut4);
        setClickable();
    }
    
    private void setClickable() {
        back.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Homepage.class);
            startActivity(intent);
        });
        tut1.setOnClickListener(v -> {
            LoginActivity.main .stop();
            Intent intent = new Intent(getApplicationContext(), additionTutorial.class);
            startActivity(intent);
        });
        tut2.setOnClickListener(v -> {
            LoginActivity.main .stop();
            Intent intent = new Intent(getApplicationContext(), subtractionTutorial.class);
            startActivity(intent);
        });
        tut3.setOnClickListener(v -> {
            LoginActivity.main .stop();
            Intent intent = new Intent(getApplicationContext(), multiplicationTutorial.class);
            startActivity(intent);
        });
        tut4.setOnClickListener(v -> {
            LoginActivity.main .stop();
            Intent intent = new Intent(getApplicationContext(), divisionTutorial.class);
            startActivity(intent);
        });
    }
}