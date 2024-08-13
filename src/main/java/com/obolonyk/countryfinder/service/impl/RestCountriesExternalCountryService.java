package com.obolonyk.countryfinder.service.impl;

import com.obolonyk.countryfinder.dto.external.ExternalCountryDTO;
import com.obolonyk.countryfinder.exception.CountryNotFoundException;
import com.obolonyk.countryfinder.service.ExternalCountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RestCountriesExternalCountryService implements ExternalCountryService {

    private final WebClient webClient;

    @Value("${restcountries.url}")
    private String restCountriesUrl;
    @Override
    public Mono<ExternalCountryDTO> getById(String cca2) {
        return webClient.get()
                .uri(restCountriesUrl + "/alpha/{cca2}", cca2)
                .retrieve()
                .bodyToFlux(ExternalCountryDTO.class)
                .next()
                .switchIfEmpty(Mono.error(new CountryNotFoundException("Country was not found for cca2 - " + cca2)));
    }
}