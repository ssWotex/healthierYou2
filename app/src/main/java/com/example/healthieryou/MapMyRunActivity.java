package com.example.healthieryou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;
import com.orm.SugarContext;

import java.util.Map;

public class MapMyRunActivity extends AppCompatActivity implements LocationListener {

    Button btnStart;
    TextView tv_lol;
    Coordinates coordinates;
    SupportMapFragment mapFragment;

    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_my_run);
        btnStart = findViewById(R.id.btn_Start);
        tv_lol = findViewById(R.id.tv_lol);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnStart.getText().toString().equals("Start")){
                    btnStart.setText("Fertig");
                    if (ActivityCompat.checkSelfPermission(MapMyRunActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapMyRunActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MapMyRunActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
                        ActivityCompat.requestPermissions(MapMyRunActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                        ActivityCompat.requestPermissions(MapMyRunActivity.this, new String[]{Manifest.permission.INTERNET}, 101);
                        System.out.println("permission granted");
                    }
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, MapMyRunActivity.this);
                    tv_lol.setText("Longitude: "+String.valueOf(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude()));
                    coordinates = new Coordinates(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude(),locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude(), System.currentTimeMillis());
                    coordinates.save();
                }
                else{
                    btnStart.setText("Start");
                    if (ActivityCompat.checkSelfPermission(MapMyRunActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapMyRunActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MapMyRunActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
                        ActivityCompat.requestPermissions(MapMyRunActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                        ActivityCompat.requestPermissions(MapMyRunActivity.this, new String[]{Manifest.permission.INTERNET}, 101);
                        System.out.println("permission granted");
                    }
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, MapMyRunActivity.this);
                    tv_lol.setText("Longitude: "+String.valueOf(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude()));
                    coordinates = Coordinates.listAll(Coordinates.class).get(0);
                    coordinates.setEndTime(System.currentTimeMillis());
                    coordinates.setLatitudeEnd(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude());
                    coordinates.setLongitudeEnd(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude());
                }
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        tv_lol.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        String lol = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).toString();
        System.out.println("WAAAAAAARNIIIIIIIING" + lol);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}