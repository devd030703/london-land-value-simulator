package com.londonlandvaluesim.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class OnspdCsvLsoaLookupTest {
  @Test
  void returnsLsoaForKnownPostcode() {
    OnspdCsvLsoaLookup lookup = new OnspdCsvLsoaLookup();
    assertEquals("E01000001", lookup.lsoaForNormalizedPostcode("E14 8HX"));
  }
}
