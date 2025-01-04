package com.example.postcode;

public class PostalCode {

    private String postcode;
    private double latitude;
    private double longitude;

    public PostalCode(String postcode, double latitude, double longitude) {
        this.postcode = postcode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getPostcode() {
        return postcode;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
