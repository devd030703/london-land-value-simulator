package com.londonlandvaluesim.engine;

import java.math.BigDecimal;
import java.util.Map;

public final class StubMedianPriceProvider implements MedianPriceProvider {
  private static final Map<String, BigDecimal> MEDIANS = Map.of(
      "E14", new BigDecimal("500000"),
      "E1", new BigDecimal("650000"),
      "SW1A", new BigDecimal("1200000")
  );

  @Override
  public BigDecimal medianPriceFor(Zone zone) {
    if (zone == null || zone.code() == null || zone.code().isBlank()) {
      throw new IllegalArgumentException("Unknown zone: " + zone);
    }

    BigDecimal median = MEDIANS.get(zone.code());
    if (median == null) {
      throw new IllegalArgumentException("Unknown zone: " + zone.code());
    }

    return median;
  }
}
