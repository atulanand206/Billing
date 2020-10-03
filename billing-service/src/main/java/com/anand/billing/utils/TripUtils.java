package com.anand.billing.utils;

import com.anand.billing.model.components.Trip;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.property.TextAlignment;
import static com.anand.billing.utils.Strings.FROM_TO;

public class TripUtils {
  public static Cell getFromToCell(final Trip tripDetails) {
    return new Cell(1, 5)
        .add(String.format(FROM_TO, tripDetails.getFrom(), tripDetails.getTo()))
        .setTextAlignment(TextAlignment.LEFT)
        .setBorder(Border.NO_BORDER);
  }
}
