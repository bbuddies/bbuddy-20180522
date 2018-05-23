package com.odde.bbuddy.budget.Repo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name="budget")
public class Budget {
    @Id
    @GeneratedValue
    private long id;

    private String month;
    private int amount;
}
