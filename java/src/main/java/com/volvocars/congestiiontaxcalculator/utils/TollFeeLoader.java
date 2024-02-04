package com.volvocars.congestiiontaxcalculator.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.volvocars.congestiiontaxcalculator.model.TollFeeRule;

public class TollFeeLoader {
  private static final String CSV_FILE_PATH = "src/main/resources/tollfees.csv";

  public static List<TollFeeRule> loadTollFeeRules() {
    List<TollFeeRule> rules = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");
        if (values.length == 4) {
          TollFeeRule rule = TollFeeRule.builder()
              .hour(Integer.parseInt(values[0].trim()))
              .minuteStart(Integer.parseInt(values[1].trim()))
              .minuteEnd(Integer.parseInt(values[2].trim()))
              .feeValue(Integer.parseInt(values[3].trim()))
              .build();
          rules.add(rule);
        } else {
          //TODO Handle invalid row (e.g., log or throw exception)
        }
      }
    } catch (IOException e) {
      //TODO Handle IOException (e.g., file not found)
    }
    return rules;
  }
}