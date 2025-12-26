package com.londonlandvaluesim.backend;

import java.math.BigDecimal;

public record ValuationResponse(
    String normalizedPostcode,
    String zoneType,
    String zoneCode,
    BigDecimal medianPrice,
    BigDecimal landShare,
    BigDecimal taxRate,
    BigDecimal landValuePerDwelling,
    BigDecimal annualLandTax
) {
}
