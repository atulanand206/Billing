package com.anand.billing.model.components;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Entity {

  public static final String GSTIN = "gstin";
  public static final String MOBILE_NUMBER = "mobile_number";
  public static final String FIRM = "firm";
  public static final String PROPRIETOR = "proprietor";
  public static final String HOME_ADDRESS = "home_address";
  public static final String FIRM_ADDRESS = "firm_address";

  @JsonProperty(GSTIN)
  private String gstin;

  @JsonProperty(MOBILE_NUMBER)
  private String mobileNumber;

  @JsonProperty(FIRM)
  private String firm;

  @JsonProperty(PROPRIETOR)
  private String proprietor;

  @JsonProperty(HOME_ADDRESS)
  private String homeAddress;

  @JsonProperty(FIRM_ADDRESS)
  private String firmAddress;

}
