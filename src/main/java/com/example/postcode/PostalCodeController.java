package com.example.postcode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/postcodes")
public class PostalCodeController {

    @Autowired
    private PostalCodeService postalCodeService;

    @GetMapping("/distance")
    public Map<String, Object> getDistance(@RequestParam String from, @RequestParam String to) {
        PostalCode location1 = postalCodeService.lookupPostalCode(from);
        PostalCode location2 = postalCodeService.lookupPostalCode(to);

        Map<String, Object> response = new HashMap<>();
        if (location1 == null || location2 == null) {
            response.put("error", "Postcodes not found");
            return response;
        }

        double distance = postalCodeService.calculateDistance(
            location1.getLatitude(), location1.getLongitude(),
            location2.getLatitude(), location2.getLongitude()
        );

        response.put("postcode1", from);
        response.put("latitude1", location1.getLatitude());
        response.put("longitude1", location1.getLongitude());
        response.put("postcode2", to);
        response.put("latitude2", location2.getLatitude());
        response.put("longitude2", location2.getLongitude());
        response.put("distance", distance);
        response.put("unit", "km");

        return response;
    }
}
