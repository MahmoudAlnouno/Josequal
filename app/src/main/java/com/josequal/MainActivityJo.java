package com.josequal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.kml.KmlLayer;
import com.josequal.Utils.Utils;
import com.josequal.adapter.DrawerAdapter;
import com.josequal.model.DrawerItem;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivityJo extends AppCompatActivity implements DrawerAdapter.OnItemClickListener, OnMapReadyCallback {


    //    private FusedLocationProviderClient fusedLocationClient;
    ArrayList<DrawerItem> drawerItems;
    KmlLayer kmlLayer;
    private GoogleMap mMap;
    private RecyclerView recyclerView;
    private DrawerAdapter drawerAdapter;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xxx);
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Initialize the GoogleMap object
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
//        if (mapFragment != null) {
//            mapFragment.getMapAsync(map -> {
//                googleMap = map;
//                // Set default map location
//                LatLng defaultLocation = new LatLng(0, 0);
//                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10));
//            });
//        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        drawerItems = DrawerItem.fillDumpData();
        drawerAdapter = new DrawerAdapter(drawerItems, this);
        recyclerView.setAdapter(drawerAdapter);

    }

    private void loadDataToMap() {
        for (DrawerItem item : drawerItems) {
            LatLng locationLatLng = item.getLocation();
            Marker marker = mMap.addMarker(new MarkerOptions().position(locationLatLng).title(item.getTitle()));
            marker.setTag(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onItemClick(DrawerItem item) {
        if (mMap != null) {
            if (kmlLayer != null) {
                kmlLayer.removeLayerFromMap();
                kmlLayer = null;
            }
            LatLng location = item.getLocation();

            // Create custom marker layout
            View markerView = LayoutInflater.from(this).inflate(R.layout.custom_marker_layout, null);
            ImageView markerImageView = markerView.findViewById(R.id.markerImageView);

            // Set the marker's image based on the selected item
            markerImageView.setImageResource(item.getImageResourceId());

            // Add the custom marker to the map at the selected location
            mMap.addMarker(new MarkerOptions()
                    .position(location)
                    .icon(BitmapDescriptorFactory.fromBitmap(Utils.getBitmapFromView(markerView)))
                    .title(item.getTitle()));

            // Move the camera to the selected location
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 12f));
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_abdail_to_mukhtar:
                loadKMLFromRawResource(R.raw.abdali_to_mukhtar);
                return true;
            case R.id.menu_istklal_to_abdali:
                loadKMLFromRawResource(R.raw.istklal_to_abdali);
                return true;
            case R.id.menu_mukhtar_to_istklal:
                loadKMLFromRawResource(R.raw.mukhtar_to_istklal);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadKMLFromRawResource(int resourceId) {
        mMap.clear();
        // Clear existing KML layer if it exists
        if (kmlLayer != null) {
            kmlLayer.removeLayerFromMap();
            kmlLayer = null;
        }

        try {
            InputStream inputStream = getResources().openRawResource(resourceId);
            kmlLayer = new KmlLayer(mMap, inputStream, getApplicationContext());
            kmlLayer.addLayerToMap();

        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message to the user)
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        loadDataToMap();
        mMap.setOnMarkerClickListener(marker -> {
            if (kmlLayer != null) {
                kmlLayer.removeLayerFromMap();
                kmlLayer = null;
            }
            // Handle marker click here
            DrawerItem item = (DrawerItem) marker.getTag();
            if (item != null) {
                // Show a dialog containing item details, for example
                showDialogWithItemDetails(item);
            }
            return true; // Return true to consume the event (prevent default behavior)
        });
    }

    private void showDialogWithItemDetails(DrawerItem item) {
        ItemDetailsDialogFragment dialogFragment = new ItemDetailsDialogFragment(item);
        dialogFragment.show(getSupportFragmentManager(), "ItemDetailsDialogFragment");
    }
}