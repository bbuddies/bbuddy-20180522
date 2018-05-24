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

    public double query(Period period) {
        List<Budget> budgets = repo.findAll();
        if (!budgets.isEmpty()) {
            Budget budget = budgets.get(0);

            int daysBetween = getOverlappingDayCount(period, budget);
            return budget.getAmount() / budget.getDayCount() * daysBetween;
        }
        return 0;
    }

    private int getOverlappingDayCount(Period period, Budget budget) {
        LocalDate startOfOverlapping = period.getStart().isAfter(budget.getStartOfBudget()) ? period.getStart() : budget.getStartOfBudget();
        LocalDate endOfOverlapping = period.getEnd().isBefore(budget.getEndOfBudget()) ? period.getEnd() : budget.getEndOfBudget();

        return startOfOverlapping.until(endOfOverlapping).getDays()+ 1;
    }

}
