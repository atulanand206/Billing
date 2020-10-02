package com.anand.billing.model.components;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Trip {

  public static final String FROM = "from";
  public static final String TO = "to";
  public static final String CORRY_NUMBER = "corry_number";
  public static final String DRIVER_NAME_AND_ADDRESS = "driver_name_and_address";
  public static final String DRIVER_MOBILE_NUMBER = "driver_mobile_number";

  @JsonProperty(FROM)
  private String from;

  @JsonProperty(TO)
  private String to;

  @JsonProperty(CORRY_NUMBER)
  private String corryNumber;

  @JsonProperty(DRIVER_NAME_AND_ADDRESS)
  private String driverNameAndAddress;

  @JsonProperty(DRIVER_MOBILE_NUMBER)
  private String driverMobileNumber;

}
