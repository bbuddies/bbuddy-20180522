package com.odde.bbuddy.budget.domain;

import com.odde.bbuddy.budget.Repo.BudgetRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
import java.util.List;

@Component
public class Budgets {

    @Autowired
    BudgetRepo budgetRepo;

    public void add(Budget budget){
        com.odde.bbuddy.budget.Repo.Budget budgetEntity = new com.odde.bbuddy.budget.Repo.Budget();
        BeanUtils.copyProperties(budget, budgetEntity);
        budgetRepo.save(budgetEntity);
    }

    public List findAll() {
        return budgetRepo.findAll();
    }

    public double calculate(List<Budget> budgets, LocalDate startDate, LocalDate endDate) {
        double result = 0.0d;

        for (Budget budget : budgets) {
            result += budget.getAmount() * getPortion(budget, startDate, endDate);
        }

        return result;
    }

    private double getPortion(Budget budget, LocalDate startDate, LocalDate endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        //convert String to LocalDate
        LocalDate firstDayOfBudgetMonth = findFirstDayOfBudgetMonth(budget, formatter);

        int startPortion = findStartPortion(firstDayOfBudgetMonth, startDate);
        int endPortion = findEndPortion(firstDayOfBudgetMonth, endDate);

        int daysInMonth =  firstDayOfBudgetMonth.lengthOfMonth();

        return (double)(daysInMonth - startPortion - endPortion) / daysInMonth;
    }

    private LocalDate findFirstDayOfBudgetMonth(Budget budget, DateTimeFormatter formatter) {
        return LocalDate.parse(budget.getMonth() + "-01", formatter);
    }

    private int findStartPortion(LocalDate budgetMonthStart, LocalDate startDate) {

        if (startDate.isBefore(budgetMonthStart)) {
            return 0;
        }

        return startDate.getDayOfMonth() - 1;
    }

    private int findEndPortion(LocalDate budgetMonthStart, LocalDate endDate){

        int lengthOfMonth = budgetMonthStart.lengthOfMonth();

        LocalDate budgetMonthEnd = budgetMonthStart.plusDays(lengthOfMonth - 1);

        if (endDate.isAfter(budgetMonthEnd)) return 0;

        return lengthOfMonth - endDate.getDayOfMonth();

    }
}
