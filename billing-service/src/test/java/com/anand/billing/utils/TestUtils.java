package com.anand.billing.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestUtils {

  private static final String INVOICE_ENDPOINT = "/bill/invoice";

  public static String createInvoice(final MockMvc fMockMvc, final String fileName)
      throws Exception {
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

  public static List<List<String>> readFromCsv(final String fileName) throws IOException {
    List<List<String>> records = new ArrayList<>();
    final InputStreamReader templateResource = new InputStreamReader(
        Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(fileName)));
    try (BufferedReader br = new BufferedReader(templateResource)) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] values = line.split("\t");
        records.add(Arrays.asList(values));
      }
    }
    return records;
  }

  public static Stream<Arguments> singleArgumentStream(Object object) {
    return Stream.of(Arguments.of(object));
  }

  public static Stream<Arguments> doubleArgumentStream(Object object1, Object object2) {
    return Stream.of(Arguments.of(object1), Arguments.of(object2));
  }
}
