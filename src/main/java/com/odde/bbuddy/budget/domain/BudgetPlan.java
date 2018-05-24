package com.odde.bbuddy.budget.domain;

import com.odde.bbuddy.budget.repo.BudgetRepository;

public class BudgetPlan {
    private final BudgetRepository repo;

    public BudgetPlan(BudgetRepository repo) {
        this.repo = repo;
    }

    public double query(Period period) {
        return repo.findAll().stream()
                .mapToDouble(budget -> budget.getOverlappingAmount(period))
                .sum();
    }

}
