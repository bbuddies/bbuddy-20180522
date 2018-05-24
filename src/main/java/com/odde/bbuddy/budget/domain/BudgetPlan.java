package com.odde.bbuddy.budget.domain;

import com.odde.bbuddy.budget.repo.Budget;
import com.odde.bbuddy.budget.repo.BudgetRepository;

import java.util.List;

public class BudgetPlan {
    private final BudgetRepository repo;

    public BudgetPlan(BudgetRepository repo) {

        this.repo = repo;
    }

    public double query(Period period) {
        List<Budget> budgets = repo.findAll();
        if (!budgets.isEmpty()) {
            Budget budget = budgets.get(0);

            int daysBetween = period.getOverlappingDayCount(budget.getPeriod());
            return budget.getAmount() / budget.getDayCount() * daysBetween;
        }
        return 0;
    }

}
