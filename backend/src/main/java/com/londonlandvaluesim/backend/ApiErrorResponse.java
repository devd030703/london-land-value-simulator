package com.londonlandvaluesim.backend;

public record ApiErrorResponse(String error, String message, String field) {
}
