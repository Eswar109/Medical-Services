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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

// In MainActivity.java
public class MainActivity extends AppCompatActivity {

    private Spinner citySpinner;
    private Button findHospitalsButton;
    private ProgressBar progressBar;
    private TextView statusTextView;
    private RecyclerView hospitalRecyclerView;
    private HospitalAdapter hospitalAdapter;
    private HospitalDataProvider hospitalDataProvider;

    private String selectedCity = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        citySpinner = findViewById(R.id.spinner_filter);
        findHospitalsButton = findViewById(R.id.btn_find_hospitals);
        progressBar = findViewById(R.id.progress_bar);
        statusTextView = findViewById(R.id.tv_status);
        hospitalRecyclerView = findViewById(R.id.recycler_hospitals);

        // Set up RecyclerView
        hospitalRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        hospitalAdapter = new HospitalAdapter();
        hospitalRecyclerView.setAdapter(hospitalAdapter);

        // Initialize hospital data provider
        hospitalDataProvider = new HospitalDataProvider();

        // Set up the spinner with city options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.facility_types, // Your array with city names
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter);

        // Handle spinner selection
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = parent.getItemAtPosition(position).toString();

                // If the first item ("Select Cities") is selected, we don't do anything
                if (position == 0) {
                    statusTextView.setText("Please select a city and press Find Hospitals");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCity = "";
            }
        });

        // Set up find hospitals button click listener
        findHospitalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findHospitalsInCity();
            }
        });
    }

    private void findHospitalsInCity() {
        // Check if a valid city is selected (not the first item)
        if (selectedCity.equals("") || selectedCity.equals("Select Cities")) {
            Toast.makeText(this, "Please select a city first", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show loading state
        progressBar.setVisibility(View.VISIBLE);
        statusTextView.setText("Searching for hospitals in " + selectedCity + "...");
        hospitalRecyclerView.setVisibility(View.GONE);

        // Get hospitals based on selected city
        hospitalDataProvider.getHospitalsInCity(selectedCity, new HospitalDataProvider.HospitalDataCallback() {
            @Override
            public void onHospitalsLoaded(List<Hospital> hospitals) {
                // Display hospitals
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        displayHospitals(hospitals);
                    }
                });
            }

            @Override
            public void onError(String errorMessage) {
                // Show error
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showError(errorMessage);
                    }
                });
            }
        });
    }

    private void displayHospitals(List<Hospital> hospitals) {
        progressBar.setVisibility(View.GONE);

        if (hospitals.isEmpty()) {
            statusTextView.setText("No hospitals found in " + selectedCity);
            hospitalRecyclerView.setVisibility(View.GONE);
        } else {
            statusTextView.setText("Found " + hospitals.size() + " hospitals in " + selectedCity);
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
}