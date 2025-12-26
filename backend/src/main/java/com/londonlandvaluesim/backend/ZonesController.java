package com.londonlandvaluesim.backend;

import com.londonlandvaluesim.engine.PostcodeNormalizer;
import com.londonlandvaluesim.engine.Zone;
import com.londonlandvaluesim.engine.ZoneResolver;
import com.londonlandvaluesim.engine.ZoneType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ZonesController {
  private final CsvMedianPriceProvider medianPriceProvider = new CsvMedianPriceProvider();
  private final OnspdCsvLsoaLookup lsoaLookup = new OnspdCsvLsoaLookup();

  @GetMapping("/zones")
  public ZonesResponse zones(@RequestParam String postcode) {
    String normalizedPostcode = PostcodeNormalizer.normalize(postcode);

    Zone outwardZone = ZoneResolver.resolve(ZoneType.OUTWARD_POSTCODE, normalizedPostcode);
    boolean outwardHasPricing = medianPriceProvider.hasPrice(outwardZone);

    Zone lsoaZone = null;
    try {
      lsoaZone = ZoneResolver.resolve(ZoneType.LSOA, normalizedPostcode, lsoaLookup);
    } catch (IllegalArgumentException ex) {
      lsoaZone = null;
    }

    String lsoaCode = lsoaZone == null ? null : lsoaZone.code();
    boolean lsoaHasPricing = lsoaZone != null && medianPriceProvider.hasPrice(lsoaZone);

    return new ZonesResponse(
        normalizedPostcode,
        outwardZone.code(),
        lsoaCode,
        outwardHasPricing,
        lsoaHasPricing
    );
  }
}
