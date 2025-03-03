package com.luiz.expensetracker.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luiz.expensetracker.model.Expense;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExpenseDataHandler {
    @Getter
    private static List<Expense> expenses = new ArrayList<>();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String FILE_PATH = "src/main/resources/expenses.json";

    // Initializes the expenses Object
    @PostConstruct
    public void init(){
        InputStream is = getClass().getResourceAsStream("/expenses.json");

        try {
            expenses = mapper.readValue(is, new TypeReference<List<Expense>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Saves the current expense Object into the file
    public static void saveExpensesToFile(){
        try{
            File file = new File(FILE_PATH);
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, expenses);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
