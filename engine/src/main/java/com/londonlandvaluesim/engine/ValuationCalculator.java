package com.londonlandvaluesim.engine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class ValuationCalculator {
  private static final int MONEY_SCALE = 2;

  public ValuationResult calculate(BigDecimal medianPrice, BigDecimal landShare, BigDecimal taxRate) {
    Objects.requireNonNull(medianPrice, "medianPrice");
    Objects.requireNonNull(landShare, "landShare");
    Objects.requireNonNull(taxRate, "taxRate");

    requireNonNegative(medianPrice, "medianPrice");
    requireRate(landShare, "landShare");
    requireRate(taxRate, "taxRate");

    BigDecimal landValue = medianPrice.multiply(landShare);
    BigDecimal annualTax = landValue.multiply(taxRate);

    return new ValuationResult(scaleMoney(landValue), scaleMoney(annualTax));
  }

  private static void requireNonNegative(BigDecimal value, String name) {
    if (value.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException(name + " must be >= 0");
    }
  }

  private static void requireRate(BigDecimal value, String name) {
    if (value.compareTo(BigDecimal.ZERO) < 0 || value.compareTo(BigDecimal.ONE) > 0) {
      throw new IllegalArgumentException(name + " must be between 0 and 1");
    }
  }

  private static BigDecimal scaleMoney(BigDecimal value) {
    return value.setScale(MONEY_SCALE, RoundingMode.HALF_UP);
  }
}
