package com.obolonyk.countryfinder.dto;

import com.obolonyk.countryfinder.dto.external.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CountryDetailsDTO {
    private String cca2;
    private Name name;
    private List<String> capital;
    private String region;
    private String subregion;
    private Map<String, Currency> currencies;
    private Map<String, String> languages;
    private List<String> borders;
    private boolean visited;
}
