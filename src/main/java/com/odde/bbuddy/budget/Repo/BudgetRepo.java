package com.odde.bbuddy.budget.Repo;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface BudgetRepo extends Repository<Budget, Long> {

    List<Budget> findAll();

    void save(Budget budget);
}
