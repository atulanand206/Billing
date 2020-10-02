package com.anand.billing.controller;

import com.anand.billing.model.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

  private static final String LINE_SPLIT_REGEX = "\r\n|\r|\n";

  @ExceptionHandler({Exception.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ErrorDto handleException(Exception exception) {
    String errorDetail = exception.getMessage();
    String modifiedMessage = errorDetail.split(LINE_SPLIT_REGEX, 2)[0];
    return new ErrorDto(HttpStatus.BAD_REQUEST, modifiedMessage);
  }

}
