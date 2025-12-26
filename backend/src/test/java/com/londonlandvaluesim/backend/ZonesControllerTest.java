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
class ZonesControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Test
  void returnsZonesAndPricingForKnownPostcode() throws Exception {
    mockMvc.perform(get("/zones")
            .param("postcode", "e148hx"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.normalizedPostcode").value("E14 8HX"))
        .andExpect(jsonPath("$.outwardZoneCode").value("E14"))
        .andExpect(jsonPath("$.lsoaZoneCode").value("E01000001"))
        .andExpect(jsonPath("$.outwardHasPricing").value(true))
        .andExpect(jsonPath("$.lsoaHasPricing").value(true));
  }

  @Test
  void returnsNullLsoaWhenLookupMissing() throws Exception {
    mockMvc.perform(get("/zones")
            .param("postcode", "E14 9ZZ"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.normalizedPostcode").value("E14 9ZZ"))
        .andExpect(jsonPath("$.outwardZoneCode").value("E14"))
        .andExpect(jsonPath("$.lsoaZoneCode").doesNotExist())
        .andExpect(jsonPath("$.outwardHasPricing").value(true))
        .andExpect(jsonPath("$.lsoaHasPricing").value(false));
  }

  @Test
  void rejectsMissingPostcode() throws Exception {
    mockMvc.perform(get("/zones"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error").value("MISSING_PARAMETER"))
        .andExpect(jsonPath("$.field").value("postcode"));
  }

  @Test
  void rejectsInvalidPostcode() throws Exception {
    mockMvc.perform(get("/zones")
            .param("postcode", "bad"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error").value("INVALID_INPUT"));
  }
}
