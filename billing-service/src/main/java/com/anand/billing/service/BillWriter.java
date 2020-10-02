package com.anand.billing.service;

import com.anand.billing.model.components.Entity;
import com.anand.billing.model.components.Invoice;
import com.anand.billing.model.components.Particular;
import com.anand.billing.model.components.Rates;
import com.anand.billing.model.components.Target;
import com.anand.billing.model.components.Trip;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BillWriter {

  private final Invoice fInvoice;
  private final Document fDocument;

  public BillWriter(final Invoice invoice, final String fileName)
      throws IOException {
    fInvoice = invoice;
    PdfWriter writer = new PdfWriter(fileName);
    PdfDocument pdf = new PdfDocument(writer);
    fDocument = new Document(pdf);
  }

  public void writeContent() {
    addCells(addHeader());
    fDocument.add(addHeaderInfo());
    addLineSeparator();
    addCells(addEntityInfo());
    addLineSeparator();
    addCells(addInvoiceInfo());
    addLineSeparator();
    addBlankCell();
    addCells(addTarget());
    fDocument.add(addTargetTable());
    fDocument.add(addParticulars());
    addBlankCell();
    addCells(footer());
    fDocument.close();
  }

  private void addLineSeparator() {
    fDocument.add(lineSeparatorElement());
  }

  private void addBlankCell() {
    fDocument.add(new Cell());
  }

  private void addCells(List<Cell> cells) {
    cells.forEach(fDocument::add);
  }

  private LineSeparator lineSeparatorElement() {
    return new LineSeparator(new SolidLine(1));
  }

  private List<Cell> addHeader() {
    List<Cell> cells = new ArrayList<>();
    Cell taxInvoiceChunk = new Cell()
        .add("Tax Invoice")
        .setUnderline(1, -2)
        .setWidthPercent(100)
        .setTextAlignment(TextAlignment.CENTER);
    cells.add(taxInvoiceChunk);
    return cells;
  }

  private Table addHeaderInfo() {
    Table table = new Table(new float[]{300F, 300F}).setBorder(Border.NO_BORDER);
    Cell gstinChunk = new Cell(3, 1)
        .add(String.format("GSTIN-%s", fInvoice.getEntity().getGstin()));
    Cell mobileChunk = new Cell()
        .setTextAlignment(TextAlignment.RIGHT)
        .add(String.format("Mob.-%s", fInvoice.getEntity().getMobileNumber()));
    table.addCell(gstinChunk.setBorder(Border.NO_BORDER));
    table.addCell(mobileChunk.setBorder(Border.NO_BORDER));
    return table;
  }

  private List<Cell> addEntityInfo() {
    List<Cell> cells = new ArrayList<>();
    Entity entity = fInvoice.getEntity();
    Cell entityName = new Cell()
        .setFontSize(20)
        .setWidth(UnitValue.createPercentValue(100))
        .setHorizontalAlignment(HorizontalAlignment.CENTER)
        .add(entity.getFirm());
    Cell entityProp = new Cell()
        .add(String.format("Prop. - %s", entity.getProprietor()));
    Cell entityHomeAddress = new Cell()
        .add(entity.getHomeAddress());
    Cell entityFirmAddress = new Cell()
        .add(entity.getFirmAddress());
    cells.add(entityName);
    cells.add(entityProp);
    cells.add(entityHomeAddress);
    cells.add(entityFirmAddress);
    return cells;
  }

  private List<Cell> addInvoiceInfo() {
    List<Cell> cells = new ArrayList<>();
    Cell invoiceNumber = new Cell()
        .setTextAlignment(TextAlignment.RIGHT)
        .add(String.format("Invoice No. - %s", fInvoice.getInvoiceNumber()));
    Cell date = new Cell()
        .setTextAlignment(TextAlignment.RIGHT)
        .add(String.format("Date - %s",
            new SimpleDateFormat("dd/MM/yyyy").format(fInvoice.getDate())));
    cells.add(invoiceNumber);
    cells.add(date);
    return cells;
  }

  private List<Cell> addTarget() {
    List<Cell> cells = new ArrayList<>();
    Target target = fInvoice.getTarget();
    Cell targetName = new Cell()
        .setTextAlignment(TextAlignment.CENTER)
        .setFontSize(18)
        .add(String.format("M/s %s", target.getBillTo()));
    Cell targetAddress = new Cell()
        .setTextAlignment(TextAlignment.CENTER)
        .add(target.getAddress());
    cells.add(targetName);
    cells.add(targetAddress);
    return cells;
  }

  private Table addTargetTable() {
    Target target = fInvoice.getTarget();
    Table table = new Table(new float[]{300F, 200F, 100F}).setBorder(Border.NO_BORDER);
    Cell gstin = new Cell()
        .add(String.format("GSTIN- %s", target.getGstin()));
    Cell state = new Cell()
        .setTextAlignment(TextAlignment.RIGHT)
        .add(String.format("State- %s", target.getStateName()));
    Cell stateCode = new Cell()
        .setTextAlignment(TextAlignment.RIGHT)
        .add(String.format("Code- %s", target.getStateCode()));
    table.addCell(gstin.setBorder(Border.NO_BORDER));
    table.addCell(state.setBorder(Border.NO_BORDER));
    table.addCell(stateCode.setBorder(Border.NO_BORDER));
    return table;
  }

  private Table addParticulars() {
    List<Particular> particulars = fInvoice.getParticulars();
    Rates rates = fInvoice.getRates();
    Trip tripDetails = fInvoice.getTripDetails();
    Table table = new Table(new float[]{10F, 90F, 90F, 50F, 60F, 60F, 60F, 80F})
        .setFontSize(10)
        .setBorder(new SolidBorder(1));
    getHeaders(fInvoice.getRates().getQuantityUnit()).forEach(
        content -> table.addHeaderCell(content).setTextAlignment(TextAlignment.CENTER));
    for (int i = 0; i < particulars.size(); i++) {
      addTableCells(addParticularRowToTable(rates, i, particulars.get(i)), table);
    }
    addBlankCells(table);
    addTableCells(addTotalRow(rates), table);
    addBlankCells(table);
    addTableCells(addSgstRow(rates), table);
    table.addCell(getFromToCell(tripDetails));
    addTableCells(addCgstRow(rates), table);
    table.addCell(getCorryNoCell(tripDetails));
    addTableCells(addIgstRow(rates), table);
    table.addCell(getDriverNameAndAddressCell(tripDetails));
    addTableCells(addRoundOffRow(), table);
    table.addCell(getMobileNumberCell(tripDetails));
    addTableCells(addGrandTotalRow(), table);
    return table;
  }

  private void addTableCells(List<Cell> cells, Table table) {
    cells.forEach(table::addCell);
  }

  private void addBlankCells(final Table table) {
    for (int i = 0; i < 5; i++) {
      table.addCell(new Cell().setBorder(Border.NO_BORDER));
    }
  }

  private List<String> getHeaders(final String unit) {
    List<String> headers = new ArrayList<>();
    headers.add("Sl. No.");
    headers.add("Vehicle number");
    headers.add("Permit number");
    headers.add("HSN Code");
    headers.add(String.format("Quantity\n%s", unit));
    headers.add(String.format("Rate\n%s", unit));
    headers.add("GST Rate");
    headers.add("Amount");
    return headers;
  }

  private List<Cell> addParticularRowToTable(
      final Rates rates,
      final int i,
      final Particular particular) {
    List<Cell> cells = new ArrayList<>();
    cells.add(getTableCell(String.valueOf(i + 1)));
    cells.add(getTableCell(particular.getVehicleNumber()));
    cells.add(getTableCell(particular.getPermitNumber()));
    cells.add(getTableCell(particular.getHsnCode()));
    cells.add(getTableCell(String.valueOf(particular.getQuantityValue())));
    cells.add(getTableCell(String.valueOf((int) rates.getRateValue())));
    cells.add(getTableCell(getPercentage((int) rates.getGstRate())));
    cells.add(
        getTableCell(String.valueOf((int) (particular.getQuantityValue() * rates.getRateValue()))));
    return cells;
  }

  private List<Cell> addTotalRow(final Rates rates) {
    List<Cell> cells = new ArrayList<>();
    cells.add(new Cell().add("Total"));
    cells.add(new Cell().add(getPercentage((int) rates.getGstRate())));
    cells.add(new Cell().add(String.valueOf((int) fInvoice.getValuation().getTotal())));
    return cells;
  }

  private List<Cell> addRoundOffRow() {
    List<Cell> cells = new ArrayList<>();
    cells.add(new Cell().add("R/off."));
    cells.add(new Cell().add(""));
    cells.add(new Cell().add(""));
    return cells;
  }

  private List<Cell> addGrandTotalRow() {
    List<Cell> cells = new ArrayList<>();
    cells.add(new Cell().add("G.Total"));
    cells.add(new Cell().add(""));
    cells.add(new Cell().add(String.valueOf((int) fInvoice.getValuation().getGrandTotal())));
    return cells;
  }

  private List<Cell> addSgstRow(final Rates rates) {
    return getXgstRow("SGST @", rates.getSgstRate(), false);
  }

  private List<Cell> addCgstRow(final Rates rates) {
    return getXgstRow("CGST @", rates.getCgstRate(), false);
  }

  private List<Cell> addIgstRow(final Rates rates) {
    return getXgstRow("IGST @", rates.getIgstRate(), true);
  }

  private List<Cell> getXgstRow(final String xgst, final double xgstRate, final boolean skip) {
    List<Cell> cells = new ArrayList<>();
    cells.add(new Cell().add(xgst));
    cells.add(new Cell().add(skip ? "" : getPercentage(xgstRate)));
    cells.add(new Cell().add(skip ? "" :
        String.valueOf((int) ((fInvoice.getValuation().getTotal() * xgstRate) / 100))));
    return cells;
  }

  private String getPercentage(final int value) {
    return String.format("%d%%", value);
  }

  private String getPercentage(final double value) {
    return String.format("%.1f%%", value);
  }

  private Cell getTableCell(final String content) {
    return new Cell().add(content).setTextAlignment(TextAlignment.CENTER);
  }

  private Table addTripDetails() {
    Table table = new Table(new float[]{324F}).setBorder(new SolidBorder(1));
    List<Cell> cells = new ArrayList<>();
    Trip tripDetails = fInvoice.getTripDetails();
    cells.add(getFromToCell(tripDetails));
    cells.add(getCorryNoCell(tripDetails));
    cells.add(getDriverNameAndAddressCell(tripDetails));
    cells.add(getMobileNumberCell(tripDetails));
    cells.forEach(table::addCell);
    return table;
  }

  private Cell getMobileNumberCell(final Trip tripDetails) {
    return new Cell(1, 5)
        .add(String.format("Mobile Number: %s", tripDetails.getDriverMobileNumber()))
        .setTextAlignment(TextAlignment.LEFT)
        .setBorder(Border.NO_BORDER);
  }

  private Cell getDriverNameAndAddressCell(final Trip tripDetails) {
    return new Cell(1, 5)
        .add(String.format("Driver Name & Address: %s", tripDetails.getDriverNameAndAddress()))
        .setTextAlignment(TextAlignment.LEFT)
        .setBorder(Border.NO_BORDER);
  }

  private Cell getCorryNoCell(final Trip tripDetails) {
    return new Cell(1, 5)
        .add(String.format("Corry No: %s", tripDetails.getCorryNumber()))
        .setTextAlignment(TextAlignment.LEFT)
        .setBorder(Border.NO_BORDER);
  }

  private Cell getFromToCell(final Trip tripDetails) {
    return new Cell(1, 5)
        .add(String.format("From: %s To: %s", tripDetails.getFrom(), tripDetails.getTo()))
        .setTextAlignment(TextAlignment.LEFT)
        .setBorder(Border.NO_BORDER);
  }

  private List<Cell> footer() {
    List<Cell> cells = new ArrayList<>();
    cells.add(new Cell()
        .add(String.format("Rs. in words: %s",fInvoice.getValuation().getRsInWords())));
    cells.add(new Cell()
        .add(String.format("Name of Bank: %s", fInvoice.getBankDetails().getNameOfBank())));
    cells.add(new Cell()
        .add(String.format("A/c No.: %s", fInvoice.getBankDetails().getAccountNumber())));
    cells.add(new Cell()
        .add(String.format("IFSC Code: %s", fInvoice.getBankDetails().getIfsc())));
    cells.add(new Cell()
        .add(fInvoice.getAuthorisedSignatory()).setTextAlignment(TextAlignment.RIGHT));
    cells.add(new Cell()
        .add("Authorised Signatory").setTextAlignment(TextAlignment.RIGHT));
    return cells;
  }
}
