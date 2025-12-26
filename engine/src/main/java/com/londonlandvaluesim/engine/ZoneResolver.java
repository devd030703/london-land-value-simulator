package com.londonlandvaluesim.engine;

public final class ZoneResolver {
  private ZoneResolver() {
  }

  public static Zone resolveFromNormalizedPostcode(String normalizedPostcode) {
    if (normalizedPostcode == null || normalizedPostcode.isBlank()) {
      throw new IllegalArgumentException("Postcode must be provided");
    }

    int spaceIndex = normalizedPostcode.indexOf(' ');
    if (spaceIndex <= 0 || spaceIndex == normalizedPostcode.length() - 1) {
      throw new IllegalArgumentException("Postcode must include a space separator");
    }

    String outward = normalizedPostcode.substring(0, spaceIndex);
    return new Zone(ZoneType.OUTWARD_POSTCODE, outward);
  }
}
