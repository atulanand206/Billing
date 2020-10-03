package com.anand.billing.utils;

import com.anand.billing.model.components.Configuration;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class JacksonUtils {

  public static Configuration readJSON() throws JsonParseException, JsonMappingException,
      IOException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(TestUtils.readFromJson("invoice.json").replace("\n", ""), Configuration.class);
  }
}
