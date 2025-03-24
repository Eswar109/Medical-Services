package com.example.medicalservices;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder> {

    private List<Hospital> hospitals = new ArrayList<>();

    public void setHospitals(List<Hospital> hospitals) {
        this.hospitals = hospitals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hospital, parent, false);
        return new HospitalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HospitalViewHolder holder, int position) {
        Hospital hospital = hospitals.get(position);
        holder.bind(hospital);
    }

    @Override
    public int getItemCount() {
        return hospitals.size();
    }

    static class HospitalViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView distanceTextView;
        private TextView addressTextView;
        private TextView phoneTextView;
        private TextView emergencyStatusTextView;
        private Button navigateButton;
        private Button callButton;

        public HospitalViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv_hospital_name);
            distanceTextView = itemView.findViewById(R.id.tv_hospital_distance);
            addressTextView = itemView.findViewById(R.id.tv_hospital_address);
            phoneTextView = itemView.findViewById(R.id.tv_hospital_phone);
            emergencyStatusTextView = itemView.findViewById(R.id.tv_emergency_status);
            navigateButton = itemView.findViewById(R.id.btn_navigate);
            callButton = itemView.findViewById(R.id.btn_call);
        }

        public void bind(Hospital hospital) {
            nameTextView.setText(hospital.getName());
            distanceTextView.setText(hospital.getFormattedDistance());
            addressTextView.setText(hospital.getAddress());
            phoneTextView.setText(hospital.getPhoneNumber());

            if (hospital.hasEmergency()) {
                emergencyStatusTextView.setText("Emergency Services Available");
                emergencyStatusTextView.setVisibility(View.VISIBLE);
            } else {
                emergencyStatusTextView.setVisibility(View.GONE);
            }

            // Set up navigation button
            navigateButton.setOnClickListener(v -> {
                // Open Google Maps with directions to the hospital
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" +
                        hospital.getLatitude() + "," + hospital.getLongitude() +
                        "&mode=d");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                if (mapIntent.resolveActivity(itemView.getContext().getPackageManager()) != null) {
                    itemView.getContext().startActivity(mapIntent);
                }
            });

            // Set up call button
            callButton.setOnClickListener(v -> {
                // Call the hospital
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + hospital.getPhoneNumber()));
                itemView.getContext().startActivity(callIntent);
            });
        }
    }
}