package com.anand.billing.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

public class ErrorDto {

    public static final String STATUS = "status";
    public static final String ERROR = "error";

    public ErrorDto(final HttpStatus httpStatus, final String error) {
        this.httpStatus = httpStatus;
        this.error = error;
    }

    @JsonProperty(STATUS)
    HttpStatus httpStatus;

    @JsonProperty(ERROR)
    String error;

}
