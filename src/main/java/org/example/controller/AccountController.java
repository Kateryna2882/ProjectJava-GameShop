package org.example.controller;

import org.example.service.AccountService;

import java.sql.SQLException;
import java.util.Scanner;

public class AccountController {

    AccountService accountService;
    public Scanner scanner;

    public AccountController() {
        this.accountService = new AccountService();
        this.scanner = new Scanner(System.in);
    }
    public void addFunds() {
        System.out.println("Enter user ID:");
        int userId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter amount to add:");
        double amount = Double.parseDouble(scanner.nextLine());
        try {
            accountService.addFunds(userId, amount);
            System.out.println("Funds added successfully!");
        } catch (SQLException e) {
            System.out.println("Failed to add funds: " + e.getMessage());
        }
    }
    public void displayBalance() {
        System.out.println("Enter user ID:");
        int userId = Integer.parseInt(scanner.nextLine());
        try {
            double balance = accountService.getBalanceByUserId(userId);
            System.out.println("User balance: " + balance);
        } catch (SQLException e) {
            System.out.println("Failed to retrieve balance: " + e.getMessage());
        }
    }
}

