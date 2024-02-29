package org.example.serviceTests;

import org.example.model.Account;
import org.example.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AccountServiceTests {
    @Mock
    private Connection mockConnection;
    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        mockConnection = mock(Connection.class);
        accountService = new AccountService(mockConnection);
    }

    @Test
    void testGetBalanceByUserId() throws SQLException {

        int userId = 1;
        double expectedBalance = 100.0;
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getDouble("balance")).thenReturn(expectedBalance);


        double actualBalance = accountService.getBalanceByUserId(userId);


        verify(mockStatement).setInt(1, userId);
        verify(mockStatement).executeQuery();
        assertEquals(expectedBalance, actualBalance);
    }

    @Test
    void testAddFunds() throws SQLException {

        int userId = 1;
        double amount = 50.0;
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);


        accountService.addFunds(userId, amount);

        verify(mockStatement).setDouble(1, amount);
        verify(mockStatement).setInt(2, userId);
        verify(mockStatement).executeUpdate();
    }

    @Test
    void testCreateAccount() throws SQLException {

        Account account = new Account(1, 100.0);
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);


        accountService.createAccount(account);


        verify(mockStatement).setInt(1, account.getUserId());
        verify(mockStatement).setDouble(2, account.getBalance());
        verify(mockStatement).executeUpdate();
    }
}
