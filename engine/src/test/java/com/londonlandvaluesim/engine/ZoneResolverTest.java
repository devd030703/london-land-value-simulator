package com.londonlandvaluesim.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ZoneResolverTest {
  @Test
  void resolvesOutwardCodeFromNormalizedPostcode() {
    Zone zone = ZoneResolver.resolveFromNormalizedPostcode("E14 8HX");
    assertEquals(ZoneType.OUTWARD_POSTCODE, zone.type());
    assertEquals("E14", zone.code());
  }
}
