package com.anand.billing.model.components;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Valuation {
  public static final String AMOUNT = "amount";
  public static final String TOTAL = "total";
  public static final String ROUND_OFF = "round_off";
  public static final String GRAND_TOTAL = "grand_total";
  public static final String RS_IN_WORDS = "rs_in_words";

  @JsonProperty(AMOUNT)
  private double amount;

  @JsonProperty(TOTAL)
  private double total;

  @JsonProperty(ROUND_OFF)
  private int roundOff;

  @JsonProperty(GRAND_TOTAL)
  private double grandTotal;

  @JsonProperty(RS_IN_WORDS)
  private String rsInWords;
}
