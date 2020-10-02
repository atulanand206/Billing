package com.anand.billing.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class InvoiceUtils {

  private static final String INVOICE_ENDPOINT = "/bill/invoice";

  public static String createInvoice(final MockMvc fMockMvc, final String fileName) throws Exception {
    String scriptJson = readFromJson(fileName).replace("\n", "");
    return fMockMvc.perform(createInvoiceRequestBuilder(scriptJson))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();
  }

  public static RequestBuilder createInvoiceRequestBuilder(final String requestBody) {
    final var reqBuilder = MockMvcRequestBuilders.post(INVOICE_ENDPOINT)
        .contentType(MediaType.APPLICATION_JSON);
    if (null != requestBody) {
      reqBuilder.content(requestBody);
    }
    return reqBuilder;
  }

  public static String readFromJson(final String fileName) {
    try (
        final InputStreamReader templateResource = new InputStreamReader(
            Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(fileName)));
        final BufferedReader bufferedTemplateReader = new BufferedReader(templateResource)) {
      return bufferedTemplateReader.lines().collect(Collectors.joining("\n"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }
}
