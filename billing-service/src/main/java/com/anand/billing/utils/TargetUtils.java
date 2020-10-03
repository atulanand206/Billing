package com.anand.billing.utils;

import com.anand.billing.model.components.Page;
import com.anand.billing.model.components.Target;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import java.util.ArrayList;
import java.util.List;
import static com.anand.billing.constants.Constants.CODE;
import static com.anand.billing.constants.Constants.GSTIN;
import static com.anand.billing.constants.Constants.MS;
import static com.anand.billing.constants.Constants.STATE;

public class TargetUtils {

  public static List<Cell> addTarget(final Page page) {
    Target target = page.getTarget();
    List<Cell> cells = new ArrayList<>();
    Cell targetName = new Cell()
        .setTextAlignment(TextAlignment.CENTER)
        .setFontSize(18)
        .add(String.format(MS, target != null ? target.getBillTo() : ""));
    Cell targetAddress = new Cell()
        .setTextAlignment(TextAlignment.CENTER)
        .add(target != null ? target.getAddress() : "");
//    cells.add(getFromToCell(page.getTripDetails()));
    cells.add(targetName);
    cells.add(targetAddress);
    return cells;
  }

  public static Table addTargetTable(final Target target) {
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
}
