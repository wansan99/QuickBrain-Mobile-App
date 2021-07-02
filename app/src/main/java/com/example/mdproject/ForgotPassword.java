package com.example.mdproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ForgotPassword extends AppCompatActivity {

    private Button submit;
    private ImageButton back;
    private EditText editTextUsername, editTextPassword, editTextRePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        submit = findViewById(R.id.btnSubmit2);
        back = findViewById(R.id.btnBack);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextNewPassword);
        editTextRePassword = findViewById(R.id.editTextReNewPassword);

        QBDataBase db = new QBDataBase(this);

        setClickable(db);
    }

    private void setClickable(QBDataBase db) {
        back.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("NewUser", "false");
            startActivity(intent);
        });
        submit.setOnClickListener(v -> resetPassword(db));
    }

    private void resetPassword(QBDataBase db) {
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        String rpassword = editTextRePassword.getText().toString();

        if(username.equals("") || password.equals("") || rpassword.equals(""))
            Toast.makeText(getApplicationContext(), "Please fill in all the fields above.", Toast.LENGTH_SHORT).show();
        else {
            if(password.equals(rpassword)) {
                boolean userExist = db.checkIfUserExist(username);
                if(userExist) {
                    boolean isUpdated = db.insertNewProfile("", username, password);
                    if(isUpdated) {
                        Toast.makeText(getApplicationContext(), "Password Changed!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.putExtra("NewUser", "false");
                        startActivity(intent);
                    }
                    else Toast.makeText(getApplicationContext(), "Update failed!", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getApplicationContext(), "Username not exist.", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(getApplicationContext(), "Password and Re-typed Password mismatched!", Toast.LENGTH_SHORT).show();
        }
    }
}