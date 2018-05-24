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

    public double getInRangeBudget(String beginDate, String endDate) {
        List<Budget> inRangeBudgetList = new ArrayList<>();
        List<com.odde.bbuddy.budget.Repo.Budget> fullBudgetList = budgetRepo.findAll();

        String bd = beginDate.substring(0, beginDate.lastIndexOf("-")) + "-01";

        LocalDate beginMonthLdt = LocalDate.parse(bd, df);
        LocalDate endDateLdt = LocalDate.parse(endDate, df);
        
        String ed = endDate.substring(0, endDate.lastIndexOf("-")) + "-" + endDateLdt.getDayOfMonth();
        LocalDate endMonthLdt = LocalDate.parse(ed, df);

        for (com.odde.bbuddy.budget.Repo.Budget budget : fullBudgetList) {
            LocalDate budgetLdt = LocalDate.parse(budget.getMonth() + "-01", df);
            if (shouldPutToList(beginMonthLdt, endMonthLdt, budgetLdt)) {
                addToInRangeBudgetList(inRangeBudgetList, budget);
            }
        }

        LocalDate beginDateForCal = LocalDate.parse(beginDate, df);
        LocalDate endDateForCal = LocalDate.parse(endDate, df);

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
