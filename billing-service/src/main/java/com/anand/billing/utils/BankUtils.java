package com.anand.billing.utils;

import com.anand.billing.model.components.Bank;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.property.TextAlignment;
import static com.anand.billing.constants.Constants.ACCOUNT_NUMBER;
import static com.anand.billing.constants.Constants.IFSC;
import static com.anand.billing.constants.Constants.NAME_OF_BANK;

public class BankUtils {

  public static Cell getIFSCCell(final Bank bankDetails) {
    return new Cell(1, 5)
        .add(String.format(IFSC, bankDetails.getIfsc()))
        .setTextAlignment(TextAlignment.LEFT)
        .setBorder(Border.NO_BORDER);
  }

  public static Cell getAccountNumberCell(final Bank bankDetails) {
    return new Cell(1, 5)
        .add(String.format(ACCOUNT_NUMBER, bankDetails.getAccountNumber()))
        .setTextAlignment(TextAlignment.LEFT)
        .setBorder(Border.NO_BORDER);
  }

  public static Cell getBankCell(final Bank bankDetails) {
    return new Cell(1, 5)
        .add(String.format(NAME_OF_BANK, bankDetails.getNameOfBank()))
        .setTextAlignment(TextAlignment.LEFT)
        .setBorder(Border.NO_BORDER);
  }
}
