package com.anand.billing.model.components;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class Invoice {

  public static final String DATE_FORMAT = "dd/MM/yyyy";
  public static final String TIME_ZONE = "IST";


  public static final String ENTITY = "entity";
  public static final String INVOICE_NUMBER = "invoice_number";
  public static final String DATE = "date";
  public static final String TARGET = "target";
  public static final String PARTICULARS = "particulars";
  public static final String RATES = "rates";
  public static final String VALUATION = "valuation";
  public static final String TRIP_DETAILS = "trip_details";
  public static final String BANK_DETAILS = "bank_details";
  public static final String AUTHORISED_SIGNATORY = "authorised_signatory";

  @JsonProperty(ENTITY)
  private Entity entity;

  @JsonProperty(INVOICE_NUMBER)
  private int invoiceNumber;

  @JsonProperty(DATE)
  @JsonFormat(pattern = DATE_FORMAT, timezone = TIME_ZONE)
  private Date date;

  @JsonProperty(TARGET)
  private Target target;

  @JsonProperty(PARTICULARS)
  private List<Particular> particulars;

  @JsonProperty(RATES)
  private Rates rates;

  @JsonProperty(VALUATION)
  private Valuation valuation;

  @JsonProperty(TRIP_DETAILS)
  private Trip tripDetails;

  @JsonProperty(BANK_DETAILS)
  private Bank bankDetails;

  @JsonProperty(AUTHORISED_SIGNATORY)
  private String authorisedSignatory;

  public Entity getEntity() {
    return entity;
  }

  public int getInvoiceNumber() {
    return invoiceNumber;
  }

  public Date getDate() {
    return date;
  }

  public Target getTarget() {
    return target;
  }

  public List<Particular> getParticulars() {
    return particulars;
  }

  public Rates getRates() {
    return rates;
  }

  public Valuation getValuation() {
    return valuation;
  }

  public Trip getTripDetails() {
    return tripDetails;
  }

  public Bank getBankDetails() {
    return bankDetails;
  }

  public String getAuthorisedSignatory() {
    return authorisedSignatory;
  }
}
