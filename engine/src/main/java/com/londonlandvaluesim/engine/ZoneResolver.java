package com.londonlandvaluesim.engine;

public final class ZoneResolver {
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

    throw new IllegalArgumentException("LSOA resolution requires a lookup");
  }

  public static Zone resolve(ZoneType zoneType, String normalizedPostcode, LsoaLookup lsoaLookup) {
    if (zoneType == ZoneType.OUTWARD_POSTCODE) {
      return resolve(zoneType, normalizedPostcode);
    }
    if (lsoaLookup == null) {
      throw new IllegalArgumentException("LSOA lookup must be provided");
    }

    String lsoa = lsoaLookup.lsoaForNormalizedPostcode(normalizedPostcode);
    return new Zone(ZoneType.LSOA, lsoa);
  }
}
