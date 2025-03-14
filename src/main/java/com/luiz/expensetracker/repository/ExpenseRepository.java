package com.luiz.expensetracker.repository;

import com.luiz.expensetracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query(value = "SELECT DISTINCT category FROM expense", nativeQuery = true)
    List<String> findDistinctByCategory();
    List<Expense> findByDate(String date);

    List<Expense> findByCategoryIgnoreCaseAndDateStartingWith(String category, String date);

}
