package com.odde.bbuddy.budget.domain;

import com.odde.bbuddy.budget.Repo.BudgetRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class Budgets {

    private final BudgetCalculator budgetCalculator = new BudgetCalculator();
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

    public double getInRangeBudget(String beginDate, String endDate) {
        List<Budget> inRangeBudgetList = new ArrayList<>();
        List<com.odde.bbuddy.budget.Repo.Budget> fullBudgetList = new ArrayList<>();
        fullBudgetList = budgetRepo.findAll();
        //String beginMonth = beginDate.substring(0,beginDate.lastIndexOf("-"));
        //String endMonth = endDate.substring(0,endDate.lastIndexOf("-"));
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate beginMonthLdt = LocalDate.parse(beginDate, df);
        LocalDate endMonthLdt = LocalDate.parse(endDate, df);

        for (com.odde.bbuddy.budget.Repo.Budget budget:fullBudgetList){
            //DateTimeFormatter df2 = DateTimeFormatter.ofPattern("yyyy-MM");
            LocalDate budgetLdt = LocalDate.parse(budget.getMonth()+"-01", df);
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

        return budgetCalculator.calculate(inRangeBudgetList, beginDateForCal, endDateForCal);
    }

    public double calculate(List<Budget> budgets, LocalDate startDate, LocalDate endDate) {

        return budgetCalculator.calculate(budgets, startDate, endDate);
    }

    private double getPortion(Budget budget, LocalDate startDate, LocalDate endDate) {


        //convert String to LocalDate

        return budgetCalculator.getPortion(budget, startDate, endDate);
    }

    private int findX(Budget budget, LocalDate startDate) {


        //convert String to LocalDate


        return budgetCalculator.findStartDiff(budget, startDate);
    }

    private int finY(Budget budget, LocalDate endDate){

        //convert String to LocalDate

        return budgetCalculator.findEndDiff(budget, endDate);
    }
}
