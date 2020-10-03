package com.anand.billing.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TestUtils {

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

  public static List<List<String>> readFromTsv(final InputStream systemResourceAsStream) throws IOException {
    List<List<String>> records = new ArrayList<>();
    if (systemResourceAsStream != null) {
      final InputStreamReader templateResource = new InputStreamReader(systemResourceAsStream);
      try (BufferedReader br = new BufferedReader(templateResource)) {
        String line;
        while ((line = br.readLine()) != null) {
          String[] values = line.split("\t");
          records.add(Arrays.asList(values));
        }
      }
    }
    return records;
  }

  public static List<List<String>> readFromCsv(final String fileName) throws IOException {
    InputStream systemResourceAsStream = ClassLoader.getSystemResourceAsStream(fileName);
    return readFromTsv(systemResourceAsStream);
  }
}
