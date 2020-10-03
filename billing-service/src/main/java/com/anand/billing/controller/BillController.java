package com.anand.billing.controller;

import com.anand.billing.model.components.Bill;
import com.anand.billing.model.components.Page;
import com.anand.billing.service.BillWriter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bill")
public class BillController {

  @PostMapping(
      value = "/invoice",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Bill createInvoice(
      @RequestBody final Bill invoice) throws Exception {
    System.out.println(invoice.toString());
    for (Page page : invoice.getPages()) {
      page.calculateTotals(invoice.getConfiguration());
    }
    new BillWriter(invoice.getConfiguration(), "bill.pdf",
        invoice.getPages())
        .writeContent();
    return invoice;
  }
}