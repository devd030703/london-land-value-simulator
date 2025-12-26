package com.londonlandvaluesim.engine;

import java.util.Objects;

public record Zone(ZoneType type, String code) {
  public Zone {
    Objects.requireNonNull(code, "code");
    if (code.isBlank()) {
      throw new IllegalArgumentException("code must be non-blank");
    }
  }
}
