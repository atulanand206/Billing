package com.anand.billing.utils;

import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import java.util.List;

public class ITextUtils {

  public static LineSeparator lineSeparatorElement() {
    return new LineSeparator(new SolidLine(1));
  }

  public static void addBlankCells(final Table table) {
    for (int i = 0; i < 5; i++) {
      table.addCell(new Cell().setBorder(Border.NO_BORDER));
    }
  }

  public static Cell getTableCell(final String content) {
    return new Cell().add(content).setTextAlignment(TextAlignment.CENTER);
  }

  public static void addTableCells(List<Cell> cells, Table table) {
    cells.forEach(table::addCell);
  }

  public static void addLineSeparator(final Document fDocument) {
    fDocument.add(lineSeparatorElement());
  }

  public static  void addBlankCell(final Document document) {
    document.add(new Cell());
  }

  public static  void addCells(List<Cell> cells, final Document document) {
    cells.forEach(document::add);
  }
}
