package com.anand.billing.service;

import com.anand.billing.model.components.Configuration;
import com.anand.billing.model.components.Page;
import com.anand.billing.model.components.Particular;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import java.io.IOException;
import java.util.List;
import static com.anand.billing.utils.EntityUtils.addEntityInfo;
import static com.anand.billing.utils.HeaderUtils.addHeader;
import static com.anand.billing.utils.HeaderUtils.addHeaderInfo;
import static com.anand.billing.utils.HeaderUtils.getSignatory;
import static com.anand.billing.utils.ITextUtils.addBlankCell;
import static com.anand.billing.utils.ITextUtils.addCells;
import static com.anand.billing.utils.ITextUtils.addLineSeparator;
import static com.anand.billing.utils.InvoiceUtils.addInvoiceInfo;
import static com.anand.billing.utils.InvoiceUtils.addParticulars;
import static com.anand.billing.utils.TargetUtils.addTarget;
import static com.anand.billing.utils.TargetUtils.addTargetTable;
import static com.anand.billing.utils.TotalUtils.getTotal;
import static com.anand.billing.utils.TotalUtils.getTotalInWords;
import static com.anand.billing.utils.TotalUtils.grandTotal;

public class BillWriter {

  private final Configuration fConfiguration;
  private final Document fDocument;
  private final List<Particular> fParticulars;
  private final Page fPage;

  public BillWriter(
      final Configuration configuration,
      final String fileName,
      final List<Particular> particulars,
      final Page page)
      throws IOException {
    fConfiguration = configuration;
    fParticulars = particulars;
    page.setTotal(getTotal(configuration.getRates(), particulars));
    page.setGrandTotal(grandTotal(configuration.getRates(), page.getTotal()));
    fPage = page;
    PdfWriter writer = new PdfWriter(fileName);
    PdfDocument pdf = new PdfDocument(writer);
    fDocument = new Document(pdf);
  }

  public void writeContent() {
    addPageHeader();
    addPageEntity();
    addPageInvoice();
    addPageTarget();
    addPageParticulars();
    addPageFooter();
    fDocument.close();
  }

  private void addPageHeader() {
    addCells(addHeader(), fDocument);
    fDocument.add(addHeaderInfo(fConfiguration.getEntity()));
    addLineSeparator(fDocument);
  }

  private void addPageEntity() {
    addCells(addEntityInfo(fConfiguration.getEntity()), fDocument);
    addLineSeparator(fDocument);
  }

  private void addPageInvoice() {
    addCells(addInvoiceInfo(fPage), fDocument);
    addLineSeparator(fDocument);
    addBlankCell(fDocument);
  }

  private void addPageTarget() {
    addCells(addTarget(fConfiguration.getTarget()), fDocument);
    fDocument.add(addTargetTable(fConfiguration.getTarget()));
  }

  private void addPageParticulars() {
    fDocument.add(addParticulars(fConfiguration, fParticulars, fPage));
    addBlankCell(fDocument);
    addCells(getTotalInWords(fPage.getGrandTotal()), fDocument);
    addBlankCell(fDocument);
  }

  private void addPageFooter() {
    addBlankCell(fDocument);
    addCells(getSignatory(fConfiguration.getAuthorisedSignatory()), fDocument);
  }
}
