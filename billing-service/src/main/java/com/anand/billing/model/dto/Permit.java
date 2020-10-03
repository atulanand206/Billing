package com.anand.billing.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import static com.anand.billing.model.components.Configuration.TIME_ZONE;

public class Permit {

  public static final String INPUT_DATE_FORMAT = "dd-MMM-yyyy hh:mm a";

  public static final String SL_NO = "Sl. No.";
  public static final String PERMIT_NO = "Permit No";
  public static final String PASS_NO = "Pass No";
  public static final String PASS_VALIDITY = "Pass Validity";
  public static final String ISSUED_ON = "Issued On";
  public static final String TRUCK_REGD_NO = "Truck Regd. No";
  public static final String MINERAL = "Mineral";
  public static final String MINERAL_TYPE = "Mineral Type";
  public static final String MINERAL_WT = "Mineral Wt.";
  public static final String SOURCE = "Source";
  public static final String DESTINATION = "Destination";
  public static final String CONSIGNEE = "Consignee";
  public static final String CHECK_GATE = "Check Gate";
  public static final String DISTANCE_IN_KM = "Distance in(K.M.)";

  @JsonProperty(SL_NO)
  private int slNo;

  @JsonProperty(PERMIT_NO)
  private String permitNo;

  @JsonProperty(PASS_NO)
  private String passNo;

  @JsonProperty(PASS_VALIDITY)
  @JsonFormat(pattern = INPUT_DATE_FORMAT, timezone = TIME_ZONE)
  private Date passValidity;

  @JsonProperty(ISSUED_ON)
  @JsonFormat(pattern = INPUT_DATE_FORMAT, timezone = TIME_ZONE)
  private Date issuedOn;

  @JsonProperty(TRUCK_REGD_NO)
  private String truckRegNo;

  @JsonProperty(MINERAL)
  private String mineral;

  @JsonProperty(MINERAL_TYPE)
  private String mineralType;

  @JsonProperty(MINERAL_WT)
  private int mineralWt;

  @JsonProperty(SOURCE)
  private String source;

  @JsonProperty(DESTINATION)
  private String destination;

  @JsonProperty(CONSIGNEE)
  private String consignee;

  @JsonProperty(CHECK_GATE)
  private String checkGate;

  @JsonProperty(DISTANCE_IN_KM)
  private int distance;

  public Permit(
      final int slNo,
      final String permitNo,
      final String passNo,
      final Date passValidity,
      final Date issuedOn,
      final String truckRegNo,
      final String mineral,
      final String mineralType,
      final int mineralWt,
      final String source,
      final String destination,
      final String consignee,
      final String checkGate,
      final int distance) {
    this.slNo = slNo;
    this.permitNo = permitNo;
    this.passNo = passNo;
    this.passValidity = passValidity;
    this.issuedOn = issuedOn;
    this.truckRegNo = truckRegNo;
    this.mineral = mineral;
    this.mineralType = mineralType;
    this.mineralWt = mineralWt;
    this.source = source;
    this.destination = destination;
    this.consignee = consignee;
    this.checkGate = checkGate;
    this.distance = distance;
  }

  public Permit(List<String> csvRow) {
    if (csvRow.size()<1)
      return;
    this.slNo = Integer.parseInt(csvRow.get(0).trim());
    if (csvRow.size()<2)
      return;
    this.permitNo = csvRow.get(1).trim();
    if (csvRow.size()<3)
      return;
    this.passNo = csvRow.get(2).trim();
    if (csvRow.size()<4)
      return;
    this.passValidity = parsedDate(csvRow.get(3).trim());
    if (csvRow.size()<5)
      return;
    this.issuedOn = parsedDate(csvRow.get(4).trim());
    if (csvRow.size()<6)
      return;
    this.truckRegNo = csvRow.get(5).trim();
    if (csvRow.size()<7)
      return;
    this.mineral = csvRow.get(6).trim();
    if (csvRow.size()<8)
      return;
    this.mineralType = csvRow.get(7).trim();
    if (csvRow.size()<9)
      return;
    this.mineralWt = Integer.parseInt(csvRow.get(8).trim());
    if (csvRow.size()<10)
      return;
    this.source = csvRow.get(9).trim();
    if (csvRow.size()<11)
      return;
    this.destination = csvRow.get(10).trim();
    if (csvRow.size()<12)
      return;
    this.consignee = csvRow.get(11).trim();
    if (csvRow.size()<13)
      return;
    this.checkGate = csvRow.get(12).trim();
    if (csvRow.size()<14)
      return;
    this.distance = Integer.parseInt(csvRow.get(13).trim());
  }

  private Date parsedDate(final String date) {
    try {
      return new SimpleDateFormat(INPUT_DATE_FORMAT).parse(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

  public int getSlNo() {
    return slNo;
  }

  public String getPermitNo() {
    return permitNo;
  }

  public String getPassNo() {
    return passNo;
  }

  public Date getPassValidity() {
    return passValidity;
  }

  public Date getIssuedOn() {
    return issuedOn;
  }

  public String getTruckRegNo() {
    return truckRegNo;
  }

  public String getMineral() {
    return mineral;
  }

  public String getMineralType() {
    return mineralType;
  }

  public int getMineralWt() {
    return mineralWt;
  }

  public String getSource() {
    return source;
  }

  public String getDestination() {
    return destination;
  }

  public String getConsignee() {
    return consignee;
  }

  public String getCheckGate() {
    return checkGate;
  }

  public int getDistance() {
    return distance;
  }

  @Override
  public String toString() {
    return "Permit{" +
        "slNo=" + slNo +
        ", permitNo='" + permitNo + '\'' +
        ", passNo='" + passNo + '\'' +
        ", passValidity=" + passValidity +
        ", issuedOn=" + issuedOn +
        ", truckRegNo='" + truckRegNo + '\'' +
        ", mineral='" + mineral + '\'' +
        ", mineralType='" + mineralType + '\'' +
        ", mineralWt=" + mineralWt +
        ", source='" + source + '\'' +
        ", destination='" + destination + '\'' +
        ", consignee='" + consignee + '\'' +
        ", checkGate='" + checkGate + '\'' +
        ", distance=" + distance +
        '}';
  }
}
