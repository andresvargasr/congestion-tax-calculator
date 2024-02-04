package com.volvocars.congestiiontaxcalculator.service;

import com.volvocars.congestiiontaxcalculator.model.TollFeeRule;
import com.volvocars.congestiiontaxcalculator.utils.TollFeeLoader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.volvocars.congestiiontaxcalculator.model.Vehicle;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CongestionTaxCalculator {

    private static Map<String, Integer> tollFreeVehicles = new HashMap<>();
    private static List<TollFeeRule> tollFeeRules;

    static {
        tollFreeVehicles.put("Motorbike", 0);
        tollFreeVehicles.put("Tractor", 1);
        tollFreeVehicles.put("Emergency", 2);
        tollFreeVehicles.put("Diplomat", 3);
        tollFreeVehicles.put("Foreign", 4);
        tollFreeVehicles.put("Military", 5);
        tollFeeRules = TollFeeLoader.loadTollFeeRules();
        System.out.println("<======> Loaded rules: ");
        tollFeeRules.stream().forEach(tollFeeRule -> System.out.println("<======> " + tollFeeRule));
    }
    
    public int getTax(Vehicle vehicle, Date[] dates)
    {
        Date intervalStart = dates[0];
        int totalFee = 0;

        for (int i = 0; i < dates.length ; i++) {
            Date date = dates[i];
            int nextFee = getTollFee(date, vehicle);
            int tempFee = getTollFee(intervalStart, vehicle);

            long diffInMillis = date.getTime() - intervalStart.getTime();
            long minutes = diffInMillis/1000/60;

            if (minutes <= 60)
            {
                if (totalFee > 0) totalFee -= tempFee;
                if (nextFee >= tempFee) tempFee = nextFee;
                totalFee += tempFee;
            }
            else
            {
                totalFee += nextFee;
            }
            intervalStart = dates[i];
        }                
      
        if (totalFee > 60) totalFee = 60;
        return totalFee;
    }

    private boolean isTollFreeVehicle(Vehicle vehicle) {
        if (vehicle == null) return false;
        String vehicleType = vehicle.getVehicleType();
        return tollFreeVehicles.containsKey(vehicleType);
    }

    public int getTollFee(Date date, Vehicle vehicle)
    {
        if (isTollFreeDate(date) || isTollFreeVehicle(vehicle)) return 0;

        int hour = date.getHours();
        int minute = date.getMinutes();
        return evaluateTollFeeRuleLoaded(hour, minute);
    }

    //TODO this methods will replace evaluateTollFeeRuleInMemory
    private static int evaluateTollFeeRuleLoaded(int hour, int minute) {
        Optional<TollFeeRule> foundFee = tollFeeRules.stream().filter(
            r -> r.getHour() == hour && (r.getMinuteStart() <= minute
                && r.getMinuteEnd() >= minute)).findFirst();
        return foundFee.isPresent()? foundFee.get().getFeeValue() : 0;
    }

    @Deprecated
    private static int evaluateTollFeeRuleInMemory(int hour, int minute) {
        if (hour == 6 && minute >= 0 && minute <= 29)
            return 8;
        else if (hour == 6 && minute >= 30 && minute <= 59)
            return 13;
        else if (hour == 7 && minute >= 0 && minute <= 59)
            return 18;
        else if (hour == 8 && minute >= 0 && minute <= 29)
            return 13;
        else if (hour >= 8 && hour <= 14 && minute >= 30 && minute <= 59)
            return 8;
        else if (hour == 15 && minute >= 0 && minute <= 29)
            return 13;
        else if (hour == 15 && minute >= 0 || hour == 16 && minute <= 59)
            return 18;
        else if (hour == 17 && minute >= 0 && minute <= 59)
            return 13;
        else if (hour == 18 && minute >= 0 && minute <= 29)
            return 8;
        else
            return 0;
    }

    private Boolean isTollFreeDate(Date date)
    {
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        int day = date.getDay() + 1;
        int dayOfMonth = date.getDate();

        if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) return true;

        if (year == 2013)
        {
            if ((month == 1 && dayOfMonth == 1) ||
                    (month == 3 && (dayOfMonth == 28 || dayOfMonth == 29)) ||
                    (month == 4 && (dayOfMonth == 1 || dayOfMonth == 30)) ||
                    (month == 5 && (dayOfMonth == 1 || dayOfMonth == 8 || dayOfMonth == 9)) ||
                    (month == 6 && (dayOfMonth == 5 || dayOfMonth == 6 || dayOfMonth == 21)) ||
                    (month == 7) ||
                    (month == 11 && dayOfMonth == 1) ||
                    (month == 12 && (dayOfMonth == 24 || dayOfMonth == 25 || dayOfMonth == 26 || dayOfMonth == 31)))
            {
                return true;
            }
        }
        return false;
    }
}
