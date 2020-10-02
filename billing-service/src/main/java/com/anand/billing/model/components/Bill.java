package com.anand.billing.model.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Bill {

  public static final String INVOICE = "invoice";
  public static final String PARTICULARS = "particulars";

  @JsonProperty(INVOICE)
  private Invoice fInvoice;

  @JsonProperty(PARTICULARS)
  private List<Particular> fParticulars;

  public Invoice getInvoice() {
    return fInvoice;
  }

  public List<Particular> getParticulars() {
    return fParticulars;
  }
}
