package com.londonlandvaluesim.engine;

import java.util.Objects;
import java.util.regex.Pattern;

public final class PostcodeNormalizer {
  private static final Pattern POSTCODE_PATTERN =
      Pattern.compile("^[A-Z]{1,2}[0-9][0-9A-Z]?[0-9][A-Z]{2}$");

  private PostcodeNormalizer() {
  }

  public static String normalize(String raw) {
    Objects.requireNonNull(raw, "raw");

    String cleaned = raw.trim().replaceAll("\\s+", "").toUpperCase();
    if (!POSTCODE_PATTERN.matcher(cleaned).matches()) {
      throw new IllegalArgumentException("Invalid UK postcode format");
    }

    int split = cleaned.length() - 3;
    return cleaned.substring(0, split) + " " + cleaned.substring(split);
  }
}
