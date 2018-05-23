package com.odde.bbuddy.budget.controller;


import com.odde.bbuddy.budget.domain.Budget;
import com.odde.bbuddy.budget.domain.Budgets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
public class BudgetController {

    @Autowired
    private Budgets budgets;

    @GetMapping("budgets/add")
    public String index() {
        return "budgets/add";
    }


    @PostMapping("budgets/add")
    public String listBudget(Budget budget, Model model) {
        budgets.add(budget);

        List<Budget> budgetList = budgets.findAll();
        log.info(budget.getMonth());
        log.info(String.valueOf(budget.getAmount()));
        model.addAttribute("Budgets", budgetList);
        return "budgets/list";
    }

}
