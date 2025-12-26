package com.londonlandvaluesim.backend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ValuationControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Test
  void returnsNormalizedPostcodeAndCalculatedValues() throws Exception {
    mockMvc.perform(get("/valuation")
            .param("postcode", "e148hx")
            .param("landShare", "0.4")
            .param("taxRate", "0.03"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.normalizedPostcode").value("E14 8HX"))
        .andExpect(jsonPath("$.zoneType").value("OUTWARD_POSTCODE"))
        .andExpect(jsonPath("$.zoneCode").value("E14"))
        .andExpect(jsonPath("$.medianPrice").value(500000))
        .andExpect(jsonPath("$.landValuePerDwelling").value(200000.00))
        .andExpect(jsonPath("$.annualLandTax").value(6000.00));
  }

  @Test
  void returnsLsoaValuationWhenZoneTypeProvided() throws Exception {
    mockMvc.perform(get("/valuation")
            .param("postcode", "e148hx")
            .param("zoneType", "LSOA")
            .param("landShare", "0.4")
            .param("taxRate", "0.03"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.zoneType").value("LSOA"))
        .andExpect(jsonPath("$.zoneCode").value("E01000001"))
        .andExpect(jsonPath("$.medianPrice").value(520000))
        .andExpect(jsonPath("$.landValuePerDwelling").value(208000.00))
        .andExpect(jsonPath("$.annualLandTax").value(6240.00));
  }
}
