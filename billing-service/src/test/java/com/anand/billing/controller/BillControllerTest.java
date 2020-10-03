package com.anand.billing.controller;

import com.anand.billing.TestApplicationConfiguration;
import com.anand.billing.model.dto.Permit;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static com.anand.billing.utils.TestUtils.createInvoiceRequestBuilder;
import static com.anand.billing.utils.TestUtils.readFromCsv;
import static com.anand.billing.utils.TestUtils.readFromJson;

@SpringBootTest(
    properties = "spring.main.allow-bean-definition-overriding=true",
    classes = {TestApplicationConfiguration.class})
@WebAppConfiguration
@AutoConfigureMockMvc
public class BillControllerTest {

  @Autowired
  private WebApplicationContext fAppContext;
  protected MockMvc fMockMvc;

  @BeforeEach
  void setUp() {
    fMockMvc = MockMvcBuilders.webAppContextSetup(fAppContext).build();
  }

  @Test
  void testCreateInvoice() throws Exception {
    String scriptJson = readFromJson("invoice.json").replace("\n", "");
    String particularJson = readFromJson("particular.json").replace("\n", "");
    String particularsJson = list(particularJson);
    String billJson = bill(scriptJson, particularsJson);
    String contentAsString =
        fMockMvc.perform(createInvoiceRequestBuilder(billJson)).andReturn().getResponse()
            .getContentAsString();
    System.out.println(contentAsString);
  }

  private String bill(final String scriptJson, final String particularsJson) {
    return String.format("{ \"configuration\":%s, \"particulars\":%s}", scriptJson, particularsJson);
  }

  private String list(final String particularJson) {
    StringBuilder res = new StringBuilder("[");
    int size = 10;
    for (int i = 0; i < size; i++) {
      res.append(particularJson);
      if (i != size - 1) {
        res.append(",");
      }
    }
    res.append("]");
    return res.toString();
  }

  @Test
  void testReadFromCsv() throws Exception {
    List<List<String>> lists = readFromCsv("test_ts.tsv");
    List<Permit> permits = new ArrayList<>();
    for (int i = 1; i < lists.size(); i++) {
      List<String> item = lists.get(i);
      permits.add(new Permit(item));
    }
    permits.forEach(System.out::println);
  }
}
