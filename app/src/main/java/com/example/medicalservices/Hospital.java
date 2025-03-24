package com.example.medicalservices;

public class Hospital {
    private String id;
    private String name;
    private String address;
    private String phoneNumber;
    private double latitude;
    private double longitude;
    private double distance; // in kilometers
    private boolean hasEmergency;

    public Hospital(String id, String name, String address, String phoneNumber,
                    double latitude, double longitude, double distance, boolean hasEmergency) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
        this.hasEmergency = hasEmergency;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public double getDistance() { return distance; }
    public boolean hasEmergency() { return hasEmergency; }

    // Formatted distance string (e.g., "1.2 km")
    public String getFormattedDistance() {
        // If distance is 0.0 (city-based search), don't display distance
        if (distance == 0.0) {
            return "";
        }
        if (distance < 1.0) {
            return String.format("%.0f m", distance * 1000);
        } else {
            return String.format("%.1f km", distance);
        }
    }
}
