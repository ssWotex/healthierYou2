package com.example.healthieryou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapMyRunActivity extends AppCompatActivity implements LocationListener {
    double firstLocationLatitude = 0;
    double firstLocationLongitude = 0;

    double endLocationLatitude = 0;
    double endLocationLongitude = 0;

    Button btnLocation;
    TextView textView;
    LocationManager locationManager;
    FragmentManager fragmentManager;
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_my_run);

        if (ActivityCompat.checkSelfPermission(MapMyRunActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapMyRunActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapMyRunActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            ActivityCompat.requestPermissions(MapMyRunActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
            ActivityCompat.requestPermissions(MapMyRunActivity.this, new String[]{Manifest.permission.INTERNET}, 101);
            System.out.println("permission granted");
        }

        textView = findViewById(R.id.tv_lol);

        locationManager = (LocationManager) this.getApplicationContext().getSystemService(LOCATION_SERVICE);
        fragmentManager = getSupportFragmentManager();
        mapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.map);

        btnLocation = findViewById(R.id.btnLocation);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnLocation.getText().toString().equals("Start")){
                    mapFragment.getMapAsync(removeMarkersCallback);
                    btnLocation.setText("Finish");
                    if (ActivityCompat.checkSelfPermission(MapMyRunActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapMyRunActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, MapMyRunActivity.this);
                    if(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) != null) {
                        firstLocationLatitude = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude();
                        firstLocationLongitude = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude();
                        mapFragment.getMapAsync(startCallback);
                    }
                }
                else{
                    btnLocation.setText("Start");
                    if (ActivityCompat.checkSelfPermission(MapMyRunActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapMyRunActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, MapMyRunActivity.this);
                    if(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) != null) {
                        endLocationLatitude = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude();
                        endLocationLongitude = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude();
                        mapFragment.getMapAsync(endCallback);
                        mapFragment.getMapAsync(drawRouteCallback);
                    }
                }
            }
        });
    }
    private OnMapReadyCallback startCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng firstPosition = new LatLng(firstLocationLatitude, firstLocationLongitude);
            googleMap.addMarker(new MarkerOptions().position(firstPosition).title("Start Location"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(firstPosition));
        }
    };

    private OnMapReadyCallback endCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng endPosition = new LatLng(endLocationLatitude, endLocationLongitude);
            googleMap.addMarker(new MarkerOptions().position(endPosition).title("End Location"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(endPosition));
        }
    };

    private OnMapReadyCallback removeMarkersCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) { ;
            googleMap.clear();
        }
    };

    private OnMapReadyCallback drawRouteCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) { ;
            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.add(new LatLng(firstLocationLatitude, firstLocationLongitude), new LatLng(endLocationLatitude, endLocationLongitude));
            polylineOptions.color(Color.BLACK);
            googleMap.addPolyline(polylineOptions);
        }
    };

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

}