package org.example.service;

import org.example.model.Account;
import org.example.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountService {

    private final Connection connection;

    public AccountService() {
        this.connection = DatabaseUtil.getConnection();
    }
    public double getBalanceByUserId(int userId) throws SQLException {
        String query = "SELECT balance FROM accounts WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("balance");
                }
            }
        }
        return 0.0;
    }
    public void addFunds(int userId, double amount) throws SQLException {
        String query = "UPDATE accounts SET balance = balance + ? WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, amount);
            statement.setInt(2, userId);
            statement.executeUpdate();
        }
    }
    public void createAccount(Account account) throws SQLException {
        String query = "INSERT INTO accounts (user_id, balance) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, account.getUserId());
            statement.setDouble(2, account.getBalance());
            statement.executeUpdate();
        }
    }
}

}
