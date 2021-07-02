package com.example.mdproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private Button completeButton;
    private EditText editTextName;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextRPassword;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        editTextName = findViewById(R.id.etName);
        editTextUsername = findViewById(R.id.etUsername);
        editTextPassword = findViewById(R.id.etPassword);
        editTextRPassword = findViewById(R.id.etRPassword);
        completeButton = findViewById(R.id.btnComplete);
        back = findViewById(R.id.btnBack);

        QBDataBase db = new QBDataBase(this);

        submitRegistration(db);
    }


    private void submitRegistration(QBDataBase db) {
        back.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });
        completeButton.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();
            String rpassword = editTextRPassword.getText().toString();

            if(name.equals("") || username.equals("") || password.equals("") || rpassword.equals(""))
                Toast.makeText(getApplicationContext(), "Please fill in all the fields above.", Toast.LENGTH_SHORT).show();
            else {
                if(password.equals(rpassword)) {
                    boolean userExist = db.checkIfUserExist(username);
                    if(!userExist) {
                        boolean isInserted = db.insertNewProfile(name, username, password);
                        if(isInserted) {
                            db.createAchievementRecord(username);
                            db.createTutorialRecord(username);
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.putExtra("NewUser", "true");
                            startActivity(intent);
                        }
                        else Toast.makeText(getApplicationContext(), "Registration failed!", Toast.LENGTH_SHORT).show();
                    }
                    else Toast.makeText(getApplicationContext(), "Username is already exist.", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "Password and Re-typed Password mismatched!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}