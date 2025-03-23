package com.luiz.expensetracker.domain.user.dto;

import com.luiz.expensetracker.domain.user.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
