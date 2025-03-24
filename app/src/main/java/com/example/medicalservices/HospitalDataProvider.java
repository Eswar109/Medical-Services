package com.example.medicalservices;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

// HospitalDataProvider.java
public class HospitalDataProvider {

    // Interface for callback when hospitals data is loaded
    public interface HospitalDataCallback {
        void onHospitalsLoaded(List<Hospital> hospitals);
        void onError(String errorMessage);
    }

    // Get hospitals in a specific city
    public void getHospitalsInCity(String city, HospitalDataCallback callback) {
        // Simulate network delay
        new Handler().postDelayed(() -> {
            try {
                // For demonstration purposes, return mock data based on city
                List<Hospital> hospitals = getMockHospitalsForCity(city);
                callback.onHospitalsLoaded(hospitals);
            } catch (Exception e) {
                callback.onError("Failed to load hospitals: " + e.getMessage());
            }
        }, 1500);
    }

    // Mock data for demonstration - different hospitals for each city
    private List<Hospital> getMockHospitalsForCity(String city) {
        List<Hospital> hospitals = new ArrayList<>();

        switch (city) {
            case "Tenali":
                hospitals.add(new Hospital(
                        "t1",
                        "Tenali General Hospital",
                        "Main Road, Tenali",
                        "+91-8642-234567",
                        16.2406, 80.6400, // Tenali coordinates
                        0.0, // No distance needed for city-based search
                        true
                ));

                hospitals.add(new Hospital(
                        "t2",
                        "Sri Ram Hospital",
                        "Gandhi Road, Tenali",
                        "+91-8642-345678",
                        16.2389, 80.6450,
                        0.0,
                        true
                ));

                hospitals.add(new Hospital(
                        "t3",
                        "Sai Medical Center",
                        "Station Road, Tenali",
                        "+91-8642-456789",
                        16.2450, 80.6380,
                        0.0,
                        false
                ));
                break;

            case "Guntur":
                hospitals.add(new Hospital(
                        "g1",
                        "Government General Hospital Guntur",
                        "Main Road, Guntur",
                        "+91-863-2234567",
                        16.3067, 80.4365,
                        0.0,
                        true
                ));

                hospitals.add(new Hospital(
                        "g2",
                        "Ramesh Hospitals",
                        "Kothapet, Guntur",
                        "+91-863-2345678",
                        16.3156, 80.4362,
                        0.0,
                        true
                ));

                hospitals.add(new Hospital(
                        "g3",
                        "NRI General Hospital",
                        "Chinakakani, Guntur",
                        "+91-863-2456789",
                        16.3079, 80.4398,
                        0.0,
                        true
                ));

                hospitals.add(new Hospital(
                        "g4",
                        "St. Joseph Hospital",
                        "Gujjanagundla, Guntur",
                        "+91-863-2567890",
                        16.3035, 80.4311,
                        0.0,
                        false
                ));
                break;

            case "Vijayawada":
                hospitals.add(new Hospital(
                        "v1",
                        "Government General Hospital Vijayawada",
                        "Siddhartha Nagar, Vijayawada",
                        "+91-866-2475240",
                        16.5062, 80.6480,
                        0.0,
                        true
                ));

                hospitals.add(new Hospital(
                        "v2",
                        "Manipal Hospital",
                        "Tadepalli, Vijayawada",
                        "+91-866-2345678",
                        16.4778, 80.6180,
                        0.0,
                        true
                ));

                hospitals.add(new Hospital(
                        "v3",
                        "Andhra Hospitals",
                        "Gollapudi, Vijayawada",
                        "+91-866-2456789",
                        16.5135, 80.6509,
                        0.0,
                        true
                ));
                break;

            case "Vadlamudi":
                hospitals.add(new Hospital(
                        "vd1",
                        "Vignan Health Center",
                        "Vignan University Campus, Vadlamudi",
                        "+91-9876543210",
                        16.2359, 80.5566,
                        0.0,
                        false
                ));

                hospitals.add(new Hospital(
                        "vd2",
                        "Vadlamudi Community Hospital",
                        "Main Road, Vadlamudi",
                        "+91-8642-234567",
                        16.2362, 80.5570,
                        0.0,
                        true
                ));
                break;
        }

        return hospitals;
    }
}