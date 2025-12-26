package com.londonlandvaluesim.engine;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class EngineVersionTest {
  @Test
  void versionIsNotEmpty() {
    assertFalse(EngineVersion.VERSION.isBlank());
  }
}
