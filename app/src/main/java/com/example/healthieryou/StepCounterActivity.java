package com.example.healthieryou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class StepCounterActivity extends AppCompatActivity {

    TextView tv_steps;
    long steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);

        tv_steps = findViewById(R.id.tv_steps);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new VerifyDataTask(getApplicationContext()).execute();
                steps = new VerifyDataTask(getApplicationContext()).getTotal();
                tv_steps.setText(String.valueOf(steps));
            }
        }, 2000);
    }
}