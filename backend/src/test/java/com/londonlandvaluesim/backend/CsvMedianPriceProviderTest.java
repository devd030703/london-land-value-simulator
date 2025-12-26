package com.londonlandvaluesim.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class CsvMedianPriceProviderTest {
  @Test
  void loadsMedianPricesFromCsv() {
    CsvMedianPriceProvider provider = new CsvMedianPriceProvider();
    BigDecimal median = provider.medianPriceFor(
        new com.londonlandvaluesim.engine.Zone(
            com.londonlandvaluesim.engine.ZoneType.OUTWARD_POSTCODE, "E14"));
    assertEquals(new BigDecimal("500000"), median);
  }
}
