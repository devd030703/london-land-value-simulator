package com.londonlandvaluesim.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class StubMedianPriceProviderTest {
  @Test
  void returnsExpectedMedianForKnownZone() {
    StubMedianPriceProvider provider = new StubMedianPriceProvider();
    BigDecimal median = provider.medianPriceFor(new Zone("E14"));
    assertEquals(new BigDecimal("500000"), median);
  }
}
