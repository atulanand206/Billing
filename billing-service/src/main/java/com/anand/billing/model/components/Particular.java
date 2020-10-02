package com.anand.billing.model.components;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Particular {

  public static final String VEHICLE_NUMBER = "vehicle_number";
  public static final String PERMIT_NUMBER = "permit_number";
  public static final String HSN_CODE = "hsn_code";
  public static final String QUANTITY_VALUE = "quantity_value";
  public static final String QUANTITY_UNIT = "quantity_unit";

  @JsonProperty(VEHICLE_NUMBER)
  private String vehicleNumber;

  @JsonProperty(PERMIT_NUMBER)
  private String permitNumber;

  @JsonProperty(HSN_CODE)
  private String hsnCode;

  @JsonProperty(QUANTITY_VALUE)
  private int quantityValue;

  @JsonProperty(QUANTITY_UNIT)
  private String quantityUnit;
}
