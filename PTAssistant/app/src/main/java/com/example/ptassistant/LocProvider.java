package com.example.ptassistant;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocProvider {

    private FusedLocationProviderClient cli;
    private Context con;
    Activity a;

    double lat;
    double lon;
    Location l1;

    public LocProvider(Context con, Activity a) {
        this.con = con;
        this.a=a;
        cli = LocationServices.getFusedLocationProviderClient(con);

        lat = 0;
        lon = 0;
        l1= new Location("Current locatiin");
    }

    public void getCurrentLoc() {

        if (ContextCompat.checkSelfPermission(con, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(a, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        cli.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // GPS location can be null if GPS is switched off
                        if (location != null) {
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                            l1 = location;
                            System.out.println(l1.getAltitude()+"::"+l1.getLongitude());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MapDemoActivity", "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });

    }

    public double distanceToGym(double lat2, double lon2){
        getCurrentLoc();
        Location lo = new Location("Gym");
        lo.setLongitude(lon2);
        lo.setLatitude(lat2);
        return l1.distanceTo(lo);
    }

//    private LocationCallback mLocationCallback = new LocationCallback() {
//
//        @Override
//        public void onLocationResult(LocationResult locationResult) {
//            Location mLastLocation = locationResult.getLastLocation();
//
//        }
//    };
//
//    @SuppressLint("MissingPermission")
//    private void requestNewLocationData() {
//
//        // Initializing LocationRequest
//        // object with appropriate methods
//        LocationRequest mLocationRequest = new LocationRequest();
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        mLocationRequest.setInterval(5);
//        mLocationRequest.setFastestInterval(0);
//        mLocationRequest.setNumUpdates(1);
//
//        // setting LocationRequest
//        // on FusedLocationClient
//        cli = LocationServices.getFusedLocationProviderClient(a);
//        cli.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
//    }

}

