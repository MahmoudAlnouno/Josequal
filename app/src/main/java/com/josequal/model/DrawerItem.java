package com.josequal.model;

import com.google.android.gms.maps.model.LatLng;
import com.josequal.R;

import java.util.ArrayList;

public class DrawerItem {
    private String title;
    private int imageResourceId;
    private String details; // New field for additional details
    private LatLng location;

    public DrawerItem(String title, int imageResourceId, LatLng location, String details) {
        this.title = title;
        this.imageResourceId = imageResourceId;
        this.location = location;
        this.details = details;
    }

    public static ArrayList<DrawerItem> fillDumpData() {

        ArrayList<DrawerItem> drawerItems = new ArrayList<>();

        drawerItems.add(new DrawerItem("Abdali mall", R.drawable.ic_mall,
                new LatLng(31.96326, 35.9089), "Immersed in the heart of Amman in a spectacularly" +
                " 227,000 square meters of modern and refined architectural space, Abdali Mall is the Kingdom’s " +
                "upscale retail and entertainment hub and the first of its kind to utilize eco-friendly technologies," +
                " designed with open-air features, allowing for natural air-flow circulation and the warmth of sunrays")); // Example location 1 (New York)
        drawerItems.add(new DrawerItem("Istiklal mall", R.drawable.ic_istklal, new LatLng(31.97919, 35.92197)
                , "Istiklal Mall has everything you need under one wing"));
        drawerItems.add(new DrawerItem("mukhtar mall", R.drawable.ic_mukhtar, new LatLng(31.9875, 35.89612)
                , "Mukhtar Mall was established in September 2006 and is located at Queen Rania St. in Amman – Sport City Circle, which is a strategic location in the middle of the capital city of Amman"));
        return drawerItems;
    }

    public String getTitle() {
        return title;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public LatLng getLocation() {
        return location;
    }

    public String getDetails() {
        return details;
    }
}
