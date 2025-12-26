package com.londonlandvaluesim.backend;

import com.londonlandvaluesim.engine.MedianPriceProvider;
import com.londonlandvaluesim.engine.Zone;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public final class CsvMedianPriceProvider implements MedianPriceProvider {
  private static final String RESOURCE_NAME = "median_prices.csv";

  private final Map<String, BigDecimal> medians;

  public CsvMedianPriceProvider() {
    this.medians = loadMedians();
  }

  @Override
  public BigDecimal medianPriceFor(Zone zone) {
    if (zone == null || zone.code() == null || zone.code().isBlank() || zone.type() == null) {
      throw new IllegalArgumentException("Unknown zone: " + zone);
    }

    BigDecimal median = medians.get(keyFor(zone.type().name(), zone.code()));
    if (median == null) {
      throw new IllegalArgumentException("Unknown zone: " + zone.type().name() + ":" + zone.code());
    }

    return median;
  }

  public boolean hasPrice(Zone zone) {
    if (zone == null || zone.code() == null || zone.code().isBlank() || zone.type() == null) {
      return false;
    }

    return medians.containsKey(keyFor(zone.type().name(), zone.code()));
  }

  private Map<String, BigDecimal> loadMedians() {
    InputStream stream = CsvMedianPriceProvider.class.getClassLoader().getResourceAsStream(RESOURCE_NAME);
    if (stream == null) {
      throw new IllegalStateException("Missing resource: " + RESOURCE_NAME);
    }

    Map<String, BigDecimal> loaded = new HashMap<>();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
      String line = reader.readLine();
      if (line == null) {
        throw new IllegalStateException("Missing CSV header in " + RESOURCE_NAME);
      }

      while ((line = reader.readLine()) != null) {
        String trimmed = line.trim();
        if (trimmed.isEmpty()) {
          continue;
        }

        String[] parts = trimmed.split(",", -1);
        if (parts.length != 3) {
          throw new IllegalStateException("Invalid CSV row: " + line);
        }

        String zoneType = parts[0].trim();
        String code = parts[1].trim();
        String price = parts[2].trim();
        if (zoneType.isEmpty() || code.isEmpty() || price.isEmpty()) {
          throw new IllegalStateException("Invalid CSV row: " + line);
        }

        loaded.put(keyFor(zoneType, code), new BigDecimal(price));
      }
    } catch (IOException ex) {
      throw new IllegalStateException("Failed to load " + RESOURCE_NAME, ex);
    }

    return Map.copyOf(loaded);
  }

  private static String keyFor(String zoneType, String code) {
    return zoneType + ":" + code;
  }
}
