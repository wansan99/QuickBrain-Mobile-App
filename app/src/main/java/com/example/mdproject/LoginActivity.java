package com.example.mdproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private Button login, forgotPassword;
    private ImageButton back;
    private String newUser;
    private EditText username;
    private EditText password;
    public static MediaPlayer main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        newUser = getIntent().getStringExtra("NewUser");
        if(newUser.equals("true"))
            Toast.makeText(getApplicationContext(), "Registration Successful!", Toast.LENGTH_SHORT).show();

        username = findViewById(R.id.username);
        password = findViewById(R.id.rgPassword);
        login = findViewById(R.id.btnSubmitLogin);
        forgotPassword = findViewById(R.id.forgotPw);
        back = findViewById(R.id.btnBack);
        main = MediaPlayer.create(this, R.raw.main);

        QBDataBase db = new QBDataBase(this);

        submitLogin(db);
        setClickable();
    }

    private void setClickable() {
        back.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });
        forgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ForgotPassword.class);
            startActivity(intent);
        });
    }

    private void submitLogin(QBDataBase db) {
        login.setOnClickListener(v -> {
            String user = username.getText().toString();
            String pw = password.getText().toString();

            if(user.equals("") || pw.equals(""))
                Toast.makeText(getApplicationContext(), "Please fill in all the fields above.", Toast.LENGTH_SHORT).show();
            else {
                boolean userExist = db.checkIfUserExist(user);
                if(userExist) {
                    boolean canLogin = db.loginProfile(user, pw);
                    if(canLogin) {
                        List<String> userInfo = db.getUserInformation(user);
                        SessionManager sessionManager = new SessionManager(getApplicationContext());
                        sessionManager.createLoginSession(user, userInfo.get(0), Integer.parseInt(userInfo.get(1)), Integer.parseInt(userInfo.get(2)), Integer.parseInt(userInfo.get(3)),
                                Integer.parseInt(userInfo.get(4)), Integer.parseInt(userInfo.get(5)), Integer.parseInt(userInfo.get(6)), Integer.parseInt(userInfo.get(7)),
                                Integer.parseInt(userInfo.get(8)), Integer.parseInt(userInfo.get(9)), Integer.parseInt(userInfo.get(10)), Integer.parseInt(userInfo.get(11)),
                                Integer.parseInt(userInfo.get(12)), Integer.parseInt(userInfo.get(13)), Integer.parseInt(userInfo.get(14)), Integer.parseInt(userInfo.get(15)), Integer.parseInt(userInfo.get(16)),
                                1, 1);
                        Intent intent;
                        main.start();
                        if (newUser.equals("true")) {
                            intent = new Intent(getApplicationContext(), RegisterCompleteActivity.class);
                        }
                        else {
                            intent = new Intent(getApplicationContext(), Homepage.class);
                        }
                        startActivity(intent);
                    }
                    else Toast.makeText(getApplicationContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getApplicationContext(), "Username not exist!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}