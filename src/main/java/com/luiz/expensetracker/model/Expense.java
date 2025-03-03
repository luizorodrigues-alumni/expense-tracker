package com.luiz.expensetracker.model;

import lombok.Data;

@Data
public class Expense {
    private Long id;
    private Integer expenseType;
    private String date;
    private double amount;
    private String category;
    private String account;
    private String note;
}
