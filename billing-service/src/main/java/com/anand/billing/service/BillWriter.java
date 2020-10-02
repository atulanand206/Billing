package com.anand.billing.service;

import com.anand.billing.model.components.Entity;
import com.anand.billing.model.components.Invoice;
import com.anand.billing.model.components.Page;
import com.anand.billing.model.components.Particular;
import com.anand.billing.model.components.Rates;
import com.anand.billing.model.components.Target;
import com.anand.billing.model.components.Trip;
import com.anand.billing.utils.EnglishNumberToWords;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.text.WordUtils;
import static com.anand.billing.utils.Strings.ACCOUNT_NUMBER;
import static com.anand.billing.utils.Strings.AMOUNT;
import static com.anand.billing.utils.Strings.AUTHORISED_SIGNATORY;
import static com.anand.billing.utils.Strings.CGST;
import static com.anand.billing.utils.Strings.CODE;
import static com.anand.billing.utils.Strings.DATE;
import static com.anand.billing.utils.Strings.FROM_TO;
import static com.anand.billing.utils.Strings.GRAND_TOTAL;
import static com.anand.billing.utils.Strings.GSTIN;
import static com.anand.billing.utils.Strings.GST_RATE;
import static com.anand.billing.utils.Strings.HSN_CODE;
import static com.anand.billing.utils.Strings.IFSC;
import static com.anand.billing.utils.Strings.IGST;
import static com.anand.billing.utils.Strings.INVOICE_NO;
import static com.anand.billing.utils.Strings.MOB;
import static com.anand.billing.utils.Strings.MS;
import static com.anand.billing.utils.Strings.NAME_OF_BANK;
import static com.anand.billing.utils.Strings.PERMIT_NUMBER;
import static com.anand.billing.utils.Strings.PROP;
import static com.anand.billing.utils.Strings.QUANTITY;
import static com.anand.billing.utils.Strings.RATE;
import static com.anand.billing.utils.Strings.ROUND_OFF;
import static com.anand.billing.utils.Strings.RS_IN_WORDS;
import static com.anand.billing.utils.Strings.SGST;
import static com.anand.billing.utils.Strings.SL_NO;
import static com.anand.billing.utils.Strings.STATE;
import static com.anand.billing.utils.Strings.TAX_INVOICE;
import static com.anand.billing.utils.Strings.TOTAL;
import static com.anand.billing.utils.Strings.VEHICLE_NUMBER;

public class BillWriter {

  private final Invoice fConfiguration;
  private final Document fDocument;
  private final List<Particular> fParticulars;
  private final Page fPage;

  public BillWriter(
      final Invoice configuration,
      final String fileName,
      final List<Particular> particulars,
      final Page page)
      throws IOException {
    fConfiguration = configuration;
    fParticulars = particulars;
    page.setTotal(getTotal(configuration.getRates(), particulars));
    page.setGrandTotal(grandTotal(configuration.getRates(), page.getTotal()));
    fPage = page;
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
    addCells(getTotalInWords());
    addBlankCell();
    addBlankCell();
    addCells(getSignatory());
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
        .add(TAX_INVOICE)
        .setUnderline(1, -2)
        .setWidthPercent(100)
        .setTextAlignment(TextAlignment.CENTER);
    cells.add(taxInvoiceChunk);
    return cells;
  }

  private Table addHeaderInfo() {
    Table table = new Table(new float[]{300F, 300F}).setBorder(Border.NO_BORDER);
    Cell gstinChunk = new Cell(3, 1)
        .add(String.format(GSTIN, fConfiguration.getEntity().getGstin()));
    Cell mobileChunk = new Cell()
        .setTextAlignment(TextAlignment.RIGHT)
        .add(String.format(MOB, fConfiguration.getEntity().getMobileNumber()));
    table.addCell(gstinChunk.setBorder(Border.NO_BORDER));
    table.addCell(mobileChunk.setBorder(Border.NO_BORDER));
    return table;
  }

