package com.luiz.expensetracker.docs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Expense Tracker API",
                version = "1.0",
                description = "API for managing personal expenses, allowing you to create, list, update and remove expenses."
        )
)
public class OpenAPIConfig {
}
