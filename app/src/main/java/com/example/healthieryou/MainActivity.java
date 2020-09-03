package com.example.healthieryou;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    Sensor accelerometer;
    ImageView ivLogo;
    float lastXValue = 0;
    float lastYValue = 0;
    float steps = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        ivLogo = findViewById(R.id.ivLogo);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ObjectAnimator rotationAnimation = ObjectAnimator.ofFloat(ivLogo, "rotation", 0F, 360F);
        rotationAnimation.setDuration(1500);
        ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(ivLogo, "scaleX", 1F, 1.5F, 1F);
        scaleXAnimation.setDuration(1500);
        ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(ivLogo, "scaleY", 1F, 1.5F, 1F);
        scaleYAnimation.setDuration(1500);
        AnimatorListener animationListener = new AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };
        rotationAnimation.addListener(animationListener);
        rotationAnimation.start();
        scaleXAnimation.start();
        scaleYAnimation.start();
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