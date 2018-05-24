package com.odde.bbuddy.budget.domain;

import java.time.LocalDate;

public class Period {
    private final LocalDate start;
    private final LocalDate end;

    public Period(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public int getOverlappingDayCount(Period another) {
        LocalDate startOfOverlapping = start.isAfter(another.start) ? start : another.start;
        LocalDate endOfOverlapping = end.isBefore(another.end) ? end : another.end;
        if (startOfOverlapping.isAfter(endOfOverlapping)){
            return 0;
        }

        return startOfOverlapping.until(endOfOverlapping).getDays()+ 1;
    }
}
