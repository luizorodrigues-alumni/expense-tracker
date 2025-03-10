package com.luiz.expensetracker.service;

import com.luiz.expensetracker.model.Expense;

import java.util.List;

public interface ExpenseTrackerService {
    public List<String> getAllExpenseCategories();
    public List<Expense> getAllExpenses();
    public List<Expense> getExpenseByDay(String date);
    public List<Expense> getExpenseByCategoryAndMonth(String category, String month);
    public Expense getExpenseById(Long id);
    public Expense addExpense(Expense expense);
    public void updateExpense(Long id, Expense expense);
    public void deleteExpense(Long id);
}
