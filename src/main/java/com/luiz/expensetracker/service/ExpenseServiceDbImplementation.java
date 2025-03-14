package com.luiz.expensetracker.service;

import com.luiz.expensetracker.exceptions.ExpenseBodyException;
import com.luiz.expensetracker.exceptions.ExpenseNotFoundException;
import com.luiz.expensetracker.exceptions.NoCategoriesFoundException;
import com.luiz.expensetracker.model.Expense;
import com.luiz.expensetracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("db")
public class ExpenseServiceDbImplementation implements ExpenseTrackerService{
    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public List<String> getAllExpenseCategories() {
        List<String> categories = expenseRepository.findDistinctByCategory();
        if (categories.isEmpty()) {
            throw new NoCategoriesFoundException("No categories Found");
        }
        return categories;
    }

    @Override
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @Override
    public List<Expense> getExpenseByDay(String date) {
        return expenseRepository.findByDate(date);
    }

    @Override
    public List<Expense> getExpenseByCategoryAndMonth(String category, String month) {
        return expenseRepository.findByCategoryIgnoreCaseAndDateStartingWith(category, month);
    }

    @Override
    public Expense getExpenseById(Long id) {
        Expense filteredExpense = expenseRepository.findById(id).orElse(null);
        if (filteredExpense == null) {
            throw new ExpenseNotFoundException("Expense not found for id: " + id);
        }
        return filteredExpense;
    }

    @Override
    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Override
    public void updateExpense(Long id, Expense expense) {
        // Checks if the id from the Expense object is the same from the Long id
        if (!expense.getId().equals(id)){
            throw new ExpenseBodyException("id from body is not the same from the Path Variable");
        }
        // Checks whether the expense exists or not for the given id
        Expense filteredExpense = expenseRepository.findById(id).orElseThrow(
                () -> new ExpenseNotFoundException("Expense not found for id: " + id)
        );

        // Updates and saves the expense
        filteredExpense.setExpenseType(expense.getExpenseType());
        filteredExpense.setDate(expense.getDate());
        filteredExpense.setAmount(expense.getAmount());
        filteredExpense.setCategory(expense.getCategory());
        filteredExpense.setAmount(expense.getAmount());
        filteredExpense.setNote(expense.getNote());

        expenseRepository.save(filteredExpense);
    }

    @Override
    public void deleteExpense(Long id) {
        // Checks whether the expense exists or not for the given id
        Expense filteredExpense = expenseRepository.findById(id).orElseThrow(
                () -> new ExpenseNotFoundException("Expense not found for id: " + id)
        );
        expenseRepository.delete(filteredExpense);
    }
}
