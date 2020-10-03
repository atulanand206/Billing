package com.anand.billing.utils;

import com.anand.billing.model.components.Bank;
import com.anand.billing.model.components.Configuration;
import com.anand.billing.model.components.Page;
import com.anand.billing.model.components.Particular;
import com.anand.billing.model.components.Rates;
import com.anand.billing.model.components.Trip;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import static com.anand.billing.utils.BankUtils.getAccountNumberCell;
import static com.anand.billing.utils.BankUtils.getBankCell;
import static com.anand.billing.utils.BankUtils.getIFSCCell;
import static com.anand.billing.utils.CalculationUtils.getPercentage;
import static com.anand.billing.utils.ITextUtils.addBlankCells;
import static com.anand.billing.utils.ITextUtils.addTableCells;
import static com.anand.billing.utils.ITextUtils.getTableCell;
import static com.anand.billing.utils.Strings.AMOUNT;
import static com.anand.billing.utils.Strings.DATE;
import static com.anand.billing.utils.Strings.GST_RATE;
import static com.anand.billing.utils.Strings.HSN_CODE;
import static com.anand.billing.utils.Strings.INVOICE_NO;
import static com.anand.billing.utils.Strings.PERMIT_NUMBER;
import static com.anand.billing.utils.Strings.QUANTITY;
import static com.anand.billing.utils.Strings.RATE;
import static com.anand.billing.utils.Strings.SL_NO;
import static com.anand.billing.utils.Strings.VEHICLE_NUMBER;
import static com.anand.billing.utils.TotalUtils.addCgstRow;
import static com.anand.billing.utils.TotalUtils.addGrandTotalRow;
import static com.anand.billing.utils.TotalUtils.addIgstRow;
import static com.anand.billing.utils.TotalUtils.addRoundOffRow;
import static com.anand.billing.utils.TotalUtils.addSgstRow;
import static com.anand.billing.utils.TotalUtils.addTotalRow;
import static com.anand.billing.utils.TripUtils.getFromToCell;

public class InvoiceUtils {

  public static List<String> getHeaders(final String unit) {
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

  public static List<Cell> addParticularRowToTable(
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

  public static List<Cell> addInvoiceInfo(final Page page) {
    List<Cell> cells = new ArrayList<>();
    Cell invoiceNumber = new Cell()
        .setTextAlignment(TextAlignment.RIGHT)
        .add(String.format(INVOICE_NO, page.getInvoiceNumber()));
    Cell date = new Cell()
        .setTextAlignment(TextAlignment.RIGHT)
        .add(String.format(DATE,
            new SimpleDateFormat(Configuration.OUTPUT_DATE_FORMAT).format(page.getDate())));
    cells.add(invoiceNumber);
    cells.add(date);
    return cells;
  }

  public static Table addParticulars(
      final Configuration configuration,
      final List<Particular> particulars,
      final Page page) {
    Rates rates = configuration.getRates();
    Trip tripDetails = configuration.getTripDetails();
    Bank bankDetails = configuration.getBankDetails();
    int total = page.getTotal();
    int grandTotal = page.getGrandTotal();
    Table table = new Table(new float[]{10F, 90F, 90F, 50F, 60F, 60F, 60F, 80F})
        .setFontSize(10)
        .setBorder(new SolidBorder(1));
    getHeaders(configuration.getRates().getQuantityUnit()).forEach(
        content -> table.addHeaderCell(content).setTextAlignment(TextAlignment.CENTER));
    for (int i = 0; i < particulars.size(); i++) {
      addTableCells(addParticularRowToTable(rates, i, particulars.get(i)), table);
    }
    addBlankCells(table);
    addTableCells(addTotalRow(rates, total), table);
    addBlankCells(table);
    addTableCells(addSgstRow(rates, total), table);
    table.addCell(getFromToCell(tripDetails));
    addTableCells(addCgstRow(rates, total), table);
    table.addCell(getBankCell(bankDetails));
    addTableCells(addIgstRow(rates, total), table);
    table.addCell(getAccountNumberCell(bankDetails));
    addTableCells(addRoundOffRow(), table);
    table.addCell(getIFSCCell(bankDetails));
    addTableCells(addGrandTotalRow(grandTotal), table);
    return table;
  }
}
