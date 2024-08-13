package com.obolonyk.countryfinder.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.obolonyk.countryfinder.dto.Name;
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
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class ExternalCountryDTO {
    private Name name;
    private String cca2;
    private boolean unMember;
    private Map<String, Currency> currencies;
    private List<String> capital;
    private String region;
    private String subregion;
    private Map<String, String> languages;
    private List<String> borders;
}
