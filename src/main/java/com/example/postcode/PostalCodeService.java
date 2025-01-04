package com.example.postcode;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PostalCodeService {

    private Map<String, PostalCode> postcodeMap;

    public PostalCodeService() {
        postcodeMap = new HashMap<>();
        // Add postcode data manually or load from CSV or database here
        // Example:
        postcodeMap.put("SW1A1AA", new PostalCode("SW1A1AA", 51.501009, -0.141588));
        postcodeMap.put("EH1 1BB", new PostalCode("EH1 1BB", 55.953251, -3.188267));
    }

    public PostalCode lookupPostalCode(String postcode) {
        return postcodeMap.get(postcode);
    }

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final double EARTH_RADIUS = 6371; // Earth radius in kilometers
        double lon1Radians = Math.toRadians(lon1);
        double lon2Radians = Math.toRadians(lon2);
        double lat1Radians = Math.toRadians(lat1);
        double lat2Radians = Math.toRadians(lat2);

        // Haversine formula
        double a = haversine(lat1Radians, lat2Radians) +
                   Math.cos(lat1Radians) * Math.cos(lat2Radians) * haversine(lon1Radians, lon2Radians);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;  // Return distance in kilometers
    }

    private double haversine(double deg1, double deg2) {
        return Math.pow(Math.sin((deg1 - deg2) / 2.0), 2);
    }
}
