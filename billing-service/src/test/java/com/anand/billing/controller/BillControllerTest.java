package com.anand.billing.controller;

import com.anand.billing.TestApplicationConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static com.anand.billing.utils.InvoiceUtils.createInvoice;
import static com.anand.billing.utils.InvoiceUtils.createInvoiceRequestBuilder;
import static com.anand.billing.utils.InvoiceUtils.readFromJson;

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
    String contentAsString = fMockMvc.perform(createInvoiceRequestBuilder(scriptJson)).andReturn().getResponse()
        .getContentAsString();
    System.out.println(contentAsString);
  }
}
