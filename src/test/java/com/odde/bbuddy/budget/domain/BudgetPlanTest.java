package com.odde.bbuddy.budget.domain;

import com.odde.bbuddy.budget.repo.Budget;
import com.odde.bbuddy.budget.repo.BudgetRepository;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BudgetPlanTest {
    BudgetRepository repo = mock(BudgetRepository.class);
    BudgetPlan plan = new BudgetPlan(repo);

    @Test
    public void no_budget() {
        givenBudgets();
        assertAmount(0, LocalDate.of(2018, 5, 3), LocalDate.of(2018, 5, 30));
    }

    @Test
    public void query_whole_month() {
        givenBudgets(new Budget("2018-05", 310));
        assertAmount(310, LocalDate.of(2018, 5, 1), LocalDate.of(2018, 5, 31));
    }

    @Test
    public void query_whole_month_of_Jun() {
        givenBudgets(new Budget("2018-06", 300));
        assertAmount(300,
                LocalDate.of(2018, 6, 1),
                LocalDate.of(2018, 6, 30));
    }

    @Test
    public void query_1_day() {
        givenBudgets(new Budget("2018-05", 310));
        assertAmount(10,
                LocalDate.of(2018, 5, 1),
                LocalDate.of(2018, 5, 1));
    }

    @Test
    public void query_1_day_with_different_amount() {
        givenBudgets(new Budget("2018-05", 31));
        assertAmount(1,
                LocalDate.of(2018, 5, 1),
                LocalDate.of(2018, 5, 1));
    }

    @Test
    public void query_1_day_in_Jun() {
        givenBudgets(new Budget("2018-06", 30));
        assertAmount(1,
                LocalDate.of(2018, 6, 1),
                LocalDate.of(2018, 6, 1));
    }

    private void assertAmount(int expected, LocalDate start, LocalDate end) {
        assertEquals(expected, plan.query(start, end), 0.1);
    }

    private void givenBudgets(Budget... budgets) {
        when(repo.findAll()).thenReturn(Arrays.asList(budgets));
    }
}
