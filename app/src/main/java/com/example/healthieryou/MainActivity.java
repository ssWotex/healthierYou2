package com.example.healthieryou;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    Sensor accelerometer;
    TextView tv_AccelerometerX;
    TextView tv_AccelerometerY;
    TextView tv_AccelerometerZ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        tv_AccelerometerX = findViewById(R.id.tv_AccelerometerX);
        tv_AccelerometerY = findViewById(R.id.tv_AccelerometerY);
        tv_AccelerometerZ = findViewById(R.id.tv_AccelerometerZ);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        tv_AccelerometerX.setText(String.valueOf(sensorEvent.values[0]));
        tv_AccelerometerY.setText(String.valueOf(sensorEvent.values[1]));
        tv_AccelerometerZ.setText(String.valueOf(sensorEvent.values[2]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}