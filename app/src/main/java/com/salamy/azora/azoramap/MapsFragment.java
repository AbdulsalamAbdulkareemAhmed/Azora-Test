package com.salamy.azora.azoramap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment implements
        GoogleMap.OnMarkerClickListener {

    private final LatLng location1 = new LatLng(15.325491, 44.180804);
    private final LatLng location2 = new LatLng(15.311030, 44.192887);
    private final LatLng location3 = new LatLng(15.307509, 44.185849);
    OnMapReadyCallback callback = new OnMapReadyCallback() {
        
        @Override
        public void onMapReady(GoogleMap googleMap) {
            googleMap.setMinZoomPreference(6.0f);
            googleMap.setMaxZoomPreference(14.0f);
            Marker marker1 = googleMap.addMarker(new MarkerOptions()
                    .position(location1)
                    .title("Title : ").snippet("Description").icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));
            marker1.showInfoWindow();
            marker1.setTag(0);

            Marker marker2 = googleMap.addMarker(new MarkerOptions()
                    .position(location2)
                    .title("Title ").snippet("Description").icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));
            marker2.showInfoWindow();
            marker2.setTag(0);

            Marker marker3 = googleMap.addMarker(new MarkerOptions()
                    .position(location3)
                    .title("Title : ").snippet(" Description ").icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));
            marker3.showInfoWindow();
            marker3.setTag(0);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location1, 15));
            googleMap.setOnMarkerClickListener(MapsFragment.this);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        Integer clickCount = (Integer) marker.getTag();

        if (clickCount != null) {
            marker.hideInfoWindow();
        }

        return false;
    }
}