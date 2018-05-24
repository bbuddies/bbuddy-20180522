package com.odde.bbuddy.budget.repo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Budget {
    private String month;
    private long amount;
}
