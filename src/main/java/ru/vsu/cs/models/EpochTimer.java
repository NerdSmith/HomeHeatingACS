package ru.vsu.cs.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;


@AllArgsConstructor
@Getter
@Setter
public class EpochTimer {
    private int id;
    private int currTime;

    private final int DAY_CYCLE = 1000;
    private final int DAYS_IN_WEEK = 7;

    public void incTime() {
        currTime = ++currTime % (DAY_CYCLE * DAYS_IN_WEEK);
    }

    public DayOfWeek getWeekday() {
        int weekdayBase0 = currTime / DAY_CYCLE;
        return DayOfWeek.of(weekdayBase0 + 1);
    }

    public Daytime getDaytime() {
        int daytime = currTime % DAY_CYCLE;
        if (daytime < DAY_CYCLE / 4) {
            return Daytime.NIGHT;
        }
        else if (daytime > 3.0 / 4 * DAY_CYCLE) {
            return Daytime.NIGHT;
        }
        else {
            return Daytime.DAY;
        }
    }
}
