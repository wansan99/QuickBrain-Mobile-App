package com.example.mdproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class subtractionTutorial extends AppCompatActivity {

    private Button startQuiz;
    private ImageButton back;
    private ViewPager mSlideViewPager;
    private LinearLayout dotsLayout;

    private TextView[] dots;

    private SlideAdapterSub slideAdapterSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtraction_tutorial);

        back = findViewById(R.id.btnBack);
        startQuiz = findViewById(R.id.btnStartQuiz2);
        mSlideViewPager = findViewById(R.id.slideViewPager);
        dotsLayout = findViewById(R.id.dots);

        slideAdapterSub = new SlideAdapterSub(this);
        mSlideViewPager.setAdapter(slideAdapterSub);

        addDotsIndicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);
        setClickable();
    }

    public void addDotsIndicator(int position) {
        dots = new TextView[9];
        dotsLayout.removeAllViews();
        for(int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.transparentWhtie));
            dotsLayout.addView(dots[i]);
        }

        if(dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.white));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void setClickable() {
        startQuiz.setOnClickListener(v -> openQuiz());
        back.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TutorialSelection.class);
            startActivity(intent);
        });
    }

    public void openQuiz(){
        Intent intent = new Intent(this, TutorialQuiz.class);
        intent.putExtra("questionType", "2");
        startActivity(intent);
    }
}