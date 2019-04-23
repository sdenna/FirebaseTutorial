package com.example.sdenna.firebasetutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

   // private FirebaseAuth mAuth;
    EditText userNameEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       // mAuth = FirebaseAuth.getInstance();

        userNameEditText = (EditText)findViewById(R.id.editTextUserName);
        passwordEditText = (EditText)findViewById(R.id.editTextPassword);
    }

    public void onClickVerifyLogin(View v) {
        Intent intent = new Intent(this, DisplayDataActivity.class);

        // fb code here to try to log in

        startActivity(intent);
    }

    public void onClickCancel(View v) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }


}
