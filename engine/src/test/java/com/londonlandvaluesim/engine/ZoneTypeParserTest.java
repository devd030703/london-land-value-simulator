package com.londonlandvaluesim.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ZoneTypeParserTest {
  @Test
  void defaultsToOutwardPostcodeWhenBlank() {
    assertEquals(ZoneType.OUTWARD_POSTCODE, ZoneTypeParser.parseOrDefault(null));
    assertEquals(ZoneType.OUTWARD_POSTCODE, ZoneTypeParser.parseOrDefault(""));
    assertEquals(ZoneType.OUTWARD_POSTCODE, ZoneTypeParser.parseOrDefault("  "));
  }

  @Test
  void parsesCaseInsensitiveValues() {
    assertEquals(ZoneType.OUTWARD_POSTCODE, ZoneTypeParser.parseOrDefault("outward_postcode"));
    assertEquals(ZoneType.LSOA, ZoneTypeParser.parseOrDefault("lsoa"));
  }

  @Test
  void throwsForUnknownValues() {
    assertThrows(IllegalArgumentException.class, () -> ZoneTypeParser.parseOrDefault("unknown"));
  }
}
