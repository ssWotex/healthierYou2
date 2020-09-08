package com.example.healthieryou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class StepCounterActivity extends AppCompatActivity implements SensorEventListener {

    TextView tv_steps;
    TextView txtGoal;
    EditText nbGoal;
    SensorManager sensorManager;
    Button btnGoal;
    Button btnBack;
    Sensor accelerometer;
    float lastXValue = 0;
    float lastYValue = 0;
    float steps = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);
        tv_steps = findViewById(R.id.tv_steps);
        txtGoal = findViewById(R.id.txtGoal);
        nbGoal = findViewById(R.id.nbGoal);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        btnGoal = findViewById(R.id.btnGoal);
        btnBack = findViewById(R.id.btnBack3);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });

        btnGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtGoal.setText("/ "+ nbGoal.getText());
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(lastYValue != 0 && lastXValue != 0) {
            if ((sensorEvent.values[0]  > lastXValue + 2.0 || sensorEvent.values[0] < lastXValue - 2.0 ) && (sensorEvent.values[1]  > lastYValue + 2.0 || sensorEvent.values[1] < lastYValue - 2.0)) {
                steps += 0.3333333333333f;
            }
        }
        lastXValue = sensorEvent.values[0];
        lastYValue = sensorEvent.values[1];
        tv_steps.setText(String.valueOf(Math.round(steps)));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, 2147483646);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}