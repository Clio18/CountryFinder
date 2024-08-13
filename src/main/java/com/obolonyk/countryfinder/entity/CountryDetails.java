package com.obolonyk.countryfinder.entity;

import com.obolonyk.countryfinder.dto.external.Currency;
import com.obolonyk.countryfinder.dto.Name;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "wishlist")
public class CountryDetails {
    @Id
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
