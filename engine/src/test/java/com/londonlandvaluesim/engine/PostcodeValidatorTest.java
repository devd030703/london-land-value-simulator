package com.londonlandvaluesim.engine;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PostcodeValidatorTest {
  @Test
  void validPostcodesReturnTrue() {
    assertTrue(PostcodeValidator.isValid("E14 8HX"));
    assertTrue(PostcodeValidator.isValid("e148hx"));
  }

  @Test
  void invalidPostcodesReturnFalse() {
    assertFalse(PostcodeValidator.isValid(null));
    assertFalse(PostcodeValidator.isValid(""));
    assertFalse(PostcodeValidator.isValid("E14"));
    assertFalse(PostcodeValidator.isValid("NOT A POSTCODE"));
  }
}
