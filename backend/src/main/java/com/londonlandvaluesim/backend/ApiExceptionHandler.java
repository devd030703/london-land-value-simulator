package com.londonlandvaluesim.backend;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
  @ExceptionHandler(MissingServletRequestParameterException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiErrorResponse handleMissingParameter(MissingServletRequestParameterException ex) {
    String field = ex.getParameterName();
    return new ApiErrorResponse(
        "MISSING_PARAMETER",
        "Missing required parameter: " + field,
        field
    );
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiErrorResponse handleIllegalArgument(IllegalArgumentException ex) {
    return new ApiErrorResponse("INVALID_INPUT", ex.getMessage(), null);
  }
}
