package com.obolonyk.countryfinder.service;

import com.obolonyk.countryfinder.dto.CountryDetailsDTO;
import reactor.core.publisher.Mono;

public interface WishCountryService {
    Mono<CountryDetailsDTO> addCountry(String cca2);

    void deleteCountry(String cca2);

    Mono<CountryDetailsDTO> getCountryDetails(String cca2);

    Mono<CountryDetailsDTO> markCountryAsVisited(String cca2);
}
