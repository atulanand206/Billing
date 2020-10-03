package com.anand.billing.model.components;

import com.anand.billing.constants.Constants;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Page {

  private int total;
  private int grandTotal;
  private Date date;
  private int invoiceNumber;
  private final List<Particular> particulars;

  public Page(final Date date, final int invoiceNumber) {
    this.date = date;
    this.invoiceNumber = invoiceNumber;
    this.particulars = new ArrayList<>();
  }

  public Page(
      final Date date,
      final int invoiceNumber,
      final List<Particular> particulars) {
    this.date = date;
    this.invoiceNumber = invoiceNumber;
    this.particulars = particulars;
  }

  public void setTotal(final int total) {
    this.total = total;
  }

  public void setDate(final Date date) {
    this.date = date;
  }

  public int getGrandTotal() {
    return grandTotal;
  }

  public void setGrandTotal(final int grandTotal) {
    this.grandTotal = grandTotal;
  }

  public void setInvoiceNumber(final int invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
  }

  public int getTotal() {
    return total;
  }

  public Date getDate() {
    return date;
  }

  public int getInvoiceNumber() {
    return invoiceNumber;
  }

  public List<Particular> getParticulars() {
    return particulars;
  }

  public void addParticular(final Particular particular) {
    if (getLatestParticular() == null || canParticularBeAdded(particular)) {
      particulars.add(particular);
    }
  }

  public boolean canParticularBeAdded(final Particular particular) {
    if (particulars.size() == Constants.PAGE_MAX_SIZE)
      return false;
    return isDateEquals(date,particular.getDate());
  }

  private boolean isDateEquals(Date date1, Date date2) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    return dateFormat.format(date1).equals(dateFormat.format(date2));
  }

  public Particular getLatestParticular() {
    if (particulars.isEmpty()) {
      return null;
    }
    return particulars.get(particulars.size() - 1);
  }
}
