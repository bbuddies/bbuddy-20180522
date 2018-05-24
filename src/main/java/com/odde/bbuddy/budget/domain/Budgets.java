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

    private final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    BudgetRepo budgetRepo;

    @Autowired
    public Budgets(BudgetRepo budgetRepo) {
        this.budgetRepo = budgetRepo;
    }


    public void add(Budget budget) {
        com.odde.bbuddy.budget.Repo.Budget budgetEntity = new com.odde.bbuddy.budget.Repo.Budget();
        BeanUtils.copyProperties(budget, budgetEntity);
        budgetRepo.save(budgetEntity);
    }

    public List findAll() {
        return budgetRepo.findAll();
    }

    public double getInRangeBudget(String startDateStr, String endDateStr) {
        List<Budget> inRangeBudgetList = new ArrayList<>();
        List<com.odde.bbuddy.budget.Repo.Budget> fullBudgetList = budgetRepo.findAll();


        LocalDate startOfMonth = LocalDate.parse((startDateStr.substring(0, startDateStr.lastIndexOf("-")) + "-01"), df);
        LocalDate endOfMonth = LocalDate.parse((endDateStr.substring(0, endDateStr.lastIndexOf("-")) + "-" + LocalDate.parse(endDateStr, df).getDayOfMonth()), df);

        for (com.odde.bbuddy.budget.Repo.Budget budget : fullBudgetList) {
            LocalDate budgetOfMonth = LocalDate.parse(budget.getMonth() + "-01", df);
            if (shouldPutToList(startOfMonth, endOfMonth, budgetOfMonth)) {
                addToInRangeBudgetList(inRangeBudgetList, budget);
            }
        }

        LocalDate beginDateForCal = LocalDate.parse(startDateStr, df);
        LocalDate endDateForCal = LocalDate.parse(endDateStr, df);

        return budgetCalculator.calculate(inRangeBudgetList, beginDateForCal, endDateForCal);
    }

    private boolean shouldPutToList(LocalDate beginMonthLdt, LocalDate endMonthLdt, LocalDate budgetLdt) {
        return budgetLdt.isEqual(beginMonthLdt)
                || budgetLdt.isAfter(beginMonthLdt)
                || budgetLdt.isEqual(endMonthLdt)
                || budgetLdt.isBefore(endMonthLdt);
    }

    private void addToInRangeBudgetList(List<Budget> inRangeBudgetList, com.odde.bbuddy.budget.Repo.Budget budget) {
        Budget domain = new Budget();
        BeanUtils.copyProperties(budget, domain);
        inRangeBudgetList.add(domain);
    }

    public double calculate(List<Budget> budgets, LocalDate startDate, LocalDate endDate) {

        return budgetCalculator.calculate(budgets, startDate, endDate);
    }
}
