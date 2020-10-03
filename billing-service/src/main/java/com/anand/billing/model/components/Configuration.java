package com.anand.billing.model.components;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Configuration {

  public static final String OUTPUT_DATE_FORMAT = "dd/MM/yyyy";
  public static final String TIME_ZONE = "IST";

  public static final String ENTITY = "entity";
  public static final String RATES = "rates";
  public static final String VALUATION = "valuation";
  public static final String BANK_DETAILS = "bank_details";
  public static final String AUTHORISED_SIGNATORY = "authorised_signatory";

  @JsonProperty(ENTITY)
  private Entity entity;

  @JsonProperty(RATES)
  private Rates rates;

  @JsonProperty(BANK_DETAILS)
  private Bank bankDetails;

  @JsonProperty(AUTHORISED_SIGNATORY)
  private String authorisedSignatory;

  public Entity getEntity() {
    return entity;
  }

  public Rates getRates() {
    return rates;
  }

  public Bank getBankDetails() {
    return bankDetails;
  }

  public String getAuthorisedSignatory() {
    return authorisedSignatory;
  }
}
