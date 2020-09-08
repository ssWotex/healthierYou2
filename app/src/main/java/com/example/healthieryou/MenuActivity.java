package com.example.healthieryou;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class MenuActivity extends AppCompatActivity {
    Button btnSprint;
    Button btnRun;
    Button btnStep;
    Button btnAbout;
    Button btnQuestionmark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        btnSprint = findViewById(R.id.btnSprint);
        btnRun = findViewById(R.id.btnRun);
        btnStep = findViewById(R.id.btnStep);
        btnAbout = findViewById(R.id.btnAbout);
        btnQuestionmark = findViewById(R.id.btnQuestionmark);

        btnSprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SprintTestActivity.class);
                startActivity(intent);
            }
        });
        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapMyRunActivity.class);
                startActivity(intent);
            }
        });
        btnStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StepCounterActivity.class);
                startActivity(intent);
            }
        });
        btnQuestionmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), QuestionmarkActivity.class);
                startActivity(intent);
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });


    }


}