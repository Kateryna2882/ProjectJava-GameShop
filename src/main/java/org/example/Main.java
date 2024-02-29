package org.example;

import org.example.model.User;
import org.example.service.UserService;
import org.example.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
            UserService userService = new UserService(connection);

            User user = userService.getUserById(1);
            if (user != null) {
                System.out.println("User found by ID:");
                System.out.println(user);
            } else {
                System.out.println("User not found by ID.");
            }

            List<User> allUsers = userService.getAllUsers();
            if (!allUsers.isEmpty()) {
                System.out.println("All users:");
                for (User u : allUsers) {
                    System.out.println(u);
                }
            } else {
                System.out.println("No users found.");
            }

            userService.deleteUser(2);
            System.out.println("User deleted successfully.");
        } catch (
                SQLException e) {
            System.out.println("Failed to retrieve user by ID: " + e.getMessage());
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }

}