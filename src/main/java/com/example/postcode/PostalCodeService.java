package com.example.postcode;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
public class PostalCodeService {

    private Map<String, PostalCode> postcodeMap;

    public PostalCodeService() {
        postcodeMap = new HashMap<>();
    }

    @PostConstruct
    public void loadPostalCodesFromCsv() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream("/ukpostcodes.csv")))) {
            
            String line;
            boolean header = true;

            while ((line = br.readLine()) != null) {
                // Skip header line
                if (header) {
                    header = false;
                    continue;
                }

                String[] parts = line.split(",");
                
                if (parts.length == 4) {
                    String postcode = parts[1].trim();  // Postcode in second column
                    double latitude = Double.parseDouble(parts[2].trim());
                    double longitude = Double.parseDouble(parts[3].trim());
                    
                    postcodeMap.put(postcode, new PostalCode(postcode, latitude, longitude));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load postal codes from CSV", e);
        }
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
