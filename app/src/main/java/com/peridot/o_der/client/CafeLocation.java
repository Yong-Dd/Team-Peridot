package com.peridot.o_der.client;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CafeLocation extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cafe_location);

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCoffeeShops(map);
            }
        });

        Button back_Btn = findViewById(R.id.back_Btn);
        back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        map = googleMap;
        // 위치 지정
        LatLng myposition = new LatLng(37.54130,126.83816);
        // 지정한 위치로 이동
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(myposition, 18));
        googleMap.addMarker(new MarkerOptions().position(myposition).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).title("My Position"));

    }

    public void showCoffeeShops(final GoogleMap googleMap) {

        MarkerOptions mOptions10 = new MarkerOptions();
        mOptions10.title("O_der_Cafe");
        mOptions10.position(new LatLng(37.54049, 126.83831));
        googleMap.addMarker(mOptions10);

    }
}
