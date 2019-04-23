package com.example.sdenna.firebasetutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayDataActivity extends AppCompatActivity {

    TextView displayTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        displayTextView = (TextView)findViewById(R.id.displayTextView);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");
        String email = intent.getStringExtra("userEmail");

        displayTextView.setText(userName);
        displayTextView.append("\n"+email);
    }
}
