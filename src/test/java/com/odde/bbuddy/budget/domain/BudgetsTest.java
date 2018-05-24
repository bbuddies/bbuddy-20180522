package com.odde.bbuddy.budget.domain;

import com.odde.bbuddy.budget.Repo.BudgetRepo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BudgetsTest {
    List<Budget> budgetList;
    Budgets budgets;
    final double DELTA = 0.000000000001d;
    BudgetRepo budgetRepo = mock(BudgetRepo.class);

    @Before
    public void setUp() throws Exception {
        budgetList = new ArrayList<>(Arrays.asList(new Budget[] {
                new Budget("2018-08", 300),
                new Budget("2018-07", 200),
                new Budget("2018-09", 400)
        }));

        budgets = new Budgets(budgetRepo);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void budgets_between_start_and_end() throws Exception {
        LocalDate startDate = LocalDate.of(2017, 1, 1);
        LocalDate endDate = LocalDate.of(2018, 12, 31);

        double sum = budgets.calculate(budgetList, startDate, endDate);

        assertEquals(900d, sum, DELTA);
    }

    @Test
    public void budget_in_startDate() throws Exception {
        LocalDate startDate = LocalDate.of(2018, 7, 3);
        LocalDate endDate = LocalDate.of(2018, 9, 30);

        double actual = budgets.calculate(budgetList, startDate, endDate);

        assertEquals(200 * 29.0/31 + 300 + 400, actual, DELTA);
    }

    @Test
    public void budgets_in_endDate() throws Exception {
        LocalDate startDate = LocalDate.of(2018, 7, 1);
        LocalDate endDate = LocalDate.of(2018, 9, 15);

        double actual = budgets.calculate(budgetList, startDate, endDate);

        assertEquals(700d, actual, DELTA);
    }

    @Test
    public void budget_in_same_month() throws Exception {
        LocalDate startDate = LocalDate.of(2018, 9, 10);
        LocalDate endDate = LocalDate.of(2018, 9, 24);
        budgetList = new ArrayList<>(Arrays.asList(new Budget[] {
                new Budget("2018-09", 300),
        }));
        double actual = budgets.calculate(budgetList, startDate, endDate);

        assertEquals(150d, actual, DELTA);
    }

    @Test
    public void one_budget_before_end_date() throws Exception {
        LocalDate startDate = LocalDate.of(2018, 9, 16);
        LocalDate endDate = LocalDate.of(2018, 10, 1);
        budgetList = new ArrayList<>(Arrays.asList(new Budget[] {
                new Budget("2018-09", 300),
        }));
        double actual = budgets.calculate(budgetList, startDate, endDate);

        assertEquals(150d, actual, DELTA);
    }

    @Test
    public void one_budget_after_begin_date() throws Exception {
        LocalDate startDate = LocalDate.of(2018, 8, 16);
        LocalDate endDate = LocalDate.of(2018, 9, 15);
        budgetList = new ArrayList<>(Arrays.asList(new Budget[] {
                new Budget("2018-09", 300),
        }));
        double actual = budgets.calculate(budgetList, startDate, endDate);

        assertEquals(150d, actual, DELTA);
    }

    @Test
    public void one_budget_between_dates() throws Exception {
        LocalDate startDate = LocalDate.of(2018, 8, 16);
        LocalDate endDate = LocalDate.of(2018, 10, 11);
        budgetList = new ArrayList<>(Arrays.asList(new Budget[] {
                new Budget("2018-09", 300),
        }));
        double actual = budgets.calculate(budgetList, startDate, endDate);

        assertEquals(300d, actual, DELTA);
    }

    @Test
    public void one_budget() throws Exception {
        LocalDate startDate = LocalDate.of(2018, 9, 1);
        LocalDate endDate = LocalDate.of(2018, 9, 1);
        budgetList = new ArrayList<>(Arrays.asList(new Budget[] {
                new Budget("2018-09", 300),
        }));
        double actual = budgets.calculate(budgetList, startDate, endDate);

        assertEquals(300.0 * 1/30, actual, DELTA);
    }

    @Test
    public void get_in_range_budget() throws Exception {
        List<com.odde.bbuddy.budget.Repo.Budget> budgetList = new ArrayList<>();
        when(budgetRepo.findAll()).thenReturn(budgetList);
        double result = budgets.getInRangeBudget("2018-01-01","2019-01-31");
        assertEquals(0.0d, result, DELTA);
    }
}