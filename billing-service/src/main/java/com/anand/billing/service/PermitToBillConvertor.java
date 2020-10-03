package com.anand.billing.service;

import com.anand.billing.model.components.Page;
import com.anand.billing.model.components.Particular;
import com.anand.billing.model.dto.Permit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PermitToBillConvertor {

  public static List<Page> convert(final List<Permit> permits, final int invoiceBegin) {
    List<Permit> permitList = permits;
    Collections.sort(permitList, new Comparator<Permit>() {

      @Override
      public int compare(final Permit o1, final Permit o2) {
        return o1.getIssuedOn().compareTo(o2.getIssuedOn());
      }
    });
    List<Page> pages = new ArrayList<>();
    Page page = null;
    int pageNo = invoiceBegin;
    for (Permit permit : permitList) {
      Particular particular = convert(permit);
      if (page == null) {
        page = new Page(permit.getIssuedOn(), pageNo++);
      } else {
        if (page.getLatestParticular() != null && !page.canParticularBeAdded(particular)) {
          pages.add(page);
          page = new Page(permit.getIssuedOn(), pageNo++);
        }
      }
      page.addParticular(particular);
    }
    return pages;
  }

  public static Particular convert(final Permit permit) {
    return new Particular(
        permit.getIssuedOn(), permit.getTruckRegNo(),
        permit.getPermitNo(),
        "",
        permit.getMineralWt());
  }
}
