package com.obolonyk.countryfinder.service.impl;

import com.obolonyk.countryfinder.dto.CountryDetailsDTO;
import com.obolonyk.countryfinder.exception.CountryNotFoundException;
import com.obolonyk.countryfinder.mapper.CountryDetailsMapper;
import com.obolonyk.countryfinder.repository.WishCountryRepository;
import com.obolonyk.countryfinder.service.ExternalCountryService;
import com.obolonyk.countryfinder.service.WishCountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultWishCountryService implements WishCountryService {
    private final WishCountryRepository wishCountryRepository;
    private final ExternalCountryService externalCountryService;
    private final CountryDetailsMapper countryDetailsMapper;

    @Override
    public Mono<CountryDetailsDTO> addCountry(String cca2) {
        return externalCountryService.getById(cca2)
                .map(countryDetailsMapper::toCountryDetails)
                .flatMap(wishCountryRepository::save)
                .map(countryDetailsMapper::toCountryDetailsDto)
                .switchIfEmpty(Mono.error(new CountryNotFoundException("Country not found for cca2 - " + cca2)));
    }

    @Override
    public void deleteCountry(String cca2) {
        wishCountryRepository.deleteByCca2(cca2).subscribe();
    }

    @Override
    public Mono<CountryDetailsDTO> getCountryDetails(String cca2) {
        return wishCountryRepository.findByCca2(cca2)
                .map(countryDetails -> {
                    CountryDetailsDTO detailsDto = countryDetailsMapper.toCountryDetailsDto(countryDetails);
                    detailsDto.setVisited(countryDetails.isVisited());
                    return detailsDto;
                })
                .switchIfEmpty(Mono.error(new CountryNotFoundException("Country not found for cca2 - " + cca2)));
    }

    @Override
    public Mono<CountryDetailsDTO> markCountryAsVisited(String cca2) {
        return wishCountryRepository.findByCca2(cca2)
                .map(countryDetails -> {

                    countryDetails.setVisited(true);
                            return countryDetails;
                        })
                .flatMap(wishCountryRepository::save)
                .map(countryDetailsMapper::toCountryDetailsDto)
                .switchIfEmpty(Mono.error(new CountryNotFoundException("Country not found for cca2 - " + cca2)));
    }
}
