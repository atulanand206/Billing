package com.anand.billing.service;

import com.anand.billing.model.components.Configuration;
import com.anand.billing.model.components.Page;
import com.anand.billing.model.dto.Permit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static com.anand.billing.utils.JacksonUtils.readJSON;
import static com.anand.billing.utils.TestUtils.readFromCsv;

public class BillService {

  public static void main(String[] args) {
    new BillService().convert();
  }

  public void convert() {
    try {
      String inputFileName = "test_ts.tsv";
      String outputFileName = "bill.pdf";
      convertTsvToPdf(inputFileName, outputFileName);
    } catch (Exception ignored) {

    }
  }

  void convertTsvToPdf(final String inputFileName, final String outputFileName) throws IOException {
    List<List<String>> lists = readFromCsv(inputFileName);
    List<Permit> permits = new ArrayList<>();
    for (int i = 1; i < lists.size(); i++) {
      List<String> item = lists.get(i);
      permits.add(new Permit(item));
    }
    permits.forEach(System.out::println);
    Configuration configuration = readJSON();
    List<Page> pages = PermitToBillConvertor.convert(permits, 1, configuration);
    pages.forEach(page -> page.getParticulars().forEach(
        System.out::println
    ));
    for (Page page : pages) {
      page.calculateTotals(configuration);
    }
    new BillWriter(configuration, outputFileName, pages).writeContent();
  }
}
