package com.odde.bbuddy.budget.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Budget {
    private String month;
    private int amount;

    private String beginDate;
    private String endDate;
    private double sum;
}
