package com.volvocars.congestiiontaxcalculator.utils;

import com.volvocars.congestiiontaxcalculator.model.Car;
import com.volvocars.congestiiontaxcalculator.model.Motorbike;
import com.volvocars.congestiiontaxcalculator.model.Vehicle;
import org.springframework.util.StringUtils;

public class VehicleFactory {
  public static Vehicle create(String vehicle) {
    if(StringUtils.isEmpty(vehicle))
      return null;
    if (vehicle.equals(Car.class.getName())) {
      return new Car();
    } else if (vehicle.equals(Motorbike.class.getName()))  {
      return new Motorbike();
    } else {
      return null;
    }
  }
}
