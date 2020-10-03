package com.anand.billing.service;

import com.anand.billing.constants.Constants;
import com.anand.billing.model.components.Configuration;
import com.anand.billing.model.components.Page;
import com.anand.billing.model.components.Particular;
import com.anand.billing.model.components.Target;
import com.anand.billing.model.components.Trip;
import com.anand.billing.model.dto.Permit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.text.WordUtils;

public class PermitToBillConvertor {

  public static List<Page> convert(
      final List<Permit> permits,
      final int invoiceBegin,
      final Configuration configuration) {
    permits.sort(Comparator.comparing(Permit::getIssuedOn));
    List<Page> pages = new ArrayList<>();
    Page page = null;
    int pageNo = invoiceBegin;
    for (Permit permit : permits) {
      Particular particular = convert(permit, configuration);
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

  public static Particular convert(
      final Permit permit,
      final Configuration configuration) {
    return new Particular(
        permit.getIssuedOn(), permit.getTruckRegNo(),
        permit.getPassNo(),
        "",
        permit.getMineralWt(),
        permit.getDestination(),
        convertTarget(permit),
        convertTrip(permit, configuration));
  }

  private static Trip convertTrip(
      final Permit permit,
      final Configuration configuration) {
    String from = configuration.getEntity().getFrom();
    String to = permit.getDestination();
    return new Trip(from, to);
  }

  public static Target convertTarget(final Permit permit) {
    String consignee = permit.getConsignee();
    String[] split = consignee.split("[(]");
    String[] props = split[0].split(" PROP ");
    String gstin = getGstin(split);
    String billTo = getCapitalized(props[0]);
    String destination = getCapitalized(permit.getDestination());
    return new Target(billTo, destination, gstin, Constants.STATE_NAME, Constants.STATE_CODE);
  }

  public static String getCapitalized(final String destination) {
    return WordUtils.capitalize(destination.replaceAll("[,.!?;:]", "$0 ").replaceAll("\\s+", " ").toLowerCase());
  }

  public static String getGstin(String[] split) {
    String gstin = "";
    if (split.length>1) {
      String[] split1 = split[1].split("[)]");
      gstin = split1[0];
    }
    return gstin;
  }
}
