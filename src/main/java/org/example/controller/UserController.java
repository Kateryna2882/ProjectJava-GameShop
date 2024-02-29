package org.example.controller;

import org.example.model.Account;
import org.example.model.User;
import org.example.service.AccountService;
import org.example.service.UserService;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class UserController {

    public UserService userService;
    public AccountService accountService;
    public Scanner scanner;


    public UserController(Connection connection) {
        this.userService = new UserService(connection);
        this.accountService = new AccountService();
        this.scanner = new Scanner(System.in);
    }


    public void registerUser() {
        System.out.println("Enter name:");
        String name = scanner.nextLine();
        System.out.println("Enter nickname:");
        String nickname = scanner.nextLine();
        System.out.println("Enter birthday (yyyy-MM-dd):");
        LocalDate birthday = LocalDate.parse(scanner.nextLine());
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        User user = new User(0, name, nickname, birthday, password);

        try {
            userService.createUser(user);
            Account account = new Account(user.getId(), 0.0);
            accountService.createAccount(account);
            System.out.println("User registered successfully!");
        } catch (SQLException e) {
            System.out.println("Failed to register user: " + e.getMessage());
        }
    }

    public void loginUser() {
        System.out.println("Enter nickname:");
        String nickname = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        try {
            User user = userService.getUserByNickname(nickname);

            if (user != null && user.getPassword().equals(password)) {
                System.out.println("Login successful!");
            } else {
                System.out.println("Incorrect nickname or password.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to login: " + e.getMessage());
        }
    }

}

