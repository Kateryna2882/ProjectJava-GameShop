package org.example.contlollerTests;
import org.example.controller.UserController;
import org.example.model.User;
import org.example.service.AccountService;
import org.example.service.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTests {

    @Mock
    private UserService userService;

    @Mock
    private AccountService accountService;

    @Mock
    private Connection connection;

    private UserController userController;

    @BeforeEach
    void setUp() {
        userController = new UserController(connection);
        userController.userService = userService;
        userController.accountService = accountService;
    }

    @Test
    void registerUser_Successfully() throws SQLException {

        String input = "John\njohnny\n1990-01-01\npassword\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        User user = new User(1, "John", "johnny",
                java.time.LocalDate.parse("1990-01-01"), "password");


        Scanner scanner = new Scanner(System.in);
        userController.scanner = scanner;


        doNothing().when(userService).createUser(any(User.class));
        doReturn(user).when(userService).getUserByNickname("johnny");
        doNothing().when(accountService).createAccount(any());



        userController.registerUser();


        assertEquals("User registered successfully!", outContent.toString().trim());
    }

    @Test
    void registerUser_Failure() throws SQLException {

        String input = "John\njohnny\n1990-01-01\npassword\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);


        Scanner scanner = new Scanner(System.in);
        userController.scanner = scanner;

        doThrow(new SQLException("Test exception")).when(userService).createUser(any(User.class));


        userController.registerUser();


        assertEquals("Failed to register user: Test exception", outContent.toString().trim());
    }

    @Test
    void loginUser_Successfully() throws SQLException {

        String input = "johnny\npassword\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        User user = new User(1, "John", "johnny", java.time.LocalDate.parse("1990-01-01"), "password");


        Scanner scanner = new Scanner(System.in);
        userController.scanner = scanner;


        when(userService.getUserByNickname("johnny")).thenReturn(user);


        userController.loginUser();


        assertEquals("Login successful!", outContent.toString().trim());
    }

    @Test
    void loginUser_IncorrectCredentials() throws SQLException {

        String input = "johnny\nwrongpassword\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);


        Scanner scanner = new Scanner(System.in);
        userController.scanner = scanner;


        when(userService.getUserByNickname("johnny")).thenReturn(new User());


        userController.loginUser();


        assertEquals("Incorrect nickname or password.", outContent.toString().trim());
    }

    @Test
    void loginUser_Failure() throws SQLException {

        String input = "johnny\npassword\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);


        Scanner scanner = new Scanner(System.in);
        userController.scanner = scanner;


        when(userService.getUserByNickname("johnny")).thenThrow(new SQLException("Test exception"));


        userController.loginUser();

        assertEquals("Failed to login: Test exception", outContent.toString().trim());
    }


    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}