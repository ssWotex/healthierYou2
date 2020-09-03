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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

public class MapMyRunActivity extends AppCompatActivity implements LocationListener {

    Button btnStart;
    TextView tv_lol;
    double firstLocationLatitude;
    double firstLocationLongitude;

    double endLocationLatitude;
    double endLocationLongitude;

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
                    firstLocationLatitude = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude();
                    firstLocationLongitude = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude();
                    System.out.println("Lat:"+ firstLocationLatitude + ", Long: " + firstLocationLongitude);
                    MapsFragment.mapFragment.getMapAsync(startCallback);
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
                    endLocationLatitude = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude();
                    endLocationLongitude = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude();
                    System.out.println("Lat:"+ endLocationLatitude + ", Long: " + endLocationLongitude);
                    MapsFragment.mapFragment.getMapAsync(endCallback);
                }
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
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

    private OnMapReadyCallback startCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng firstPosition = new LatLng(firstLocationLatitude, firstLocationLongitude);
            googleMap.addMarker(new MarkerOptions().position(firstPosition).title("Marker in Sydney"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(firstPosition));
        }
    };

    private OnMapReadyCallback endCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng endPosition = new LatLng(endLocationLatitude, endLocationLongitude);
            googleMap.addMarker(new MarkerOptions().position(endPosition).title("Marker in Sydney"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(endPosition));
        }
    };
}