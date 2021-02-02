package com.ayoholdings;


import com.ayoholdings.model.ConversionDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConversionApiApplicationIntegrationTests {
    public static final String CATEGORY_API = "/api/categories";
    public static final String CONVERSION_API = "/api/convert/Mass/Kilogram/Pound/10";
    @LocalServerPort
    private int port;

    TestRestTemplate testRestTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    @Test
    void conversionApis() {
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<ConversionDTO> categoryResponse = this.testRestTemplate.exchange(formatApi(CATEGORY_API), HttpMethod.GET, httpEntity, ConversionDTO.class);
        assert categoryResponse != null;
        assertTrue(categoryResponse.getStatusCode().is2xxSuccessful());
        final ConversionDTO conversionDTO = categoryResponse.getBody();
        assert conversionDTO != null;
        assertTrue(conversionDTO.getCategories().keySet().containsAll(Arrays.asList("Temperature", "Mass", "Length")));


        ResponseEntity<Double> conversionResponse = this.testRestTemplate.exchange(formatApi(CONVERSION_API), HttpMethod.GET, httpEntity, Double.class);
        assert conversionResponse != null;
        assertTrue(conversionResponse.getStatusCode().is2xxSuccessful());
        assert conversionResponse.getBody() != null;
        assertEquals(22.0462, conversionResponse.getBody(), 0.1);

        ResponseEntity<Object> errorResponse1 = this.testRestTemplate.exchange(formatApi(CONVERSION_API.replace("Mass", "Dummy")), HttpMethod.GET, httpEntity, Object.class);
        assert errorResponse1 != null;
        assertTrue(errorResponse1.getStatusCode().is4xxClientError());
        assert errorResponse1.getBody() != null;

        ResponseEntity<Object> errorResponse2 = this.testRestTemplate.exchange(formatApi(CONVERSION_API.replace("Kilogram", "Dummy")), HttpMethod.GET, httpEntity, Object.class);
        assert errorResponse2 != null;
        assertTrue(errorResponse2.getStatusCode().is4xxClientError());
        assert errorResponse2.getBody() != null;
    }

    private String formatApi(String endpoint) {
        return String.format("http://localhost:%s/%s", this.port, endpoint);
    }

}
