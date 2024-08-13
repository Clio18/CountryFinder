package com.obolonyk.countryfinder.mapper;

import com.obolonyk.countryfinder.dto.CountryDetailsDTO;
import com.obolonyk.countryfinder.dto.Name;
import com.obolonyk.countryfinder.dto.external.Currency;
import com.obolonyk.countryfinder.dto.external.ExternalCountryDTO;
import com.obolonyk.countryfinder.entity.CountryDetails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CountryDetailsMapperTest {
    @Autowired
    private CountryDetailsMapper countryDetailsMapper;

    @Test
    @DisplayName("Test ToCountryDetailsEntity Mapper's Method")
    void testToCountryDetailsEntity() {
        ExternalCountryDTO dto = ExternalCountryDTO.builder()
                .cca2("UA")
                .name(Name.builder().official("Ukraine").common("Ukraine").build())
                .capital(List.of("Kyiv"))
                .region("Europe")
                .subregion("Eastern Europe")
                .currencies(Map.of("UAH", Currency.builder().name("Ukrainian hryvnia").symbol("₴").build()))
                .borders(List.of("Poland", "Slovakia", "Hungary", "Romania", "Moldova", "Russia", "Belarus"))
                .languages(Map.of("ukr","Ukrainian"))
                .build();
        CountryDetails entity = countryDetailsMapper.toCountryDetails(dto);
        assertNotNull(entity);
        assertEquals(dto.getCca2(), entity.getCca2());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getCapital(), entity.getCapital());
        assertEquals(dto.getRegion(), entity.getRegion());
        assertEquals(dto.getSubregion(), entity.getSubregion());
        assertEquals(dto.getCurrencies(), entity.getCurrencies());
        assertEquals(dto.getLanguages(), entity.getLanguages());
        assertEquals(dto.getBorders(), entity.getBorders());
    }

    @Test
    @DisplayName("Test ToCountryDetailsDto Mapper's Method")
    void toCountryDetailsEntity() {
        CountryDetails countryDetails = CountryDetails.builder()
                .cca2("UA")
                .name(Name.builder().official("Ukraine").common("Ukraine").build())
                .capital(List.of("Kyiv"))
                .region("Europe")
                .subregion("Eastern Europe")
                .currencies(Map.of("UAH", Currency.builder().name("Ukrainian hryvnia").symbol("₴").build()))
                .borders(List.of("Poland", "Slovakia", "Hungary", "Romania", "Moldova", "Russia", "Belarus"))
                .languages(Map.of("ukr","Ukrainian"))
                .build();
        CountryDetailsDTO dto = countryDetailsMapper.toCountryDetailsDto(countryDetails);
        assertNotNull(dto);

        assertEquals(countryDetails.getCca2(), dto.getCca2());
        assertEquals(countryDetails.getName(), dto.getName());
        assertEquals(countryDetails.getCapital(), dto.getCapital());
        assertEquals(countryDetails.getRegion(), dto.getRegion());
        assertEquals(countryDetails.getSubregion(), dto.getSubregion());
        assertEquals(countryDetails.getCurrencies(), dto.getCurrencies());
        assertEquals(countryDetails.getLanguages(), dto.getLanguages());
        assertEquals(countryDetails.getBorders(), dto.getBorders());
    }
}