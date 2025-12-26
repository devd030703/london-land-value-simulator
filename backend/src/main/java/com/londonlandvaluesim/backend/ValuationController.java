package com.londonlandvaluesim.backend;

import com.londonlandvaluesim.engine.PostcodeNormalizer;
import com.londonlandvaluesim.engine.ValuationCalculator;
import com.londonlandvaluesim.engine.ValuationResult;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValuationController {
  private final ValuationCalculator calculator = new ValuationCalculator();

  @GetMapping("/valuation")
  public ValuationResponse valuation(
      @RequestParam String postcode,
      @RequestParam BigDecimal medianPrice,
      @RequestParam BigDecimal landShare,
      @RequestParam BigDecimal taxRate
  ) {
    String normalizedPostcode = PostcodeNormalizer.normalize(postcode);
    ValuationResult result = calculator.calculate(medianPrice, landShare, taxRate);

    return new ValuationResponse(
        normalizedPostcode,
        medianPrice,
        landShare,
        taxRate,
        result.landValuePerDwelling(),
        result.annualLandTax()
    );
  }
}
