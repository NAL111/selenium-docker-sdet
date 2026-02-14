package com.nal.tests.vendorportal.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by N on 2/14/2026
 **/

public record VendorPortalTestData(String username,
                                   String password,

                                   @JsonProperty("monthlyEarning")
                                   String monthlyEarnings,

                                   String annualEarning,
                                   String profitMargin,
                                   String availableInventory,
                                   String searchKeyword,
                                   int searchResultsCount) { }
