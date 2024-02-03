package com.volvocars.congestiiontaxcalculator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.volvocars.congestiiontaxcalculator.model.Car;
import com.volvocars.congestiiontaxcalculator.model.Motorbike;
import java.util.Calendar;
import java.util.Date;

import com.volvocars.congestiiontaxcalculator.model.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CongestionTaxCalculatorTest {

  private CongestionTaxCalculator taxCalculator;
  private Vehicle vehicle;

  @BeforeEach
  void setUp() {
    taxCalculator = new CongestionTaxCalculator();
    vehicle = new Car();
  }

  @Test
  void shouldChargeCorrectTollFee() {
    Calendar cal = Calendar.getInstance();
    cal.set(2013, Calendar.MARCH, 4, 6, 0); // 4th March 2013 at 06:00
    Date date = cal.getTime();
    int fee = taxCalculator.getTollFee(date, vehicle);
    assertEquals(8, fee, "Fee should be 8 for time between 06:00 and 06:29");
  }

  @Test
  void shouldNotChargeTollFeeForTollFreeVehicle() {
    Vehicle tollFreeVehicle = new Motorbike();
    Calendar cal = Calendar.getInstance();
    cal.set(2013, Calendar.MARCH, 4, 6, 0);
    Date date = cal.getTime();
    int fee = taxCalculator.getTollFee(date, tollFreeVehicle);
    assertEquals(0, fee, "No fee should be charged for toll-free vehicles");
  }

  @Test
  void shouldNotChargeTollFeeOnTollFreeDate() {
    Calendar cal = Calendar.getInstance();
    cal.set(2013, Calendar.JULY, 1); // 1st July 2013, which is toll-free
    Date date = cal.getTime();
    int fee = taxCalculator.getTollFee(date, vehicle);
    assertEquals(0, fee, "No fee should be charged on toll-free dates");
  }

  @Test
  void shouldChargeMaximumFeeForMultiplePassages() {
    Calendar cal = Calendar.getInstance();
    cal.set(2013, Calendar.MARCH, 4, 6, 0);
    Date firstPassage = cal.getTime();
    cal.set(2013, Calendar.MARCH, 4, 6, 30);
    Date secondPassage = cal.getTime();
    cal.set(2013, Calendar.MARCH, 4, 7, 0);
    Date thirdPassage = cal.getTime();

    Date[] dates = { firstPassage, secondPassage, thirdPassage };
    int totalFee = taxCalculator.getTax(vehicle, dates);
    assertEquals(18, totalFee, "The total fee should not exceed the highest fee in a single hour");
  }

  //TODO Assume multiple passages throughout the day
  // Test to make sure that the daily cap of 60 is respected
  // Create Date objects for different times and add to an array
  // Call taxCalculator.getTax(vehicle, dates) and assert the result is capped at 60
  @Test
  void shouldCapFeeAtMaximumPerDay() {
  }

  // Add more test cases for edge cases and different scenarios
}