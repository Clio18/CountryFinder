package com.obolonyk.countryfinder.web;

import com.obolonyk.countryfinder.dto.CountryDetailsDTO;
import com.obolonyk.countryfinder.service.WishCountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/countries")
@RequiredArgsConstructor
public class CountryController {

    private final WishCountryService wishCountryService;

    @PostMapping("/{cca2}")
    public Mono<CountryDetailsDTO> addCountry(@PathVariable String cca2) {
        return wishCountryService.addCountry(cca2);
    }

    @DeleteMapping("/{cca2}")
    public void deleteCountry(@PathVariable String cca2) {
        wishCountryService.deleteCountry(cca2);
    }

    @GetMapping("/{cca2}")
    public Mono<CountryDetailsDTO> getCountryDetails(@PathVariable String cca2) {
        return wishCountryService.getCountryDetails(cca2);
    }

    @PutMapping("/{cca2}/mark-visited")
    public Mono<CountryDetailsDTO> markCountryAsVisited(@PathVariable String cca2) {
        return wishCountryService.markCountryAsVisited(cca2);
    }
}
