package com.odde.bbuddy.budget.domain;

import com.odde.bbuddy.budget.Repo.BudgetRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class Budgets {

    @Autowired
    BudgetRepo budgetRepo;

    public void add(com.odde.bbuddy.budget.domain.Budget budget){
        com.odde.bbuddy.budget.Repo.Budget budgetEntity = new com.odde.bbuddy.budget.Repo.Budget();
        BeanUtils.copyProperties(budget, budgetEntity);
        budgetRepo.save(budgetEntity);
    }

    public List findAll() {
        return budgetRepo.findAll();
    }

    public double getInRangeBudget(String beginDate, String endDate) {
        List<Budget> inRangeBudgetList = new ArrayList<>();
        List<com.odde.bbuddy.budget.Repo.Budget> fullBudgetList = new ArrayList<>();
        fullBudgetList = budgetRepo.findAll();
        String beginMonth = beginDate.substring(0,beginDate.lastIndexOf("-"));
        String endMonth = endDate.substring(0,endDate.lastIndexOf("-"));
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM");
        LocalDateTime beginMonthLdt = LocalDateTime.parse(beginMonth, df);
        LocalDateTime endMonthLdt = LocalDateTime.parse(endMonth, df);

        for (com.odde.bbuddy.budget.Repo.Budget budget:fullBudgetList){
            LocalDateTime budgetLdt = LocalDateTime.parse(budget.getMonth(), df);
            if(budgetLdt.isEqual(beginMonthLdt)|| budgetLdt.isAfter(beginMonthLdt)){
                Budget domain = new Budget();
                BeanUtils.copyProperties(budget, domain);
                inRangeBudgetList.add(domain);
            }

            if(budgetLdt.isEqual(endMonthLdt)|| budgetLdt.isAfter(endMonthLdt)){
                Budget domain = new Budget();
                BeanUtils.copyProperties(budget, domain);
                inRangeBudgetList.add(domain);
            }
        }

        DateTimeFormatter dfForCal = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate beginDateForCal = LocalDate.parse(beginDate,dfForCal);
        LocalDate endDateForCal = LocalDate.parse(endDate,dfForCal);

        return calculate(inRangeBudgetList, beginDateForCal,endDateForCal );
    }

    public double calculate(List<Budget> budgets, LocalDate startDate, LocalDate endDate) {
        double result = 0.0d;

        for (Budget budget : budgets) {
            result += budget.getAmount() * getPortion(budget, startDate, endDate);
        }

        return result;
    }

    private double getPortion(Budget budget, LocalDate startDate, LocalDate endDate) {

        int x = findX(budget, startDate);
        int y = finY(budget, endDate);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        //convert String to LocalDate
        LocalDate budgetMonthStart = LocalDate.parse(budget.getMonth() + "-01", formatter);

        int daysInMonth =  budgetMonthStart.lengthOfMonth();

        return (double)(daysInMonth - x - y) / daysInMonth;
    }

    private int findX(Budget budget, LocalDate startDate) {
        String budgetMonth = budget.getMonth();


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        //convert String to LocalDate
        LocalDate budgetMonthStart = LocalDate.parse(budgetMonth + "-01", formatter);


        if (startDate.isBefore(budgetMonthStart)) {
            return 0;
        }

        return startDate.getDayOfMonth() - 1;
    }

    private int finY(Budget budget, LocalDate endDate){

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
