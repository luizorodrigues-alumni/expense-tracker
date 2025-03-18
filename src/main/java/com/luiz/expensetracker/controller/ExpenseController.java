package com.luiz.expensetracker.controller;

import com.luiz.expensetracker.dto.ErrorMessage;
import com.luiz.expensetracker.exceptions.ExpenseBodyException;
import com.luiz.expensetracker.exceptions.ExpenseNotFoundException;
import com.luiz.expensetracker.model.Expense;
import com.luiz.expensetracker.service.ExpenseTrackerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
@Tag(name = "Expenses", description = "Personal Expense Management")
public class ExpenseController {
    private final ExpenseTrackerService expenseService;

    public ExpenseController(ExpenseTrackerService expenseService){
        this.expenseService = expenseService;
    }

    @Operation(
            summary = "Retrieves expenses",
            description = "Fetches expenses based on optional filters. "
                    + "If no parameters are provided, returns all expenses. "
                    + "If 'day' is provided, fetches expenses for that day. "
                    + "If 'category' and 'month' are provided, fetches expenses for the specified category and month."
    )
    @GetMapping()
    public ResponseEntity<List<Expense>> getExpenses(
            @RequestParam(value = "day", required = false) String day,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "month", required = false) String month
    ) {
        // Gets expenses by day
        if (day != null) {
            return ResponseEntity.ok(expenseService.getExpenseByDay(day));
        }
        // Get expenses by category and month
        if (category != null && month != null) {
            return ResponseEntity.ok(expenseService.getExpenseByCategoryAndMonth(category, month));
        }
        // Get all expenses
        return ResponseEntity.ok(expenseService.getAllExpenses());
    }

    // Gets all expense categories
    @Operation(
            summary = "Retrieves all expense categories",
            description = "Returns a list of all available expense categories."
    )
    @GetMapping("/categories")
    public ResponseEntity<?> getCategories(){
        try {
            return ResponseEntity.ok(expenseService.getAllExpenseCategories());
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    // Gets expenses by id
    @Operation(
            summary = "Retrieves an expense by ID",
            description = "Fetches an expense based on the provided ID. "
                    + "If the expense is not found, returns a 404 error."
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getExpenseById(@PathVariable Long id){
        try{
            return ResponseEntity.ok(expenseService.getExpenseById(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ErrorMessage(e.getMessage()));
        }
    }

    // Creates an expense
    @Operation(
            summary = "Creates an expense",
            description = "Adds a new expense to the database."
    )    @PostMapping()
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense){
        return ResponseEntity.status(201).body(expenseService.addExpense(expense));
    }

    // Updates an expense
    @Operation(
            summary = "Updates an expense",
            description = "Updates the details of an existing expense based on the provided ID. "
                    + "If the expense is not found, returns a 404 error."
    )
    @PutMapping("/{expenseId}")
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
    @Operation(
            summary = "Deletes an expense",
            description = "Removes an expense from the database based on the provided ID. "
                    + "If the expense is not found, returns a 404 error."
    )
    @DeleteMapping("/{expenseId}")
    public ResponseEntity<?> deleteExpense(@PathVariable Long expenseId){
        try{
            expenseService.deleteExpense(expenseId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ErrorMessage(e.getMessage()));
        }
    }
}
