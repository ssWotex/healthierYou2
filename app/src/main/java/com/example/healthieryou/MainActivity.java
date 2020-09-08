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

public class MainActivity extends AppCompatActivity{

    ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}