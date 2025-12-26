package com.londonlandvaluesim.engine;

public final class ZoneResolver {
  private static final java.util.Map<String, String> LSOA_BY_OUTWARD = java.util.Map.of(
      "E14", "E01000001",
      "E1", "E01000002",
      "SW1A", "E01000003"
  );

  private ZoneResolver() {
  }

  public static Zone resolveFromNormalizedPostcode(String normalizedPostcode) {
    return resolve(ZoneType.OUTWARD_POSTCODE, normalizedPostcode);
  }

  public static Zone resolve(ZoneType zoneType, String normalizedPostcode) {
    if (normalizedPostcode == null || normalizedPostcode.isBlank()) {
      throw new IllegalArgumentException("Postcode must be provided");
    }
    if (zoneType == null) {
      throw new IllegalArgumentException("Zone type must be provided");
    }

    int spaceIndex = normalizedPostcode.indexOf(' ');
    if (spaceIndex <= 0 || spaceIndex == normalizedPostcode.length() - 1) {
      throw new IllegalArgumentException("Postcode must include a space separator");
    }

    String outward = normalizedPostcode.substring(0, spaceIndex);
    if (zoneType == ZoneType.OUTWARD_POSTCODE) {
      return new Zone(ZoneType.OUTWARD_POSTCODE, outward);
    }

    String lsoa = LSOA_BY_OUTWARD.get(outward);
    if (lsoa == null) {
      throw new IllegalArgumentException("No LSOA mapping for outward: " + outward);
    }

    return new Zone(ZoneType.LSOA, lsoa);
  }
}
