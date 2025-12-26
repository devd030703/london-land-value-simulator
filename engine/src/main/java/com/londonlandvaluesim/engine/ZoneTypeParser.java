package com.londonlandvaluesim.engine;

public final class ZoneTypeParser {
  private ZoneTypeParser() {
  }

  public static ZoneType parseOrDefault(String raw) {
    if (raw == null || raw.isBlank()) {
      return ZoneType.OUTWARD_POSTCODE;
    }

    String normalized = raw.trim().toUpperCase();
    for (ZoneType type : ZoneType.values()) {
      if (type.name().equals(normalized)) {
        return type;
      }
    }

    throw new IllegalArgumentException("Unknown zoneType: " + raw);
  }
}
