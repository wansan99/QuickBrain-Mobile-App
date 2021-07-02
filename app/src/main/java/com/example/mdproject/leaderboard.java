package com.example.mdproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class leaderboard extends AppCompatActivity {
    private ImageButton back;
    LinearLayout layoutLeaderBoard;
    List<LeaderboardProfiles> leaderboardProfiles;

    RadioGroup tabGame;
    RadioButton tabPTG, tabMatching, tabQuickMath;
    Spinner spinnerLvl;

    String[] ptgLevel = {"Level 1", "Level 2"};
    String[] matchingLevel = {"Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6", "Level 7"};
    String[] quickMathLevel = {"Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6", "Level 7"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        back = findViewById(R.id.btnBack);
        layoutLeaderBoard = findViewById(R.id.layoutLeaderBoardRow);

        QBDataBase db = new QBDataBase(this);

        tabGame = findViewById(R.id.tabGameSelect);
        tabPTG = findViewById(R.id.tabPTG);
        tabMatching = findViewById(R.id.tabMatching);
        tabQuickMath = findViewById(R.id.tabQuickMath);
        spinnerLvl = findViewById(R.id.lvlSelection);
        setupLeaderBoard(db,8);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, ptgLevel);
        spinnerLvl.setAdapter(adapter);
        setClickable();
        tabGame.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                case R.id.tabPTG: //no picker
                    Log.d("execute tab no picker", "yes");
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, ptgLevel);
                    spinnerLvl.setAdapter(adapter1);
                    spinnerLvl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position==0){ //level 1
                                    setupLeaderBoard(db,8); }
                            else if (position==1){ //level 2
                                    setupLeaderBoard(db,9); }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                    break;

                case R.id.tabMatching: //matching game
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, matchingLevel);
                    spinnerLvl.setAdapter(adapter2);
                    spinnerLvl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(position==0){ //level 1
                                setupLeaderBoard(db,1);}
                            else if(position==1){ //level 2
                                setupLeaderBoard(db,2);}
                            else if(position==2){ //level 3
                                setupLeaderBoard(db,3);}
                            else if(position==3){ //level 4
                                setupLeaderBoard(db,4);}
                            else if(position==4){ //level 5
                                setupLeaderBoard(db,5);}
                            else if(position==5){ //level 6
                                setupLeaderBoard(db,6);}
                            else if(position==6){ //level 7
                                setupLeaderBoard(db,7);}
                            }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    break;

                case R.id.tabQuickMath: //quick math
                    ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, quickMathLevel);
                    spinnerLvl.setAdapter(adapter3);
                    spinnerLvl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(position==0){ //level 1
                                setupLeaderBoard(db,10);}
                            else if(position==1){ //level 2
                                setupLeaderBoard(db,11);}
                            else if(position==2){ //level 3
                                setupLeaderBoard(db,12);}
                            else if(position==3){ //level 4
                                setupLeaderBoard(db,13);}
                            else if(position==4){ //level 5
                                setupLeaderBoard(db,14);}
                            else if(position==5){ //level 6
                                setupLeaderBoard(db,15);}
                            else if(position==6){ //level 7
                                setupLeaderBoard(db,16);}
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                    break;
            }
        });
    }

    private void setClickable() {
        back.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Homepage.class);
            startActivity(intent);
        });
    }


    private void setupLeaderBoard(QBDataBase db, int category) {
        layoutLeaderBoard.removeAllViews();
        leaderboardProfiles = db.getLeaderBoardScore(category);
        for(int i = 0; i < leaderboardProfiles.size(); i++) {
            LeaderboardProfiles currentLeaderBoardProfile = leaderboardProfiles.get(i);
            View leaderBoardRow = getLayoutInflater().inflate(R.layout.leader_board_row, null, false);
            TextView textViewPlace = leaderBoardRow.findViewById(R.id.textViewPlace);
            TextView textViewName = leaderBoardRow.findViewById(R.id.textViewName);
            TextView textViewScore = leaderBoardRow.findViewById(R.id.textViewScore);

            textViewPlace.setText("" + (i + 1) + ".");
            textViewName.setText("" + currentLeaderBoardProfile.getName());
            textViewScore.setText("" + currentLeaderBoardProfile.getScore());
            if(i+1 == 1) {
                textViewPlace.setTextColor(Color.parseColor("#FBBC05"));
                textViewName.setTextColor(Color.parseColor("#FBBC05"));
                textViewScore.setTextColor(Color.parseColor("#FBBC05"));
                textViewPlace.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                textViewName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                textViewScore.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            }
            else if(i+1 == 2) {
                textViewPlace.setTextColor(Color.parseColor("#AAA9AD"));
                textViewName.setTextColor(Color.parseColor("#AAA9AD"));
                textViewScore.setTextColor(Color.parseColor("#AAA9AD"));
                textViewPlace.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
                textViewName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
                textViewScore.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
            }
            else if(i+1 == 3) {
                textViewPlace.setTextColor(Color.parseColor("#A97142"));
                textViewName.setTextColor(Color.parseColor("#A97142"));
                textViewScore.setTextColor(Color.parseColor("#A97142"));
                textViewPlace.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
                textViewName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
                textViewScore.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
            }
            else {
                textViewPlace.setTextColor(Color.parseColor("#000000"));
                textViewName.setTextColor(Color.parseColor("#000000"));
                textViewScore.setTextColor(Color.parseColor("#000000"));
                textViewPlace.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                textViewName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                textViewScore.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            }
            layoutLeaderBoard.addView(leaderBoardRow);
        }
    }
}