package com.anand.billing.service;

import com.anand.billing.model.components.Configuration;
import com.anand.billing.model.components.Page;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.property.AreaBreakType;
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
import static com.anand.billing.utils.TotalUtils.getTotalInWords;

public class BillWriter {

  private final Configuration fConfiguration;
  private final Document fDocument;
  private Page fCurrentPage;

  private final List<Page> fPages;

  public BillWriter(
      final Configuration configuration,
      final String fileName,
      final List<Page> pages)
      throws IOException {
    fConfiguration = configuration;
    fPages = pages;
    PdfWriter writer = new PdfWriter(fileName);
    PdfDocument pdf = new PdfDocument(writer);
    fDocument = new Document(pdf);
  }

  public void writeContent() {
    for (Page page : fPages) {
      fCurrentPage = page;
      addPage();
    }
    fDocument.close();
  }

  private void addPage() {
    addPageHeader();
    addPageEntity();
    addPageInvoice();
    addPageTarget();
    addPageParticulars();
    addPageFooter();
    fDocument.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
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
    addCells(addInvoiceInfo(fCurrentPage), fDocument);
    addLineSeparator(fDocument);
    addBlankCell(fDocument);
  }

  private void addPageTarget() {
    addCells(addTarget(fCurrentPage), fDocument);
    fDocument.add(addTargetTable(fCurrentPage.getTarget()));
  }

  private void addPageParticulars() {
    fDocument.add(addParticulars(fConfiguration, fCurrentPage));
    addBlankCell(fDocument);
    addCells(getTotalInWords(fCurrentPage.getGrandTotal()), fDocument);
    addBlankCell(fDocument);
  }

  private void addPageFooter() {
    addBlankCell(fDocument);
    addCells(getSignatory(fConfiguration.getAuthorisedSignatory()), fDocument);
  }
}
