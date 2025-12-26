package com.londonlandvaluesim.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    LsoaLookup lookup = normalizedPostcode -> {
      if ("E14 8HX".equals(normalizedPostcode)) {
        return "E01000001";
      }
      throw new IllegalArgumentException("No LSOA for postcode: " + normalizedPostcode);
    };
    Zone zone = ZoneResolver.resolve(ZoneType.LSOA, "E14 8HX", lookup);
    assertEquals(ZoneType.LSOA, zone.type());
    assertEquals("E01000001", zone.code());
  }

  @Test
  void throwsWhenLsoaLookupMissing() {
    assertThrows(IllegalArgumentException.class,
        () -> ZoneResolver.resolve(ZoneType.LSOA, "E14 8HX"));
  }
}