  private List<Cell> addEntityInfo() {
    List<Cell> cells = new ArrayList<>();
    Entity entity = fConfiguration.getEntity();
    Cell entityName = new Cell()
        .setFontSize(20)
        .setWidth(UnitValue.createPercentValue(100))
        .setHorizontalAlignment(HorizontalAlignment.CENTER)
        .add(entity.getFirm());
    Cell entityProp = new Cell()
        .add(String.format(PROP, entity.getProprietor()));
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
        .add(String.format(INVOICE_NO, fPage.getInvoiceNumber()));
    Cell date = new Cell()
        .setTextAlignment(TextAlignment.RIGHT)
        .add(String.format(DATE,
            new SimpleDateFormat(Invoice.OUTPUT_DATE_FORMAT).format(fPage.getDate())));
    cells.add(invoiceNumber);
    cells.add(date);
    return cells;
  }

  private List<Cell> addTarget() {
    List<Cell> cells = new ArrayList<>();
    Target target = fConfiguration.getTarget();
    Cell targetName = new Cell()
        .setTextAlignment(TextAlignment.CENTER)
        .setFontSize(18)
        .add(String.format(MS, target.getBillTo()));
    Cell targetAddress = new Cell()
        .setTextAlignment(TextAlignment.CENTER)
        .add(target.getAddress());
    cells.add(targetName);
    cells.add(targetAddress);
    return cells;
  }

  private Table addTargetTable() {
    Target target = fConfiguration.getTarget();
    Table table = new Table(new float[]{300F, 200F, 100F}).setBorder(Border.NO_BORDER);
    Cell gstin = new Cell()
        .add(String.format(GSTIN, target.getGstin()));
    Cell state = new Cell()
        .setTextAlignment(TextAlignment.RIGHT)
        .add(String.format(STATE, target.getStateName()));
    Cell stateCode = new Cell()
        .setTextAlignment(TextAlignment.RIGHT)
        .add(String.format(CODE, target.getStateCode()));
    table.addCell(gstin.setBorder(Border.NO_BORDER));
    table.addCell(state.setBorder(Border.NO_BORDER));
    table.addCell(stateCode.setBorder(Border.NO_BORDER));
    return table;
  }

  private Table addParticulars() {
    List<Particular> particulars = fParticulars;
    Rates rates = fConfiguration.getRates();
    Trip tripDetails = fConfiguration.getTripDetails();
    Table table = new Table(new float[]{10F, 90F, 90F, 50F, 60F, 60F, 60F, 80F})
        .setFontSize(10)
        .setBorder(new SolidBorder(1));
    getHeaders(fConfiguration.getRates().getQuantityUnit()).forEach(
        content -> table.addHeaderCell(content).setTextAlignment(TextAlignment.CENTER));
    for (int i = 0; i < particulars.size(); i++) {
      addTableCells(addParticularRowToTable(rates, i, particulars.get(i)), table);
    }
    addBlankCells(table);
    addTableCells(addTotalRow(rates, fPage.getTotal()), table);
    addBlankCells(table);
    addTableCells(addSgstRow(rates, fPage.getTotal()), table);
    table.addCell(getFromToCell(tripDetails));
    addTableCells(addCgstRow(rates, fPage.getTotal()), table);
    table.addCell(getBankCell());
    addTableCells(addIgstRow(rates, fPage.getTotal()), table);
    table.addCell(getAccountNumberCell());
    addTableCells(addRoundOffRow(), table);
    table.addCell(getIFSCCell());
    addTableCells(addGrandTotalRow(), table);
    return table;
  }

  private int getTotal(
      final Rates rates,
      final List<Particular> particulars) {
    int total = 0;
    for (Particular particular : particulars) {
      total += particular.getQuantityValue() * rates.getRateValue();
    }
    return total;
  }

