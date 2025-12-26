package com.londonlandvaluesim.backend;

import com.londonlandvaluesim.engine.PostcodeNormalizer;
import com.londonlandvaluesim.engine.ValuationCalculator;
import com.londonlandvaluesim.engine.ValuationResult;
import com.londonlandvaluesim.engine.Zone;
import com.londonlandvaluesim.engine.ZoneResolver;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValuationController {
  private final ValuationCalculator calculator = new ValuationCalculator();
  private final CsvMedianPriceProvider medianPriceProvider = new CsvMedianPriceProvider();

  @GetMapping("/valuation")
  public ValuationResponse valuation(
      @RequestParam String postcode,
      @RequestParam BigDecimal landShare,
      @RequestParam BigDecimal taxRate
  ) {
    String normalizedPostcode = PostcodeNormalizer.normalize(postcode);
    Zone zone = ZoneResolver.resolveFromNormalizedPostcode(normalizedPostcode);
    BigDecimal medianPrice = medianPriceProvider.medianPriceFor(zone);
    ValuationResult result = calculator.calculate(medianPrice, landShare, taxRate);

    return new ValuationResponse(
        normalizedPostcode,
        zone.type().name(),
        zone.code(),
        medianPrice,
        landShare,
        taxRate,
        result.landValuePerDwelling(),
        result.annualLandTax()
    );
  }
}
