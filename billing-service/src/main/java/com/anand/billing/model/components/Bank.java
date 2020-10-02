package com.anand.billing.model.components;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Bank {

  private static final String NAME_OF_BANK = "name_of_bank";
  private static final String ACCOUNT_NUMBER = "account_number";
  private static final String IFSC = "ifsc";

  @JsonProperty(NAME_OF_BANK)
  private String nameOfBank;

  @JsonProperty(ACCOUNT_NUMBER)
  private String accountNumber;

  @JsonProperty(IFSC)
  private String ifsc;

  public String getNameOfBank() {
    return nameOfBank;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public String getIfsc() {
    return ifsc;
  }
}
