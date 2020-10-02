package com.anand.billing.model.components;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rates {

  public static final String GST_RATE = "gst_rate";
  public static final String SGST_RATE = "sgst_rate";
  public static final String CGST_RATE = "cgst_rate";
  public static final String IGST_RATE = "igst_rate";
  public static final String RATE_VALUE = "rate_value";
  public static final String RATE_UNIT = "rate_unit";

  @JsonProperty(GST_RATE)
  private double gstRate;

  @JsonProperty(SGST_RATE)
  private double sgstRate;

  @JsonProperty(CGST_RATE)
  private double cgstRate;

  @JsonProperty(IGST_RATE)
  private double igstRate;

  @JsonProperty(RATE_VALUE)
  private double rateValue;

  @JsonProperty(RATE_UNIT)
  private String rateUnit;

}
