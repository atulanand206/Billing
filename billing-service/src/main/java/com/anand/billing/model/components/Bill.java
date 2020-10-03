package com.anand.billing.model.components;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Bill {

  public static final String CONFIGURATION = "configuration";
  public static final String PAGES = "pages";

  @JsonProperty(CONFIGURATION)
  private Configuration fConfiguration;

  @JsonProperty(PAGES)
  private List<Page> fPages;

  public Configuration getConfiguration() {
    return fConfiguration;
  }

  public List<Page> getPages() {
    return fPages;
  }
}
