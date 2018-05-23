package com.odde.bbuddy.acceptancetest.steps;

import com.odde.bbuddy.acceptancetest.data.Budget;
import com.odde.bbuddy.acceptancetest.driver.UiDriver;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BudgetSteps {

    @Autowired
    UiDriver uiDriver;

    @When("^input month \"([^\"]*)\" and amount \"([^\"]*)\"$")
    public void input_and_budget_s(String month, String amount) throws Throwable {


        uiDriver.navigateTo("/budgets/add");

        uiDriver.inputTextByName(month, "month");
        uiDriver.inputTextByName(amount, "amount");

        uiDriver.clickByText("save");

    }

    @Then("^show month and amount$")
    public void show_and(List<Budget> budgets) throws Throwable {

        uiDriver.waitForTextPresent(budgets.get(0).getMonth());
        uiDriver.waitForTextPresent(budgets.get(0).getAmount());
    }
}
