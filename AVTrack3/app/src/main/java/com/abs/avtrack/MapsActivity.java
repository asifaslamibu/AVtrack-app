package com.abs.avtrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback{
    public static String veh_name,devicetime,speed;
    public static Double lat,lng;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_working);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle((CharSequence) "AVTrack");
        Intent i = getIntent();
        veh_name = i.getStringExtra("vname");
        speed =  i.getStringExtra("speed");
        devicetime = i.getStringExtra("devicetime");
        lat = Double.valueOf(i.getStringExtra("lat"));
        lng = Double.valueOf(i.getStringExtra("lng"));
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(lat, lng);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Plate No: "+veh_name +" Speed: "+speed));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,14));



    }

    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(this, Dashboard.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        return true;
    }

}