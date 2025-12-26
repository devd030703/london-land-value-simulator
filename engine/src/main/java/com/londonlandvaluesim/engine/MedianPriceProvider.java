package com.londonlandvaluesim.engine;

import java.math.BigDecimal;

public interface MedianPriceProvider {
  BigDecimal medianPriceFor(Zone zone);
}
