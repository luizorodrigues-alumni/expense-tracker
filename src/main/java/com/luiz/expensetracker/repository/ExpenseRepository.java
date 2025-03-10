package com.luiz.expensetracker.repository;

import com.luiz.expensetracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
