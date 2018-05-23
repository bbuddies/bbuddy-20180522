package com.odde.bbuddy.budget.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BudgetController {


    @GetMapping("budgets/add")
    public String index() {
        return "budgets/add";
    }


    @PostMapping("budgets/add")
    public String listBudget() {




        return "budgets/list";
    }

}