  private int grandTotal(
      final Rates rates,
      final int total) {
    return (int) (total + (total * (rates.getSgstRate() + rates.getCgstRate()))/100);
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
    headers.add(SL_NO);
    headers.add(VEHICLE_NUMBER);
    headers.add(PERMIT_NUMBER);
    headers.add(HSN_CODE);
    headers.add(String.format(QUANTITY, unit));
    headers.add(String.format(RATE, unit));
    headers.add(GST_RATE);
    headers.add(AMOUNT);
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

  private List<Cell> addTotalRow(final Rates rates, final int total) {
    List<Cell> cells = new ArrayList<>();
    cells.add(new Cell().add(TOTAL));
    cells.add(new Cell().add(getPercentage((int) rates.getGstRate())));
    cells.add(new Cell().add(String.valueOf((int) total)));
    return cells;
  }

  private List<Cell> addRoundOffRow() {
    List<Cell> cells = new ArrayList<>();
    cells.add(new Cell().add(ROUND_OFF));
    cells.add(new Cell().add(""));
    cells.add(new Cell().add(""));
    return cells;
  }

  private List<Cell> addGrandTotalRow() {
    List<Cell> cells = new ArrayList<>();
    cells.add(new Cell().add(GRAND_TOTAL));
    cells.add(new Cell().add(""));
    cells.add(new Cell().add(String.valueOf(fPage.getGrandTotal())));
    return cells;
  }

  private List<Cell> addSgstRow(final Rates rates, final int total) {
    return getXgstRow(SGST, rates.getSgstRate(), false, total);
  }

  private List<Cell> addCgstRow(final Rates rates, final int total) {
    return getXgstRow(CGST, rates.getCgstRate(), false, total);
  }

  private List<Cell> addIgstRow(final Rates rates, final int total) {
    return getXgstRow(IGST, rates.getIgstRate(), true, total);
  }

  private List<Cell> getXgstRow(
      final String xgst,
      final double xgstRate,
      final boolean skip,
      final int total) {
    List<Cell> cells = new ArrayList<>();
    cells.add(new Cell().add(xgst));
    cells.add(new Cell().add(skip ? "" : getPercentage(xgstRate)));
    cells.add(new Cell().add(skip ? "" :
        String.valueOf((int) ((total * xgstRate) / 100))));
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

  private Cell getIFSCCell() {
    return new Cell(1, 5)
        .add(String.format(IFSC, fConfiguration.getBankDetails().getIfsc()))
        .setTextAlignment(TextAlignment.LEFT)
        .setBorder(Border.NO_BORDER);
  }

  private Cell getAccountNumberCell() {
    return new Cell(1, 5)
        .add(String.format(ACCOUNT_NUMBER, fConfiguration.getBankDetails().getAccountNumber()))
        .setTextAlignment(TextAlignment.LEFT)
        .setBorder(Border.NO_BORDER);
  }

  private Cell getBankCell() {
    return new Cell(1, 5)
        .add(String.format(NAME_OF_BANK, fConfiguration.getBankDetails().getNameOfBank()))
        .setTextAlignment(TextAlignment.LEFT)
        .setBorder(Border.NO_BORDER);
  }

  private Cell getFromToCell(final Trip tripDetails) {
    return new Cell(1, 5)
        .add(String.format(FROM_TO, tripDetails.getFrom(), tripDetails.getTo()))
        .setTextAlignment(TextAlignment.LEFT)
        .setBorder(Border.NO_BORDER);
  }

  private List<Cell> getTotalInWords() {
    List<Cell> cells = new ArrayList<>();
    cells.add(new Cell()
        .add(String.format(
            RS_IN_WORDS,
            WordUtils.capitalize(EnglishNumberToWords.convert(fPage.getGrandTotal())).trim())));
    return cells;
  }

  private List<Cell> getSignatory() {
    List<Cell> cells = new ArrayList<>();
    cells.add(new Cell()
        .add(fConfiguration.getAuthorisedSignatory()).setTextAlignment(TextAlignment.RIGHT));
    cells.add(new Cell()
        .add(AUTHORISED_SIGNATORY).setTextAlignment(TextAlignment.RIGHT));
    return cells;
  }
}
