package com.anand.billing.utils;

public class CalculationUtils {

  public static String getPercentage(final int value) {
    return String.format("%d%%", value);
  }

  public static String getPercentage(final double value) {
    return String.format("%.1f%%", value);
  }
}
