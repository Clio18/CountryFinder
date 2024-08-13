package com.obolonyk.countryfinder.service;

import com.obolonyk.countryfinder.dto.external.ExternalCountryDTO;
import reactor.core.publisher.Mono;

public interface ExternalCountryService {
    Mono<ExternalCountryDTO> getById(String id);
}
