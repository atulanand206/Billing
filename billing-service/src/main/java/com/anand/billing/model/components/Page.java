package com.anand.billing.model.components;

import java.util.Date;

public class Page {

  private int total;

  private int grandTotal;

  private Date date;

  private int invoiceNumber;

  public Page(final Date date, final int invoiceNumber) {
    this.date = date;
    this.invoiceNumber = invoiceNumber;
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
}
