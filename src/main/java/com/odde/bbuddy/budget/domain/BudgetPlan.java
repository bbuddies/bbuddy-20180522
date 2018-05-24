package com.odde.bbuddy.budget.domain;

import com.odde.bbuddy.budget.repo.Budget;
import com.odde.bbuddy.budget.repo.BudgetRepository;

import java.time.LocalDate;
import java.util.List;

public class BudgetPlan {
    private final BudgetRepository repo;

    public BudgetPlan(BudgetRepository repo) {

        this.repo = repo;
    }

    public double query(LocalDate start, LocalDate end) {
        List<Budget> budgets = repo.findAll();
        if (!budgets.isEmpty()) {
            Budget budget = budgets.get(0);
            LocalDate startOfBudget = budget.getStartOfBudget();
            LocalDate endOfBudget = budget.getEndOfBudget();

            LocalDate startOfOverlapping = start.isAfter(startOfBudget) ? start : startOfBudget;
            LocalDate endOfOverlapping = end.isBefore(endOfBudget) ? end : endOfBudget;

            int daysBetween = startOfOverlapping.until(endOfOverlapping).getDays()+ 1;
            return budget.getAmount() / budget.getDayCount() * daysBetween;
        }
        return 0;
    }

}
