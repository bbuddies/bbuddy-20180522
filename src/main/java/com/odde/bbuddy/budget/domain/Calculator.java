package com.odde.bbuddy.budget.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Calculator {
    public Calculator() {
    }

    public double calculate(List<Budget> budgets, LocalDate startDate, LocalDate endDate) {
        double result = 0.0d;

        for (Budget budget : budgets) {
            result += budget.getAmount() * getPortion(budget, startDate, endDate);
        }

        return result;
    }

    double getPortion(Budget budget, LocalDate startDate, LocalDate endDate) {

        int x = findX(budget, startDate);
        int y = finY(budget, endDate);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        //convert String to LocalDate
        LocalDate budgetMonthStart = LocalDate.parse(budget.getMonth() + "-01", formatter);

        int daysInMonth = budgetMonthStart.lengthOfMonth();

        return (double) (daysInMonth - x - y) / daysInMonth;
    }

    int findX(Budget budget, LocalDate startDate) {
        String budgetMonth = budget.getMonth();


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        //convert String to LocalDate
        LocalDate budgetMonthStart = LocalDate.parse(budgetMonth + "-01", formatter);


        if (startDate.isBefore(budgetMonthStart)) {
            return 0;
        }

        return startDate.getDayOfMonth() - 1;
    }

    int finY(Budget budget, LocalDate endDate) {

        String budgetMonth = budget.getMonth();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        //convert String to LocalDate
        LocalDate budgetMonthStart = LocalDate.parse(budgetMonth + "-01", formatter);

        int lengthOfMonth = budgetMonthStart.lengthOfMonth();

        LocalDate budgetMonthEnd = LocalDate.parse(budgetMonth + "-" + lengthOfMonth, formatter);

        if (endDate.isAfter(budgetMonthEnd)) return 0;

        return lengthOfMonth - endDate.getDayOfMonth();

    }
}