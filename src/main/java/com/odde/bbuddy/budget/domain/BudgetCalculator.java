package com.odde.bbuddy.budget.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BudgetCalculator {
    public BudgetCalculator() {
    }

    public double calculate(List<Budget> budgets, LocalDate startDate, LocalDate endDate) {
        double result = 0.0d;

        for (Budget budget : budgets) {
            result += budget.getAmount() * getPortion(budget, startDate, endDate);
        }

        return result;
    }

    double getPortion(Budget budget, LocalDate startDate, LocalDate endDate) {

        String budgetMonth = budget.getMonth();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //convert String to LocalDate
        LocalDate budgetMonthStart = LocalDate.parse(budgetMonth + "-01", formatter);


        int startDiff = findStartDiff(budgetMonthStart, startDate);
        int endDiff = findEndDiff(budgetMonthStart, endDate);


        int daysInMonth = budgetMonthStart.lengthOfMonth();

        return (double) (daysInMonth - startDiff - endDiff) / daysInMonth;
    }

    int findStartDiff(LocalDate budgetMonthStart, LocalDate startDate) {
        if (startDate.isBefore(budgetMonthStart)) {
            return 0;
        }

        return startDate.getDayOfMonth() - 1;
    }

    int findEndDiff(LocalDate budgetMonthStart, LocalDate endDate) {

        int lengthOfMonth = budgetMonthStart.lengthOfMonth();

        LocalDate budgetMonthEnd = budgetMonthStart.plusDays(lengthOfMonth - 1);

        if (endDate.isAfter(budgetMonthEnd)) return 0;

        return lengthOfMonth - endDate.getDayOfMonth();

    }
}