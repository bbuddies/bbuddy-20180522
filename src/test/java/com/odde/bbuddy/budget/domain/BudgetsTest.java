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

    @Before
    public void setUp() throws Exception {
        budgetList = new ArrayList<>(Arrays.asList(new Budget[] {
                new Budget("2018-07", 200),
                new Budget("2018-08", 300),
                new Budget("2018-09", 400)
        }));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void budgets_between_start_and_end() throws Exception {
        Budgets budgets = new Budgets();
        LocalDate startDate = LocalDate.of(2017, 1, 1);
        LocalDate endDate = LocalDate.of(2018, 12, 31);

        double sum = budgets.calculate(budgetList, startDate, endDate);

        assertEquals(900d, sum, 0.000001);
    }
}