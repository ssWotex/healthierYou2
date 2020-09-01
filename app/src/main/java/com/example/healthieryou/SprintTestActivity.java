package com.example.healthieryou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.MenuItemHoverListener;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class SprintTestActivity extends AppCompatActivity implements SensorEventListener {
    Button btnBack;
    Button btnGo;
    Button btnShare;
    TextView txtTimer;
    TextView nbMeter;
    TextView txtTopSpeed;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    boolean inMotion = false;
    int Seconds, MilliSeconds;
    int setDistance;
    Handler handler;
    SensorManager sensorManager;
    Sensor accelerometer;
    List<Float> accelerations;
    List<Float> speed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprint_test);
        btnBack = findViewById(R.id.btnBack);
        btnGo = findViewById(R.id.btnGo);
        btnShare = findViewById(R.id.btnShare);
        txtTimer = findViewById(R.id.txtTimer);
        handler = new Handler();
        nbMeter = findViewById(R.id.nbMeter);
        accelerations = new ArrayList<Float>();
        speed = new ArrayList<Float>();
        txtTopSpeed = findViewById(R.id.txtTopSpeed);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!nbMeter.getText().toString().equals("") ) {
                    inMotion = true;
                    setDistance = Integer.parseInt(nbMeter.getText().toString());
                    System.out.println(setDistance);
                    StartTime = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable, 0);
                }
            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Ich bin in " + Seconds + ":" + MilliSeconds + " Sekunden " + setDistance + " Meter gerannt!");
                Intent shareIntent = Intent.createChooser(whatsappIntent, null);
                startActivity(shareIntent);

            }
        });
    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Seconds = Seconds % 60;

            MilliSeconds = (int) (UpdateTime % 1000);

            txtTimer.setText("" + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));

            handler.postDelayed(this, 0);
        }
    };

    float median;

    public void calc(){
        if(inMotion == true) {
            Collections.sort(accelerations);
            median = accelerations.get(accelerations.size() / 2);
            //System.out.println(median);
            if (median * Seconds >= setDistance) {
                handler.removeCallbacks(runnable);
                inMotion = false;

                Collections.sort(speed);
                float topSpeed = speed.get(speed.size()-1) * 3.6f;
                txtTopSpeed.setText("" + topSpeed);
                speed.clear();
                accelerations.clear();
            }
        }
    }

    int i = 0;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(inMotion == true){
            if(sensorEvent.values[0] > 1) {
                System.out.println(sensorEvent.values[0]);
                accelerations.add(sensorEvent.values[0]);
                if(speed.size() < 1){
                    speed.add(sensorEvent.values[0]*0.25f);
                }else{
                    if(sensorEvent.values[0] > accelerations.get(accelerations.size()-2)) {
                        speed.add(speed.get(speed.size() - 1) + (sensorEvent.values[0] - accelerations.get(accelerations.size() - 2)) * 0.25f);
                    }else{
                        speed.add(speed.get(speed.size()-1) + sensorEvent.values[0] * 0.25f);
                    }
                    //System.out.println(speed.get(speed.size()-1)*3.6f);
                }

                i++;
                if (i > 2) {
                    calc();
                    i = 0;
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
}