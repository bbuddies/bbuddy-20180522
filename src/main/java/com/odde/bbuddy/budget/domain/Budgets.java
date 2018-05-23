package com.odde.bbuddy.budget.domain;

import com.odde.bbuddy.budget.Repo.BudgetRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        List<com.odde.bbuddy.budget.Repo.Budget> inRangeBudgetList = new ArrayList<>();
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
                inRangeBudgetList.add(budget);
            }

            if(budgetLdt.isEqual(endMonthLdt)|| budgetLdt.isAfter(endMonthLdt)){
                inRangeBudgetList.add(budget);
            }

            // call


        }
        double calResult = 0;
        return calResult;
    }
}
