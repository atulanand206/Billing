package com.anand.billing.utils;

import com.anand.billing.model.components.Entity;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.UnitValue;
import java.util.ArrayList;
import java.util.List;
import static com.anand.billing.utils.Strings.PROP;

public class EntityUtils {

  public static List<Cell> addEntityInfo(final Entity entity) {
    List<Cell> cells = new ArrayList<>();
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
}
