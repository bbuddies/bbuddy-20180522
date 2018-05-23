package com.odde.bbuddy.budget.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class BudgetsTest {
    List<Budget> budgetList;
    Budgets budgets;

    @Before
    public void setUp() throws Exception {
        budgetList = new ArrayList<>(Arrays.asList(new Budget[] {
                new Budget("2018-07", 200),
                new Budget("2018-08", 300),
                new Budget("2018-09", 400)
        }));

        budgets = new Budgets();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void budgets_between_start_and_end() throws Exception {
        LocalDate startDate = LocalDate.of(2017, 1, 1);
        LocalDate endDate = LocalDate.of(2018, 12, 31);

        double sum = budgets.calculate(budgetList, startDate, endDate);

        assertEquals(900d, sum, 0.000001);
    }

    @Test
    public void budget_in_startDate() throws Exception {
        LocalDate startDate = LocalDate.of(2018, 7, 3);
        LocalDate endDate = LocalDate.of(2018, 9, 30);

        double actual = budgets.calculate(budgetList, startDate, endDate);

        assertEquals(200 * 29.0/31 + 300 + 400, actual, 0.000000000001);
    }

    @Test
    public void budgets_in_endDate() throws Exception {
        LocalDate startDate = LocalDate.of(2018, 7, 1);
        LocalDate endDate = LocalDate.of(2018, 9, 15);

        double sum = budgets.calculate(budgetList, startDate, endDate);

        assertEquals(700d, sum, 0.000001);
    }
}