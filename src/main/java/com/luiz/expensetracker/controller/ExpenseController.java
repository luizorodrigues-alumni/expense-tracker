package com.luiz.expensetracker.controller;

import com.luiz.expensetracker.dto.ErrorMessage;
import com.luiz.expensetracker.exceptions.ExpenseBodyException;
import com.luiz.expensetracker.exceptions.ExpenseNotFoundException;
import com.luiz.expensetracker.model.Expense;
import com.luiz.expensetracker.service.ExpenseTrackerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExpenseController {
    private final ExpenseTrackerService expenseService;

    public ExpenseController(ExpenseTrackerService expenseService){
        this.expenseService = expenseService;
    }

    // Get all expenses
    @GetMapping("/expenses")
    public ResponseEntity<List<Expense>> getAllExpenses(){
        return ResponseEntity.ok(expenseService.getAllExpenses());
    }

    // Gets all expense categories
    @GetMapping("/expenses/categories")
    public ResponseEntity<?> getCategories(){
        try {
            return ResponseEntity.ok(expenseService.getAllExpenseCategories());
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    // Gets expenses by id
    @GetMapping("/expenses/{id}")
    public ResponseEntity<?> getExpenseById(@PathVariable Long id){
        try{
            return ResponseEntity.ok(expenseService.getExpenseById(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ErrorMessage(e.getMessage()));
        }
    }

    // Gets expenses by day
    @GetMapping(value = "/expenses", params = "day")
    public ResponseEntity<List<Expense>> getExpensesByDay(@RequestParam(value = "day") String date){
        return ResponseEntity.ok(expenseService.getExpenseByDay(date));
    }

    // Get expenses by category and month
    @GetMapping(value = "/expenses", params = {"category", "month"})
    public ResponseEntity<List<Expense>> getExpensesByCategoryAndMonth(
            @RequestParam(value = "category") String category,
            @RequestParam(value = "month") String month){

        return ResponseEntity.ok(expenseService.getExpenseByCategoryAndMonth(category, month));
    }

    // Creates an expense
    @PostMapping("/expenses")
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense){
        return ResponseEntity.status(201).body(expenseService.addExpense(expense));
    }

    // Updates an expense
    @PutMapping("/expenses/{expenseId}")
    public ResponseEntity<?> updateExpense(@PathVariable Long expenseId, @RequestBody Expense expense){
        try{
            expenseService.updateExpense(expenseId, expense);
            return ResponseEntity.noContent().build();
        } catch (ExpenseNotFoundException e) {
            return ResponseEntity.status(404).body(new ErrorMessage(e.getMessage()));
        }
        catch (ExpenseBodyException e){
            return ResponseEntity.status(400).body(new ErrorMessage(e.getMessage()));
        }
    }

    // Deletes an expense
    @DeleteMapping("/expenses/{expenseId}")
    public ResponseEntity<?> deleteExpense(@PathVariable Long expenseId){
        try{
            expenseService.deleteExpense(expenseId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ErrorMessage(e.getMessage()));
        }
    }
}
