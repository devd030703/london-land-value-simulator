package com.londonlandvaluesim.backend;

import com.londonlandvaluesim.engine.LsoaLookup;
import com.londonlandvaluesim.engine.PostcodeNormalizer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public final class OnspdCsvLsoaLookup implements LsoaLookup {
  private static final String RESOURCE_NAME = "onspd_sample.csv";

  private final Map<String, String> lsoaByPostcode;

  public OnspdCsvLsoaLookup() {
    this.lsoaByPostcode = loadLsoas();
  }

  @Override
  public String lsoaForNormalizedPostcode(String normalizedPostcode) {
    if (normalizedPostcode == null || normalizedPostcode.isBlank()) {
      throw new IllegalArgumentException("No LSOA for postcode: " + normalizedPostcode);
    }

    String lsoa = lsoaByPostcode.get(normalizedPostcode);
    if (lsoa == null) {
      throw new IllegalArgumentException("No LSOA for postcode: " + normalizedPostcode);
    }

    return lsoa;
  }

  private Map<String, String> loadLsoas() {
    InputStream stream = OnspdCsvLsoaLookup.class.getClassLoader().getResourceAsStream(RESOURCE_NAME);
    if (stream == null) {
      throw new IllegalStateException("Missing resource: " + RESOURCE_NAME);
    }

    Map<String, String> loaded = new HashMap<>();
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
        if (parts.length != 2) {
          throw new IllegalStateException("Invalid CSV row: " + line);
        }

        String postcode = parts[0].trim();
        String lsoa = parts[1].trim();
        if (postcode.isEmpty() || lsoa.isEmpty()) {
          throw new IllegalStateException("Invalid CSV row: " + line);
        }

        String normalizedPostcode = PostcodeNormalizer.normalize(postcode);
        loaded.put(normalizedPostcode, lsoa);
      }
    } catch (IOException ex) {
      throw new IllegalStateException("Failed to load " + RESOURCE_NAME, ex);
    }

    return Map.copyOf(loaded);
  }
}
