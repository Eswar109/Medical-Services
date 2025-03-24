package com.example.medicalservices;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

public class HospitalDataProvider {

    // Interface for callback when hospitals data is loaded
    public interface HospitalDataCallback {
        void onHospitalsLoaded(List<Hospital> hospitals);
        void onError(String errorMessage);
    }

    // In a real app, this would call an API service or use Google Places API
    public void getNearbyHospitals(double latitude, double longitude, HospitalDataCallback callback) {
        // Simulate network delay
        new Handler().postDelayed(() -> {
            try {
                // For demonstration purposes, return mock data
                List<Hospital> hospitals = getMockHospitals(latitude, longitude);
                callback.onHospitalsLoaded(hospitals);
            } catch (Exception e) {
                callback.onError("Failed to load hospitals: " + e.getMessage());
            }
        }, 1500);
    }

    // Mock data for demonstration
    private List<Hospital> getMockHospitals(double userLatitude, double userLongitude) {
        List<Hospital> hospitals = new ArrayList<>();

        // In a real app, these would come from an API
        hospitals.add(new Hospital(
                "h1",
                "City General Hospital",
                "123 Main Street, Cityville",
                "+1-555-123-4567",
                userLatitude + 0.01,
                userLongitude - 0.01,
                0.8,
                true
        ));

        hospitals.add(new Hospital(
                "h2",
                "Riverside Medical Center",
                "456 Park Avenue, Cityville",
                "+1-555-987-6543",
                userLatitude - 0.015,
                userLongitude + 0.008,
                1.2,
                true
        ));

        hospitals.add(new Hospital(
                "h3",
                "Westside Health Clinic",
                "789 Oak Drive, Cityville",
                "+1-555-456-7890",
                userLatitude + 0.02,
                userLongitude + 0.02,
                1.9,
                false
        ));

        hospitals.add(new Hospital(
                "h4",
                "North County Medical Group",
                "321 Pine Road, Cityville",
                "+1-555-567-8901",
                userLatitude - 0.025,
                userLongitude - 0.018,
                2.4,
                true
        ));

        hospitals.add(new Hospital(
                "h5",
                "Southside Community Hospital",
                "987 Elm Street, Cityville",
                "+1-555-345-6789",
                userLatitude + 0.03,
                userLongitude - 0.025,
                3.1,
                true
        ));

        return hospitals;
    }
}
