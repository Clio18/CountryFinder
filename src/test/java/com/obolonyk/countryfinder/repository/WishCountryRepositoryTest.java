package com.obolonyk.countryfinder.repository;

import com.obolonyk.countryfinder.AbstractBaseITest;
import com.obolonyk.countryfinder.dto.Name;
import com.obolonyk.countryfinder.entity.CountryDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
class WishCountryRepositoryTest extends AbstractBaseITest {
    @Autowired
    private WishCountryRepository wishCountryRepository;

    @BeforeEach
    void init(){
        wishCountryRepository.deleteAll().block();
    }

    @Test
    @DisplayName("Test FindByCca2 When Country Exists Then Find Successfully")
    void testFindByCca2_WhenCountryExists_ThenFindSuccessfully(){
        CountryDetails country = CountryDetails.builder()
                .cca2("UA")
                .name(new Name("Ukraine", "Ukrainian"))
                .build();

        wishCountryRepository.save(country).block();

        CountryDetails foundCountry = wishCountryRepository.findById("UA").block();

        assertNotNull(foundCountry);
        assertEquals("UA", foundCountry.getCca2());
    }

    @Test
    @DisplayName("Test FindByCca2 When Country Does Not Exist Then Return Null")
    void testFindByCca2_WhenCountryDoesNotExist_ThenReturnNull(){
        CountryDetails foundCountry = wishCountryRepository.findById("UA").block();
        assertNull(foundCountry);
    }


}