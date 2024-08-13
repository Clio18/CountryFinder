package com.obolonyk.countryfinder.web;

import com.obolonyk.countryfinder.AbstractBaseITest;
import com.obolonyk.countryfinder.dto.CountryDetailsDTO;
import com.obolonyk.countryfinder.dto.Name;
import com.obolonyk.countryfinder.dto.external.Currency;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.containsInAnyOrder;


class CountryControllerITest extends AbstractBaseITest {

    private final static String API_PREFIX = "/api/v1/countries";

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    @DisplayName("Test AddCountry - Should Succeed")
    void testAddCountry_ShouldSucceed() {
        webTestClient.post()
                .uri(API_PREFIX + "/ua")
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.cca2").isEqualTo("UA")
                .jsonPath("$.name.common").isEqualTo("Ukraine")
                .jsonPath("$.name.official").isEqualTo("Ukraine")
                .jsonPath("$.region").isEqualTo("Europe")
                .jsonPath("$.subregion").isEqualTo("Eastern Europe")
                .jsonPath("$.currencies.UAH.name").isEqualTo("Ukrainian hryvnia")
                .jsonPath("$.borders[*]").value(containsInAnyOrder("BLR", "HUN", "MDA", "POL", "ROU", "RUS", "SVK"))
                .jsonPath("$.capital[*]").value(containsInAnyOrder("Kyiv"))
                .jsonPath("$.languages.ukr").isEqualTo("Ukrainian");
    }

    @Test
    @Disabled
    @DisplayName("Test MarkCountryAsVisited - Should Succeed")
    void testMarkCountryAsVisited_ShouldSucceed() {
        CountryDetailsDTO expectedDto = CountryDetailsDTO.builder()
                .cca2("UA")
                .name(Name.builder().official("Ukraine").common("Ukraine").build())
                .capital(List.of("Kyiv"))
                .region("Europe")
                .subregion("Eastern Europe")
                .currencies(Map.of("UAH", Currency.builder().name("Ukrainian hryvnia").symbol("₴").build()))
                .borders(List.of("Poland", "Slovakia", "Hungary", "Romania", "Moldova", "Russia", "Belarus"))
                .languages(Map.of("ukr","Ukrainian"))
                .visited(true)
                .build();

        webTestClient.get()
                .uri(API_PREFIX + "/ua/mark-visited")
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.visited").isEqualTo(true);
    }

    @Test
    @DisplayName("Test DeleteCountry - Should Succeed")
    void testDeleteCountry_ShouldSucceed() {
        webTestClient.delete()
                .uri(API_PREFIX + "/ua")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Disabled
    @DisplayName("Test GetCountryDetails - Should Succeed")
    void testGetCountryDetails_ShouldSucceed() {
        mongoTemplate.createCollection("wishlist").insertOne(
                new org.bson.Document()
                        .append("cca2", "ua")
                        .append("name", new org.bson.Document()
                                .append("common", "Ukraine")
                                .append("official", "Ukraine"))
                        .append("capital", List.of("Kyiv"))
                        .append("region", "Europe")
                        .append("subregion", "Eastern Europe")
                        .append("currencies", Map.of("UAH", new org.bson.Document()
                                .append("name", "Ukrainian hryvnia")))
                        .append("languages", Map.of("ukr", "Ukrainian"))
                        .append("borders", List.of("BLR", "HUN", "MDA", "POL", "ROU", "RUS", "SVK"))
                        .append("visited", false)
        );

        System.out.println(mongoTemplate.getCollection("wishlist").find().first().toJson());

        webTestClient.get()
                .uri(API_PREFIX + "/ua")
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.cca2").isEqualTo("ua");
    }

}