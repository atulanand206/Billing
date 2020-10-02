package com.anand.billing.model.components;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Target {

  public static final String BILL_TO = "bill_to";
  public static final String ADDRESS = "address";
  public static final String GSTIN = "gstin";
  public static final String STATE_CODE = "state_code";

  @JsonProperty(BILL_TO)
  private String billTo;

  @JsonProperty(ADDRESS)
  private String address;

  @JsonProperty(GSTIN)
  private String gstin;

  @JsonProperty(STATE_CODE)
  private int stateCode;



}
