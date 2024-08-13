package com.obolonyk.countryfinder.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response<T> {
    public enum Type {
        SUCCESS, ERROR
    }

    @Builder.Default
    private Type type = Type.SUCCESS;
    private String description;
    @Builder.Default
    private int status = 200;
    private T data;
}