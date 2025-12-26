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

  @Test
  void resolvesLsoaFromNormalizedPostcode() {
    Zone zone = ZoneResolver.resolve(ZoneType.LSOA, "E14 8HX");
    assertEquals(ZoneType.LSOA, zone.type());
    assertEquals("E01000001", zone.code());
  }
}
