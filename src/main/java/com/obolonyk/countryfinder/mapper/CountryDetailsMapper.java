package com.obolonyk.countryfinder.mapper;

import com.obolonyk.countryfinder.dto.CountryDetailsDTO;
import com.obolonyk.countryfinder.dto.external.ExternalCountryDTO;
import com.obolonyk.countryfinder.entity.CountryDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryDetailsMapper {

    CountryDetailsDTO toCountryDetailsDto(CountryDetails entity);

    CountryDetails toCountryDetails(ExternalCountryDTO dto);
}
