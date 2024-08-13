package com.obolonyk.countryfinder.exception;

public class CountryNotFoundException extends RuntimeException {

    private static final String NO_COUNTRY_BY_CODE_MESSAGE = "There is no country with code: %s";

    public CountryNotFoundException(String code) {
        super(String.format(NO_COUNTRY_BY_CODE_MESSAGE, code));
    }
}
