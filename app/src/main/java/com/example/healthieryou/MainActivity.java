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
    TextView tv_AccelerometerX, tv_AccelerometerY, tv_AccelerometerZ, highestX, highestY, lowestX, lowestY, tv_steps, tv_lastX, tv_lastY;
    float highestXValue = 0;
    float lowestXValue = 0;
    float highestYValue = 0;
    float lowestYValue = 0;
    float lastXValue = 0;
    float lastYValue = 0;
    float steps = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        tv_AccelerometerX = findViewById(R.id.tv_AccelerometerX);
        tv_AccelerometerY = findViewById(R.id.tv_AccelerometerY);
        tv_AccelerometerZ = findViewById(R.id.tv_AccelerometerZ);
        highestX = findViewById(R.id.highestX);
        highestY = findViewById(R.id.highestY);
        lowestY = findViewById(R.id.lowestY);
        lowestX = findViewById(R.id.lowestX);
        tv_steps = findViewById(R.id.tv_steps);
        tv_lastX = findViewById(R.id.tv_lastX);
        tv_lastY = findViewById(R.id.tv_lastY);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        tv_AccelerometerX.setText(String.valueOf(sensorEvent.values[0]));
        tv_AccelerometerY.setText(String.valueOf(sensorEvent.values[1]));
        tv_AccelerometerZ.setText(String.valueOf(sensorEvent.values[2]));
        if(sensorEvent.values[0] > highestXValue){
            highestXValue = sensorEvent.values[0];
            highestX.setText(String.valueOf(sensorEvent.values[0]));
        }
        if(sensorEvent.values[0] < lowestXValue){
            lowestXValue = sensorEvent.values[0];
            lowestX.setText(String.valueOf(sensorEvent.values[0]));
        }
        if(sensorEvent.values[1] > highestYValue){
            highestYValue = sensorEvent.values[1];
            highestY.setText(String.valueOf(sensorEvent.values[1]));
        }
        if(sensorEvent.values[1] < lowestYValue){
            lowestYValue = sensorEvent.values[1];
            lowestY.setText(String.valueOf(sensorEvent.values[1]));
        }
        if(lastYValue != 0 && lastXValue != 0) {
            if ((sensorEvent.values[0] + 10.0 > lastXValue || sensorEvent.values[0] - 0.002 < lastXValue) && (sensorEvent.values[1] + 2.0 > lastYValue || sensorEvent.values[1] - 0.0002 < lastYValue)) {
                steps++;
                tv_steps.setText(String.valueOf(steps));
            }
        }
        lastXValue = sensorEvent.values[0];
        lastYValue = sensorEvent.values[1];
        tv_lastY.setText(String.valueOf(lastYValue));
        tv_lastX.setText(String.valueOf(lastXValue));

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}