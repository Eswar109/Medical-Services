package com.example.medicalservices;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    private Button findHospitalsButton;
    private ProgressBar progressBar;
    private TextView statusTextView;
    private RecyclerView hospitalRecyclerView;
    private HospitalAdapter hospitalAdapter;

    private FusedLocationProviderClient fusedLocationClient;
    private HospitalDataProvider hospitalDataProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        findHospitalsButton = findViewById(R.id.btn_find_hospitals);
        progressBar = findViewById(R.id.progress_bar);
        statusTextView = findViewById(R.id.tv_status);
        hospitalRecyclerView = findViewById(R.id.recycler_hospitals);

        // Set up RecyclerView
        hospitalRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        hospitalAdapter = new HospitalAdapter();
        hospitalRecyclerView.setAdapter(hospitalAdapter);

        // Initialize location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Initialize hospital data provider
        hospitalDataProvider = new HospitalDataProvider();

        // Set up find hospitals button click listener
        findHospitalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNearbyHospitals();
            }
        });
    }
    private void findNearbyHospitals() {
        // Check for location permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Request location permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        statusTextView.setText("Getting your location...");
        hospitalRecyclerView.setVisibility(View.GONE);

        // Get current location
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // Location retrieved successfully
                            statusTextView.setText("Searching for hospitals near you...");

                            // Get nearby hospitals based on location
                            hospitalDataProvider.getNearbyHospitals(
                                    location.getLatitude(),
                                    location.getLongitude(),
                                    new HospitalDataProvider.HospitalDataCallback() {
                                        @Override
                                        public void onHospitalsLoaded(List<Hospital> hospitals) {
                                            // Display hospitals
                                            displayHospitals(hospitals);
                                        }

                                        @Override
                                        public void onError(String errorMessage) {
                                            // Show error
                                            showError(errorMessage);
                                        }
                                    });
                        } else {
                            showError("Could not get your location. Please try again.");
                        }
                    }
                })
                .addOnFailureListener(this, e -> {
                    showError("Failed to get your location: " + e.getMessage());
                });
    }
    private void displayHospitals(List<Hospital> hospitals) {
        progressBar.setVisibility(View.GONE);

        if (hospitals.isEmpty()) {
            statusTextView.setText("No hospitals found nearby.");
            hospitalRecyclerView.setVisibility(View.GONE);
        } else {
            statusTextView.setText("Found " + hospitals.size() + " hospitals nearby");
            hospitalAdapter.setHospitals(hospitals);
            hospitalRecyclerView.setVisibility(View.VISIBLE);
        }
    }
    private void showError(String errorMessage) {
        progressBar.setVisibility(View.GONE);
        statusTextView.setText("Error: " + errorMessage);
        hospitalRecyclerView.setVisibility(View.GONE);
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, try again
                findNearbyHospitals();
            } else {
                showError("Location permission is required to find nearby hospitals.");
            }
        }
    }
}