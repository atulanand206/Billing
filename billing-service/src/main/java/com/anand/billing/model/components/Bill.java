package com.anand.billing.model.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Bill {

  public static final String CONFIGURATION = "configuration";
  public static final String PARTICULARS = "particulars";

  @JsonProperty(CONFIGURATION)
  private Configuration fConfiguration;

  @JsonProperty(PARTICULARS)
  private List<Particular> fParticulars;

  public Configuration getConfiguration() {
    return fConfiguration;
  }

  public List<Particular> getParticulars() {
    return fParticulars;
  }
}
