package com.odde.bbuddy.budget.domain;

import com.odde.bbuddy.budget.repo.Budget;
import com.odde.bbuddy.budget.repo.BudgetRepository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class BudgetPlan {
    private final BudgetRepository repo;

    public BudgetPlan(BudgetRepository repo) {

        this.repo = repo;
    }

    public double query(LocalDate start, LocalDate end) {
        List<Budget> budgets = repo.findAll();
        if(!budgets.isEmpty()){
            if(start.equals(end)){
                YearMonth yearMonth = YearMonth.parse(budgets.get(0).getMonth());
                return budgets.get(0).getAmount() / yearMonth.lengthOfMonth();
            }
            return budgets.get(0).getAmount();
        }
        return 0;
    }
}
