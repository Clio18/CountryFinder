package com.obolonyk.countryfinder.service.impl;

import com.obolonyk.countryfinder.dto.external.ExternalCountryDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class RestCountriesExternalCountryServiceITest {
    @Autowired
    RestCountriesExternalCountryService restCountriesExternalCountryService;

    @Test
    public void testGetCountryDetails_ShouldSucceed() {
        Mono<ExternalCountryDTO> ua = restCountriesExternalCountryService.getById("ua");
        assertNotNull(ua);
    }
}