package org.example.contlollerTests;


import org.example.controller.AccountController;
import org.example.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTests {

    @Mock
    private AccountService mockAccountService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testAddFunds() throws SQLException {

        String input = "123\n100.0\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        doNothing().when(mockAccountService).addFunds(anyInt(), anyDouble());

        accountController.addFunds();


        verify(mockAccountService).addFunds(123, 100.0);
    }

    @Test
    void testDisplayBalance() throws SQLException {

        String input = "123\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        when(mockAccountService.getBalanceByUserId(anyInt())).thenReturn(150.0);

        accountController.displayBalance();


        verify(mockAccountService).getBalanceByUserId(123);
        assertEquals("User balance: 150.0\n", outContent.toString());
    }
}