package com.volvocars.congestiiontaxcalculator.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TollFeeRule {
  private int hour;
  private int minuteStart;
  private int minuteEnd;
  private int feeValue;
}
