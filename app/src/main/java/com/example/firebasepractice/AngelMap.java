package com.example.firebasepractice;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.firebasepractice.databinding.ActivityAngelMapBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AngelMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityAngelMapBinding binding;
    DatabaseReference databaseReference;
    ArrayList<LatLng> latLngArrayList = new ArrayList<>();

    private static ArrayList<Double> latitude = new ArrayList<>();
    private static ArrayList<Double> longitude = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAngelMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Log.v("hello", "oncreate  "+latitude.size());


//
//        for (int i = 0; i<latitude.size(); i++){
//            latLngArrayList.add(new LatLng(Double.parseDouble(latitude.get(i)), Double.parseDouble(longitude.get(i))));
//        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Map");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                double ini_lat = 0;
                double init_lon = 0;
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                    String lat = dataSnapshot.child("latitude").getValue().toString();
                    String lon = dataSnapshot.child("longitude").getValue().toString();

                    double lati = Double.parseDouble(lat);
                    double lonn = Double.parseDouble(lon);
                    ini_lat = lati;
                    init_lon = lonn;

                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(lati,lonn))
                            .anchor(0.5f, 0.5f)
                            .title("Title2"));

                }
                LatLng lng = new LatLng(ini_lat, init_lon);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lng, 10.0f));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}