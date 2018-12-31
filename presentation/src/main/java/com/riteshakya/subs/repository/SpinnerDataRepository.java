package com.riteshakya.subs.repository;

import com.riteshakya.subs.views.component.BaseSpinner;

import com.riteshakya.subs.views.component.BaseSpinner;
import com.riteshakya.domain.models.enums.Currency;
import com.riteshakya.domain.models.enums.Cycle;
import com.riteshakya.domain.models.enums.Duration;
import com.riteshakya.domain.models.enums.Reminder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ritesh Shakya
 */

public class SpinnerDataRepository {
    private static List<BaseSpinner> durationList;
    private static List<BaseSpinner> reminderList;
    private static List<BaseSpinner> baseSpinners;
    private static List<BaseSpinner> cycleList;

    public static List<BaseSpinner> getCycleList() {
        if (cycleList != null) return cycleList;
        cycleList = new ArrayList<>();
        for (Cycle cycle : Cycle.values())
            cycleList.add(new BaseSpinner(cycle.name(), cycle.toString()));
        return cycleList;
    }

    public static List<BaseSpinner> getDurationList() {
        if (durationList != null) return durationList;
        durationList = new ArrayList<>();
        for (Duration duration : Duration.values())
            durationList.add(new BaseSpinner(duration.name(), duration.toString()));
        return durationList;
    }

    public static List<BaseSpinner> getReminderList() {
        if (reminderList != null) return reminderList;
        reminderList = new ArrayList<>();
        for (Reminder reminder : Reminder.values())
            reminderList.add(new BaseSpinner(reminder.name(), reminder.toString()));
        return reminderList;
    }

    public static List<BaseSpinner> getCurrencyList() {
        if (baseSpinners != null) return baseSpinners;
        baseSpinners = new ArrayList<>();
        for (Currency currency : Currency.values())
            baseSpinners.add(new BaseSpinner(currency.name(), currency.toString()));
        return baseSpinners;
    }
}
