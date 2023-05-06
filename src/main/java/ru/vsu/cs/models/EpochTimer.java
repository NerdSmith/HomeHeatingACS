package ru.vsu.cs.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.vsu.cs.annotations.Column;
import ru.vsu.cs.annotations.ForeignKey;
import ru.vsu.cs.annotations.Id;

import java.time.DayOfWeek;

import static ru.vsu.cs.utils.Constants.DAYS_IN_WEEK;
import static ru.vsu.cs.utils.Constants.DAY_CYCLE;


@AllArgsConstructor
@Getter
@Setter
public class EpochTimer implements Model {
    @Id
    private int id;
    @Column(name = "currTime")
    private int currTime;

    public EpochTimer(int id, int currTime) {
        this.id = id;
        this.currTime = currTime;
    }

    @Column(name = "environment")
    @ForeignKey(name = "environment_id", refTable = "environment")
    private int environment;

    public synchronized void incTime() {
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
