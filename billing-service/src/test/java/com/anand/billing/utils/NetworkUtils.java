package com.anand.billing.utils;

import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static com.anand.billing.utils.TestUtils.readFromJson;

public class NetworkUtils {

  private static final String INVOICE_ENDPOINT = "/bill/invoice";

  public static String createInvoice(final MockMvc fMockMvc, final String fileName)
      throws Exception {
    String scriptJson = readFromJson(fileName).replace("\n", "");
    return fMockMvc.perform(createInvoiceRequestBuilder(scriptJson))
        .andExpect(MockMvcResultMatchers.status().isOk())
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

  public static Stream<Arguments> singleArgumentStream(Object object) {
    return Stream.of(Arguments.of(object));
  }

  public static Stream<Arguments> doubleArgumentStream(Object object1, Object object2) {
    return Stream.of(Arguments.of(object1), Arguments.of(object2));
  }

}
