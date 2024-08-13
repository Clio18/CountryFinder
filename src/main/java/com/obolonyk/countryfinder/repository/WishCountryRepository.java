package com.obolonyk.countryfinder.repository;

import com.obolonyk.countryfinder.entity.CountryDetails;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public interface WishCountryRepository extends ReactiveMongoRepository<CountryDetails, String> {
    Mono<Void> deleteByCca2(String cca2);

    Mono<CountryDetails> findByCca2(String cca2);
}
