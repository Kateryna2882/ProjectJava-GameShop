package org.example.serviceTests;

import org.example.model.User;
import org.example.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {
    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testCreateUser() throws SQLException {
        User user = new User(1, "John Doe", "john",
                LocalDate.of(1990, 1, 1), "password");
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        userService.createUser(user);

        verify(connection, times(1)).prepareStatement(any(String.class));
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testGetUserById() throws SQLException {
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("John Doe");
        when(resultSet.getString("nickname")).thenReturn("john");
        when(resultSet.getDate("birthday"))
                .thenReturn(java.sql.Date.valueOf(LocalDate.of(1990, 1, 1)));
        when(resultSet.getString("password")).thenReturn("password");

        User actualUser = userService.getUserById(1);

        assertNotNull(actualUser);
        assertEquals(1, actualUser.getId());
        assertEquals("John Doe", actualUser.getName());
        assertEquals("john", actualUser.getNickname());
        assertEquals(LocalDate.of(1990, 1, 1), actualUser.getBirthday());
        assertEquals("password", actualUser.getPassword());
    }

    @Test
    public void testGetAllUsers() throws SQLException {
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("John Doe");
        when(resultSet.getString("nickname")).thenReturn("john");
        when(resultSet.getDate("birthday"))
                .thenReturn(java.sql.Date.valueOf(LocalDate.of(1990, 1, 1)));
        when(resultSet.getString("password")).thenReturn("password");

        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(new User(1, "John Doe", "john",
                LocalDate.of(1990, 1, 1), "password"));

        List<User> actualUsers = userService.getAllUsers();

        assertNotNull(actualUsers);
        assertEquals(expectedUsers.size(), actualUsers.size());
        for (int i = 0; i < expectedUsers.size(); i++) {
            User expectedUser = expectedUsers.get(i);
            User actualUser = actualUsers.get(i);
            assertEquals(expectedUser.getId(), actualUser.getId());
            assertEquals(expectedUser.getName(), actualUser.getName());
            assertEquals(expectedUser.getNickname(), actualUser.getNickname());
            assertEquals(expectedUser.getBirthday(), actualUser.getBirthday());
            assertEquals(expectedUser.getPassword(), actualUser.getPassword());
        }
    }

    @Test
    public void testDeleteUser() throws SQLException {
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);

        userService.deleteUser(1);

        verify(connection, times(1)).prepareStatement(any(String.class));
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testGetUserByNickname() throws SQLException {
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("John Doe");
        when(resultSet.getString("nickname")).thenReturn("john");
        when(resultSet.getDate("birthday"))
                .thenReturn(java.sql.Date.valueOf(LocalDate.of(1990, 1, 1)));
        when(resultSet.getString("password")).thenReturn("password");

        User actualUser = userService.getUserByNickname("john");

        assertNotNull(actualUser);
        assertEquals(1, actualUser.getId());
        assertEquals("John Doe", actualUser.getName());
        assertEquals("john", actualUser.getNickname());
        assertEquals(LocalDate.of(1990, 1, 1), actualUser.getBirthday());
        assertEquals("password", actualUser.getPassword());
    }
}
