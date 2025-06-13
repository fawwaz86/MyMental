package com.example.mymental;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private TextView textViewLocation, textViewPlace, textViewSuggestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        textViewLocation = findViewById(R.id.textView_location);
        textViewPlace = findViewById(R.id.textView_place);
        textViewSuggestion = findViewById(R.id.textView_suggestion);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        requestLocationPermission();
        Button btnGoOutside = findViewById(R.id.btn_go_outside);
        btnGoOutside.setOnClickListener(v -> {
            Intent intent = new Intent(LocationActivity.this, LogActivity.class);
            startActivity(intent);
        });
    }

    private void requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            startLocationUpdates();
        }
    }

    private void startLocationUpdates() {
        LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000)
                .setMinUpdateIntervalMillis(2000)
                .build();

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                double latitude = locationResult.getLastLocation().getLatitude();
                double longitude = locationResult.getLastLocation().getLongitude();
                float accuracy = locationResult.getLastLocation().getAccuracy();

                textViewLocation.setText("Latitude: " + latitude + "\nLongitude: " + longitude);

                // Reverse Geocoding
                Geocoder geocoder = new Geocoder(LocationActivity.this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    if (addresses != null && !addresses.isEmpty()) {
                        String address = addresses.get(0).getAddressLine(0);
                        textViewPlace.setText(address);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (accuracy > 30) {
                    textViewSuggestion.setText("You seem to be indoors. Please go outside for better accuracy.");
                } else {
                    textViewSuggestion.setText("");
                }
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, getMainLooper());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }
}
