package com.londonlandvaluesim.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class ValuationCalculatorTest {
  private final ValuationCalculator calculator = new ValuationCalculator();

  @Test
  void happyPathCalculatesLandValueAndTax() {
    BigDecimal medianPrice = new BigDecimal("500000");
    BigDecimal landShare = new BigDecimal("0.4");
    BigDecimal taxRate = new BigDecimal("0.03");

    ValuationResult result = calculator.calculate(medianPrice, landShare, taxRate);

    assertEquals(new BigDecimal("200000.00"), result.landValuePerDwelling());
    assertEquals(new BigDecimal("6000.00"), result.annualLandTax());
  }

  @Test
  void zeroLandShareProducesZeroLandValueAndTax() {
    ValuationResult result = calculator.calculate(
        new BigDecimal("500000"), new BigDecimal("0"), new BigDecimal("0.03"));

    assertEquals(new BigDecimal("0.00"), result.landValuePerDwelling());
    assertEquals(new BigDecimal("0.00"), result.annualLandTax());
  }

  @Test
  void zeroTaxRateProducesZeroAnnualTax() {
    ValuationResult result = calculator.calculate(
        new BigDecimal("500000"), new BigDecimal("0.4"), new BigDecimal("0"));

    assertEquals(new BigDecimal("200000.00"), result.landValuePerDwelling());
    assertEquals(new BigDecimal("0.00"), result.annualLandTax());
  }

  @Test
  void rejectsNullInputs() {
    assertThrows(NullPointerException.class,
        () -> calculator.calculate(null, new BigDecimal("0.4"), new BigDecimal("0.03")));
    assertThrows(NullPointerException.class,
        () -> calculator.calculate(new BigDecimal("500000"), null, new BigDecimal("0.03")));
    assertThrows(NullPointerException.class,
        () -> calculator.calculate(new BigDecimal("500000"), new BigDecimal("0.4"), null));
  }

  @Test
  void rejectsOutOfRangeValues() {
    assertThrows(IllegalArgumentException.class,
        () -> calculator.calculate(new BigDecimal("-1"), new BigDecimal("0.4"), new BigDecimal("0.03")));
    assertThrows(IllegalArgumentException.class,
        () -> calculator.calculate(new BigDecimal("500000"), new BigDecimal("-0.1"), new BigDecimal("0.03")));
    assertThrows(IllegalArgumentException.class,
        () -> calculator.calculate(new BigDecimal("500000"), new BigDecimal("1.1"), new BigDecimal("0.03")));
    assertThrows(IllegalArgumentException.class,
        () -> calculator.calculate(new BigDecimal("500000"), new BigDecimal("0.4"), new BigDecimal("-0.01")));
    assertThrows(IllegalArgumentException.class,
        () -> calculator.calculate(new BigDecimal("500000"), new BigDecimal("0.4"), new BigDecimal("1.01")));
  }
}
