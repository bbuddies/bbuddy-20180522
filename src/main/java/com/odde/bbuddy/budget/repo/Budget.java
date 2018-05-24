package com.odde.bbuddy.budget.repo;

import com.odde.bbuddy.budget.domain.Period;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.YearMonth;

@AllArgsConstructor
@Getter
public class Budget {
    private String month;
    private long amount;

    public YearMonth getYearMonth() {
        return YearMonth.parse(getMonth());
    }

    public LocalDate getStartOfBudget() {
        return getYearMonth().atDay(1);
    }

    public LocalDate getEndOfBudget() {
        return getYearMonth().atEndOfMonth();
    }

    public int getDayCount() {
        return getYearMonth().lengthOfMonth();
    }

    public Period getPeriod() {
        return new Period(getStartOfBudget(), getEndOfBudget());
    }

    public double getOverlappingAmount(Period period) {
        return getAmount() / getDayCount() * period.getOverlappingDayCount(getPeriod());
    }
}
