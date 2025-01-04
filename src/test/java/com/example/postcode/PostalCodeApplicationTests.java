package com.example.postcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

class PostalCodeServiceTest {

    private PostalCodeService postalCodeService;

    @BeforeEach
    void setUp() {
        postalCodeService = new PostalCodeService();
        postalCodeService.loadPostalCodesFromCsv();  // Load the postcodes from the CSV file before each test
    }

    @Test
    void testLookupPostalCode_validPostcode() {
        // Test with a known postcode
		PostalCode postcode = postalCodeService.lookupPostalCode("AB11 6UL");
		assertNotNull(postcode);
		assertEquals("AB11 6UL", postcode.getPostcode());
		assertEquals(57.137547, postcode.getLatitude(), 0.0001);
		assertEquals(-2.112233, postcode.getLongitude(), 0.0001);
    }

    @Test
    void testLookupPostalCode_invalidPostcode() {
        // Test with an invalid postcode
        PostalCode postcode = postalCodeService.lookupPostalCode("INVALID 1XX");
        assertNull(postcode);  // It should return null for an invalid postcode
    }

    @Test
    void testCalculateDistance() {
        // Test with AB11 6UL and GL19 4JP
		double lat1 = 57.137547;
		double lon1 = -2.112233; // AB11 6UL
		double lat2 = 51.93175;
		double lon2 = -2.273904; // GL19 4JP
	
		double distance = postalCodeService.calculateDistance(lat1, lon1, lat2, lon2);
		assertEquals(578.95, distance, 0.1);
    }

    @Test
    void testCsvLoading() {
        PostalCode postcode = postalCodeService.lookupPostalCode("AB11 6UL");
        assertNotNull(postcode);
        assertEquals("AB11 6UL", postcode.getPostcode());
		assertEquals(57.137547, postcode.getLatitude());
		assertEquals(-2.112233, postcode.getLongitude());
    }

    @Test
    void testMockedCsvLoading() {
        PostalCodeService mockedService = Mockito.mock(PostalCodeService.class);
        PostalCode mockPostcode = new PostalCode("AB11 6UL", 57.1404, -2.1041);
        Mockito.when(mockedService.lookupPostalCode("AB11 6UL")).thenReturn(mockPostcode);

        PostalCode postcode = mockedService.lookupPostalCode("AB11 6UL");
        assertNotNull(postcode);
        assertEquals("AB11 6UL", postcode.getPostcode());
    }
}
