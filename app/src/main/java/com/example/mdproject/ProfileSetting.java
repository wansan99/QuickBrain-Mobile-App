package com.example.mdproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;

public class ProfileSetting extends AppCompatActivity {

    private Button signout, submit;
    private ImageButton back;
    private Switch soundEffect, backgroundMusic;
    private EditText editTextUsername, editTextName, editTextNewPassword, editTextReNewPassword, editTextOldPassword;
    private String[] achievements = new String[4];

    private int stars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);

        signout = findViewById(R.id.btnSignout);
        soundEffect = findViewById(R.id.soundEffectSwitch);
        backgroundMusic = findViewById(R.id.backgroundMusicSwitch);

        back = findViewById(R.id.btnBack);
        submit = findViewById(R.id.btnUpdateProfile);

        editTextUsername = findViewById(R.id.editUsername);
        editTextName = findViewById(R.id.editName);
        editTextOldPassword = findViewById(R.id.editOPw);
        editTextNewPassword = findViewById(R.id.editPw);
        editTextReNewPassword = findViewById(R.id.editRPw);

        stars = Integer.parseInt(getIntent().getStringExtra("Stars"));
        achievements[0] = getIntent().getStringExtra("ASA");
        achievements[1] = getIntent().getStringExtra("LSA");
        achievements[2] = getIntent().getStringExtra("MGA");
        achievements[3] = getIntent().getStringExtra("MQA");

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> userInfo = sessionManager.getUserDataFromSession();
        QBDataBase db = new QBDataBase(this);

        editTextUsername.setText("" + userInfo.get(SessionManager.KEY_USERNAME));
        editTextUsername.setEnabled(false);

        setupSoundSettings(userInfo, sessionManager);
        logout(sessionManager);
        submitUpdate(db);
    }

    private void setupSoundSettings(HashMap<String, String> userInfo, SessionManager sessionManager) {
        if(userInfo.get(SessionManager.KEY_VSE).equals("1")) soundEffect.setChecked(true);
        else soundEffect.setChecked(false);
        if(userInfo.get(SessionManager.KEY_VBM).equals("1")) backgroundMusic.setChecked(true);
        else backgroundMusic.setChecked(false);
        soundEffect.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                MainActivity.correctSound.setVolume(1, 1);
                MainActivity.wrongSound.setVolume(1, 1);
                MainActivity.onTickSound.setVolume(1,1);
                MainActivity.onFinishSound.setVolume(1, 1);
                sessionManager.setKeyVse(1);
            }
            else {
                MainActivity.correctSound.setVolume(0, 0);
                MainActivity.wrongSound.setVolume(0, 0);
                MainActivity.onTickSound.setVolume(0,0);
                MainActivity.onFinishSound.setVolume(0, 0);
                sessionManager.setKeyVse(0);
            }
        });
        backgroundMusic.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                LoginActivity.main.setVolume(1, 1);
                MainActivity.gamePlaySound.setVolume(1,1);
                sessionManager.setKeyVbm(1);
            }
            else {
                LoginActivity.main.setVolume(0, 0);
                MainActivity.gamePlaySound.setVolume(0,0);
                sessionManager.setKeyVbm(0);
            }
        });
    }

    private void logout(SessionManager sessionManager) {
        back.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Profile.class);
            intent.putExtra("Stars", "" + stars);
            intent.putExtra("ASA", achievements[0]);
            intent.putExtra("LSA", achievements[1]);
            intent.putExtra("MGA", achievements[2]);
            intent.putExtra("MQA", achievements[3]);
            startActivity(intent);
        });
        signout.setOnClickListener(v -> {
            LoginActivity.main.stop();
            sessionManager.logoutUserFromSession();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("NewUser", "false");
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Account Logged Out!", Toast.LENGTH_SHORT).show();
        });
    }

    private void submitUpdate(QBDataBase db) {
        submit.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString();
            String name = editTextName.getText().toString();
            String oldPassword = editTextOldPassword.getText().toString();
            String password = editTextNewPassword.getText().toString();
            String rpassword = editTextReNewPassword.getText().toString();

            if(!name.equals("") || !password.equals("") || !rpassword.equals("") || !oldPassword.equals("")) {
                if(!name.equals("") && oldPassword.equals(""))
                    Toast.makeText(getApplicationContext(), "Please fill in old password to change name.", Toast.LENGTH_SHORT).show();
                if(!oldPassword.equals("") && (name.equals("") && password.equals("") && rpassword.equals("")))
                    Toast.makeText(getApplicationContext(), "Nothing to update.", Toast.LENGTH_SHORT).show();
                if((((!password.equals("") ^ !rpassword.equals("")) || oldPassword.equals("")) || (!oldPassword.equals("") ^ (!password.equals("") || !rpassword.equals("")))) && (!password.equals("") || !rpassword.equals(""))) {
                    Toast.makeText(getApplicationContext(), "Please fill in the necessary fields.", Toast.LENGTH_SHORT).show();
                }
                if((!name.equals("") && !oldPassword.equals("")) || (!password.equals("") && !rpassword.equals("") && !oldPassword.equals(""))) {
                    boolean correctOP = db.loginProfile(username, oldPassword);
                    if(correctOP) {
                        boolean canUpdate = false;
                        if((!name.equals("") && !oldPassword.equals("")) || (password.equals(rpassword)))  canUpdate = true;
                        if(canUpdate) {
                            boolean isUpdated = db.updateProfile(name, username, password);
                            SessionManager sessionManager = new SessionManager(this);
                            if(!name.equals("")) sessionManager.setKeyName(name);
                            if (isUpdated) {
                                Toast.makeText(getApplicationContext(), "Profile Updated.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Homepage.class);
                                startActivity(intent);
                            } else
                                Toast.makeText(getApplicationContext(), "Update failed.", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(getApplicationContext(), "New password and retype new password mismatch.", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Incorrect Password.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}