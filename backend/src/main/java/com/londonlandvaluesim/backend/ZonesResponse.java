package com.londonlandvaluesim.backend;

public record ZonesResponse(
    String normalizedPostcode,
    String outwardZoneCode,
    String lsoaZoneCode,
    boolean outwardHasPricing,
    boolean lsoaHasPricing
) {
}
