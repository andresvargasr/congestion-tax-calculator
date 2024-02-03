package com.volvocars.congestiiontaxcalculator.controller;

import com.volvocars.congestiiontaxcalculator.model.Vehicle;
import com.volvocars.congestiiontaxcalculator.service.CongestionTaxCalculator;
import com.volvocars.congestiiontaxcalculator.utils.VehicleFactory;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8088")
@RestController
@RequestMapping("/congestion-tax-calculator")
@Slf4j
public class CongestionTaxApi {

  @Autowired
  CongestionTaxCalculator congestionTaxCalculator;
  @GetMapping
  public ResponseEntity<Integer> getTax(
      @RequestParam("vehicle") String vehicle,
      @RequestParam("dates") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date[] dates
  ) {
    Vehicle vehicleToCalculate = VehicleFactory.create(vehicle);
    try {
      int tax =  congestionTaxCalculator.getTax(vehicleToCalculate,dates);
      return new ResponseEntity<>(tax, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
