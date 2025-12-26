package com.londonlandvaluesim.backend;

import com.londonlandvaluesim.engine.PostcodeNormalizer;
import com.londonlandvaluesim.engine.ValuationCalculator;
import com.londonlandvaluesim.engine.ValuationResult;
import com.londonlandvaluesim.engine.Zone;
import com.londonlandvaluesim.engine.ZoneResolver;
import com.londonlandvaluesim.engine.ZoneType;
import com.londonlandvaluesim.engine.ZoneTypeParser;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValuationController {
  private final ValuationCalculator calculator = new ValuationCalculator();
  private final CsvMedianPriceProvider medianPriceProvider = new CsvMedianPriceProvider();
  private final OnspdCsvLsoaLookup lsoaLookup = new OnspdCsvLsoaLookup();

  @GetMapping("/valuation")
  public ValuationResponse valuation(
      @RequestParam String postcode,
      @RequestParam(required = false) String zoneType,
      @RequestParam BigDecimal landShare,
      @RequestParam BigDecimal taxRate
  ) {
    validateRate(landShare, "landShare");
    validateRate(taxRate, "taxRate");

    String normalizedPostcode = PostcodeNormalizer.normalize(postcode);
    ZoneType resolvedType = ZoneTypeParser.parseOrDefault(zoneType);
    Zone zone = resolvedType == ZoneType.LSOA
        ? ZoneResolver.resolve(resolvedType, normalizedPostcode, lsoaLookup)
        : ZoneResolver.resolve(resolvedType, normalizedPostcode);
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

  private static void validateRate(BigDecimal value, String name) {
    if (value == null) {
      throw new IllegalArgumentException(name + " must be between 0 and 1");
    }
    if (value.compareTo(BigDecimal.ZERO) < 0 || value.compareTo(BigDecimal.ONE) > 0) {
      throw new IllegalArgumentException(name + " must be between 0 and 1");
    }
  }
}
