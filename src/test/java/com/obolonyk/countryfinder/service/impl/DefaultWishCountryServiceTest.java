package com.obolonyk.countryfinder.service.impl;

import com.obolonyk.countryfinder.dto.CountryDetailsDTO;
import com.obolonyk.countryfinder.dto.Name;
import com.obolonyk.countryfinder.dto.external.Currency;
import com.obolonyk.countryfinder.dto.external.ExternalCountryDTO;
import com.obolonyk.countryfinder.entity.CountryDetails;
import com.obolonyk.countryfinder.exception.CountryNotFoundException;
import com.obolonyk.countryfinder.mapper.CountryDetailsMapper;
import com.obolonyk.countryfinder.repository.WishCountryRepository;
import com.obolonyk.countryfinder.service.ExternalCountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultWishCountryServiceTest {
    @Mock
    private WishCountryRepository wishCountryRepository;
    @Mock
    private ExternalCountryService externalCountryService;
    @Mock
    private CountryDetailsMapper countryDetailsMapper;

    @InjectMocks
    private DefaultWishCountryService defaultWishCountryService;

    private CountryDetailsDTO dto;
    private ExternalCountryDTO external;
    private CountryDetails entity;


    @BeforeEach
    void init() {
        dto = CountryDetailsDTO.builder()
                .cca2("UA")
                .name(Name.builder().official("Ukraine").common("Ukraine").build())
                .capital(List.of("Kyiv"))
                .region("Europe")
                .subregion("Eastern Europe")
                .currencies(Map.of("UAH", Currency.builder().name("Ukrainian hryvnia").symbol("₴").build()))
                .borders(List.of("Poland", "Slovakia", "Hungary", "Romania", "Moldova", "Russia", "Belarus"))
                .languages(Map.of("ukr","Ukrainian"))
                .build();
        external = ExternalCountryDTO.builder()
                .cca2("UA")
                .name(Name.builder().official("Ukraine").common("Ukraine").build())
                .capital(List.of("Kyiv"))
                .region("Europe")
                .subregion("Eastern Europe")
                .currencies(Map.of("UAH", Currency.builder().name("Ukrainian hryvnia").symbol("₴").build()))
                .borders(List.of("Poland", "Slovakia", "Hungary", "Romania", "Moldova", "Russia", "Belarus"))
                .languages(Map.of("ukr","Ukrainian"))
                .build();
        entity = CountryDetails.builder()
                .cca2("UA")
                .name(Name.builder().official("Ukraine").common("Ukraine").build())
                .capital(List.of("Kyiv"))
                .region("Europe")
                .subregion("Eastern Europe")
                .currencies(Map.of("UAH", Currency.builder().name("Ukrainian hryvnia").symbol("₴").build()))
                .borders(List.of("Poland", "Slovakia", "Hungary", "Romania", "Moldova", "Russia", "Belarus"))
                .languages(Map.of("ukr","Ukrainian"))
                .build();
    }

    @Test
    @DisplayName("Test AddCountry When Valid Data Then Check Result And Services Calling")
    void testAddCountryWhenValidData_ThenCheckResultAndServicesCalling() {
        dto.setVisited(false);
        when(countryDetailsMapper.toCountryDetails(external)).thenReturn(entity);
        when(countryDetailsMapper.toCountryDetailsDto(entity)).thenReturn(dto);
        when(wishCountryRepository.save(entity)).thenReturn(Mono.just(entity));
        when(externalCountryService.getById("UA")).thenReturn(Mono.just(external));

        Mono<CountryDetailsDTO> ua = defaultWishCountryService.addCountry("UA");
        ua.subscribe(countryDetailsDTO -> {
            assertEquals(dto, countryDetailsDTO);
        });

        verify(externalCountryService).getById("UA");
        verify(countryDetailsMapper).toCountryDetails(external);
        verify(countryDetailsMapper).toCountryDetailsDto(entity);
        verify(wishCountryRepository).save(entity);
    }

    @Test
    @DisplayName("Test AddCountry When Bad Scenario Then Exception Throwing")
    void testAddCountryWhenBadScenario_ThenExceptionThrowing() {
        when(externalCountryService.getById("UA")).thenReturn(Mono.empty());
        CountryNotFoundException exception = assertThrows(CountryNotFoundException.class, () -> defaultWishCountryService.addCountry("UA").block());

        assertEquals("There is no country with code: Country not found for cca2 - UA", exception.getMessage());
    }

    @Test
    @DisplayName("Test DeleteCountry Then Success")
    void testDeleteCountry_ThenSuccess() {
        when(wishCountryRepository.deleteByCca2("UA")).thenReturn(Mono.empty());
        defaultWishCountryService.deleteCountry("UA");

        verify(wishCountryRepository).deleteByCca2("UA");
    }

    @Test
    @DisplayName("Test GetCountryDetails When Valid Data Than Success")
    void testGetCountryDetailsWhenValidData_ThanSuccess() {
        when(countryDetailsMapper.toCountryDetailsDto(entity)).thenReturn(dto);
        when(wishCountryRepository.findByCca2("UA")).thenReturn(Mono.just(entity));

        defaultWishCountryService.getCountryDetails("UA").subscribe(countryDetailsDTO -> {
            assertEquals(dto, countryDetailsDTO);
        });

        verify(countryDetailsMapper).toCountryDetailsDto(entity);
        verify(wishCountryRepository).findByCca2("UA");
    }

    @Test
    @DisplayName("Test AddCountry When Country Not Found  Then Exception Throwing")
    void testAddCountryWhenCountryNotFound_ThenExceptionThrowing() {
        when(wishCountryRepository.findByCca2("UA")).thenReturn(Mono.empty());
        CountryNotFoundException exception = assertThrows(CountryNotFoundException.class, () -> defaultWishCountryService.getCountryDetails("UA").block());
        assertEquals("There is no country with code: Country not found for cca2 - UA", exception.getMessage());
    }

    @Test
    @DisplayName("Test MarkCountryAsVisited When Valid Data Than Success")
    void testMarkCountryAsVisitedWhenValidData_ThanSuccess() {
        when(wishCountryRepository.findByCca2("UA")).thenReturn(Mono.just(entity));
        when(wishCountryRepository.save(entity)).thenReturn(Mono.just(entity));

        defaultWishCountryService.markCountryAsVisited("UA").subscribe(countryDetailsDTO -> {
            assertEquals(dto, countryDetailsDTO);
            assertTrue(countryDetailsDTO.isVisited());
        });

        verify(countryDetailsMapper).toCountryDetailsDto(entity);
        verify(wishCountryRepository).save(entity);
        verify(wishCountryRepository).findByCca2("UA");
    }

    @Test
    @DisplayName("Test MarkCountryAsVisitedWhen Country Not Found  Then Exception Throwing")
    void testMarkCountryAsVisitedWhenCountryNotFound_ThenExceptionThrowing() {
        when(wishCountryRepository.findByCca2("UA")).thenReturn(Mono.empty());
        CountryNotFoundException exception = assertThrows(CountryNotFoundException.class, () -> defaultWishCountryService.markCountryAsVisited("UA").block());
        assertEquals("There is no country with code: Country not found for cca2 - UA", exception.getMessage());
    }

}