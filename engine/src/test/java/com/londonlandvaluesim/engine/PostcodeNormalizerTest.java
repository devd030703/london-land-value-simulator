package com.londonlandvaluesim.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PostcodeNormalizerTest {
  @Test
  void normalizesValidPostcodes() {
    assertEquals("E14 8HX", PostcodeNormalizer.normalize("E14 8HX"));
    assertEquals("E14 8HX", PostcodeNormalizer.normalize("e14 8hx"));
    assertEquals("E14 8HX", PostcodeNormalizer.normalize(" E148hx "));
  }

  @Test
  void rejectsNull() {
    assertThrows(NullPointerException.class, () -> PostcodeNormalizer.normalize(null));
  }

  @Test
  void rejectsInvalidFormats() {
    assertThrows(IllegalArgumentException.class, () -> PostcodeNormalizer.normalize(""));
    assertThrows(IllegalArgumentException.class, () -> PostcodeNormalizer.normalize("123"));
    assertThrows(IllegalArgumentException.class, () -> PostcodeNormalizer.normalize("E14"));
    assertThrows(IllegalArgumentException.class, () -> PostcodeNormalizer.normalize("E14 8H"));
    assertThrows(IllegalArgumentException.class, () -> PostcodeNormalizer.normalize("E14 8HXX"));
  }
}
