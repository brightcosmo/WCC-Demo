package com.example.postcode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/distance")
public class PostalCodeController {

    @Autowired
    private PostalCodeService postalCodeService;

    @GetMapping
    public Map<String, Object> getDistance(@RequestParam String postcode1, @RequestParam String postcode2) {
        PostalCode location1 = postalCodeService.lookupPostalCode(postcode1);
        PostalCode location2 = postalCodeService.lookupPostalCode(postcode2);

        Map<String, Object> response = new HashMap<>();
        if (location1 == null || location2 == null) {
            response.put("error", "Postcodes not found");
            return response;
        }

        double distance = postalCodeService.calculateDistance(location1.getLatitude(), location1.getLongitude(),
                location2.getLatitude(), location2.getLongitude());

        response.put("postcode1", postcode1);
        response.put("latitude1", location1.getLatitude());
        response.put("longitude1", location1.getLongitude());
        response.put("postcode2", postcode2);
        response.put("latitude2", location2.getLatitude());
        response.put("longitude2", location2.getLongitude());
        response.put("distance", distance);
        response.put("unit", "km");

        return response;
    }
}
