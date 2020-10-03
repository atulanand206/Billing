package com.anand.billing.utils;

import com.anand.billing.model.components.Entity;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import java.util.ArrayList;
import java.util.List;
import static com.anand.billing.utils.Strings.AUTHORISED_SIGNATORY;
import static com.anand.billing.utils.Strings.GSTIN;
import static com.anand.billing.utils.Strings.MOB;
import static com.anand.billing.utils.Strings.TAX_INVOICE;

public class HeaderUtils {

  public static List<Cell> addHeader() {
    List<Cell> cells = new ArrayList<>();
    Cell taxInvoiceChunk = new Cell()
        .add(TAX_INVOICE)
        .setUnderline(1, -2)
        .setWidthPercent(100)
        .setTextAlignment(TextAlignment.CENTER);
    cells.add(taxInvoiceChunk);
    return cells;
  }

  public static Table addHeaderInfo(Entity entity) {
    Table table = new Table(new float[]{300F, 300F}).setBorder(Border.NO_BORDER);
    Cell gstinChunk = new Cell(3, 1)
        .add(String.format(GSTIN, entity.getGstin()));
    Cell mobileChunk = new Cell()
        .setTextAlignment(TextAlignment.RIGHT)
        .add(String.format(MOB, entity.getMobileNumber()));
    table.addCell(gstinChunk.setBorder(Border.NO_BORDER));
    table.addCell(mobileChunk.setBorder(Border.NO_BORDER));
    return table;
  }

  public static List<Cell> getSignatory(final String authorisedSignatory) {
    List<Cell> cells = new ArrayList<>();
    cells.add(new Cell()
        .add(authorisedSignatory).setTextAlignment(TextAlignment.RIGHT));
    cells.add(new Cell()
        .add(AUTHORISED_SIGNATORY).setTextAlignment(TextAlignment.RIGHT));
    return cells;
  }
}
