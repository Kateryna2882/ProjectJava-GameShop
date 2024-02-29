package org.example;


import org.example.controller.GameController;
import org.example.controller.UserController;
import org.example.controller.AccountController;
import org.example.util.DatabaseUtil;

import java.sql.Connection;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Connection connection = DatabaseUtil.getConnection();
        UserController userController = new UserController(connection);
        Scanner scanner = new Scanner(System.in);
        GameController gameController = new GameController();
        AccountController accountController = new AccountController();

        boolean running = true;
        while (running) {
            System.out.println("Welcome to the Game Store!");
            System.out.println("1. Log in");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    userController.loginUser();
                    gameController.displayAllGames();
                    gameController.displayGameById();
                    gameController.createGame();
                    gameController.deleteGameById();
                    gameController.updateGame();
                    accountController.addFunds();
                    accountController.displayBalance();
                    break;
                case 2:
                    userController.registerUser();
                    gameController.displayAllGames();
                    gameController.displayGameById();
                    gameController.createGame();
                    gameController.deleteGameById();
                    gameController.updateGame();
                    accountController.addFunds();
                    accountController.displayBalance();
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}