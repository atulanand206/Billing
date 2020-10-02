package com.anand.billing.model.components;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Trip {

  public static final String FROM = "from";
  public static final String TO = "to";
  public static final String LORRY_NUMBER = "lorry_number";
  public static final String DRIVER_NAME_AND_ADDRESS = "driver_name_and_address";
  public static final String DRIVER_MOBILE_NUMBER = "driver_mobile_number";

  @JsonProperty(FROM)
  private String from;

  @JsonProperty(TO)
  private String to;

  @JsonProperty(LORRY_NUMBER)
  private String lorryNumber;

  @JsonProperty(DRIVER_NAME_AND_ADDRESS)
  private String driverNameAndAddress;

  @JsonProperty(DRIVER_MOBILE_NUMBER)
  private String driverMobileNumber;

  public String getFrom() {
    return from;
  }

  public String getTo() {
    return to;
  }

  public String getLorryNumber() {
    return lorryNumber;
  }

  public String getDriverNameAndAddress() {
    return driverNameAndAddress;
  }

  public String getDriverMobileNumber() {
    return driverMobileNumber;
  }
}
