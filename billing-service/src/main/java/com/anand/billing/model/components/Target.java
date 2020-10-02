package com.anand.billing.model.components;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Target {

  public static final String BILL_TO = "bill_to";
  public static final String ADDRESS = "address";
  public static final String GSTIN = "gstin";
  public static final String STATE_NAME = "state_name";
  public static final String STATE_CODE = "state_code";

  @JsonProperty(BILL_TO)
  private String billTo;

  @JsonProperty(ADDRESS)
  private String address;

  @JsonProperty(GSTIN)
  private String gstin;

  @JsonProperty(STATE_NAME)
  private String stateName;

  @JsonProperty(STATE_CODE)
  private int stateCode;

  public String getBillTo() {
    return billTo;
  }

  public String getAddress() {
    return address;
  }

  public String getGstin() {
    return gstin;
  }

  public String getStateName() {
    return stateName;
  }

  public int getStateCode() {
    return stateCode;
  }
}
