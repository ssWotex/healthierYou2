package com.example.healthieryou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class StepCounterActivity extends AppCompatActivity implements SensorEventListener {

    TextView tv_steps;
    SensorManager sensorManager;
    Sensor accelerometer;
    float lastXValue = 0;
    float lastYValue = 0;
    float steps = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);
        tv_steps = findViewById(R.id.tv_steps);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
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