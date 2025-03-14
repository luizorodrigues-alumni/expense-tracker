package com.luiz.expensetracker.service;

import com.luiz.expensetracker.exceptions.ExpenseBodyException;
import com.luiz.expensetracker.exceptions.ExpenseNotFoundException;
import com.luiz.expensetracker.exceptions.NoCategoriesFoundException;
import com.luiz.expensetracker.model.Expense;
import com.luiz.expensetracker.utils.ExpenseDataHandler;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("json")
public class ExpenseServiceFileImplementation implements ExpenseTrackerService{
    // Gets all expenses
    @Override
    public List<Expense> getAllExpenses(){
        return ExpenseDataHandler.getExpenses();
    }

    // Gets all categories
    @Override
    public List<String> getAllExpenseCategories(){
        List<String> categories = ExpenseDataHandler.getExpenses().stream()
                .map(Expense::getCategory)
                .distinct()
                .toList();
        if (categories.isEmpty()){
            throw new NoCategoriesFoundException("No categories Found");
        }
        return categories;
    }
    @Override
    // Get expenses by day
    public List<Expense> getExpenseByDay(String date){
        return ExpenseDataHandler.getExpenses().stream()
                .filter(expense -> expense.getDate().equalsIgnoreCase(date))
                .toList();
    }
    @Override
    public List<Expense> getExpenseByCategoryAndMonth(String category, String month){
        // filter the expenses list by month and category
        return ExpenseDataHandler.getExpenses().stream()
                .filter(
                        expense -> expense.getCategory()
                                .equalsIgnoreCase(category) && expense.getDate()
                                .startsWith(month)
                        )
                .toList();
    }
    @Override
    public Expense getExpenseById(Long id){
        // Filter the expenses List to get the object with the corresponding id
        Expense filteredExpense = ExpenseDataHandler.getExpenses().stream()
                .filter(expense -> expense.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (filteredExpense == null){
            throw new ExpenseNotFoundException("Expense not found for id: " + id);
        }
        return filteredExpense;
    }
    @Override
    public Expense addExpense(Expense expense){
        // Gets the list of expenses
        List<Expense> expenseList = ExpenseDataHandler.getExpenses();

        // Set the id incrementing the last value
        Long lastExpenseId = expenseList.getLast().getId();
        expense.setId(lastExpenseId + 1);

        // Adds the expense to the list and update the JSON file
        expenseList.add(expense);
        ExpenseDataHandler.saveExpensesToFile();
        return expense;
    }
    @Override
    public void updateExpense(Long id, Expense expense){
        // Checks if the id from the Expense object is the same from the Long id
        if (!expense.getId().equals(id)){
            throw new ExpenseBodyException("Id from body is not the same from the Path Variable");
        }
        // Expense to Update
        Expense expenseToUpdate = getExpenseById(id);
        if (expenseToUpdate == null) {
            throw new ExpenseNotFoundException("Expense not found for id: " + id);
        }
        // Gets the list of Expenses and the index
        List<Expense> expenseList = ExpenseDataHandler.getExpenses();
        int expenseIndex = expenseList.indexOf(expenseToUpdate);

        // Update the List and Save the JSON file
        expenseList.set(expenseIndex, expense);
        ExpenseDataHandler.saveExpensesToFile();
    }
    @Override
    public void deleteExpense(Long id){
        // Get expense by id
        Expense expense = getExpenseById(id);
        if (expense == null) {
            throw new ExpenseNotFoundException("Expense not found for id: " + id);
        }
        // Delete the expense and save the new List on a JSON File
        ExpenseDataHandler.getExpenses().remove(expense);
        ExpenseDataHandler.saveExpensesToFile();
    }
}
