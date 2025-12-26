package com.londonlandvaluesim.engine;

public final class PostcodeValidator {
  private PostcodeValidator() {
  }

  public static boolean isValid(String raw) {
    if (raw == null) {
      return false;
    }

    try {
      PostcodeNormalizer.normalize(raw);
      return true;
    } catch (IllegalArgumentException ex) {
      return false;
    }
  }
}
