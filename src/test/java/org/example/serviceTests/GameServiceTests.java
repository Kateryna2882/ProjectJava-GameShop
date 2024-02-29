package org.example.serviceTests;

import org.example.model.Game;
import org.example.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GameServiceTests {
    @Mock
    private Connection mockConnection;

    private GameService gameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        gameService = new GameService(mockConnection);
    }

    @Test
    void testGetAllGames() throws SQLException {
        // Arrange
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);


        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("Test Game");
        when(mockResultSet.getDate("release_date")).thenReturn(java.sql.Date.valueOf(LocalDate.now()));
        when(mockResultSet.getInt("rating")).thenReturn(5);
        when(mockResultSet.getDouble("cost")).thenReturn(50.0);
        when(mockResultSet.getString("description")).thenReturn("Test Description");


        List<Game> games = gameService.getAllGames();


        verify(mockStatement).executeQuery();
        assertEquals(1, games.size()); // Assuming we expect 1 game in the list
        Game game = games.get(0);
        assertEquals(1, game.getId());
        assertEquals("Test Game", game.getName());
        assertEquals(LocalDate.now(), game.getReleaseDate());
        assertEquals(5, game.getRating());
        assertEquals(50.0, game.getCost());
        assertEquals("Test Description", game.getDescription());
    }

    @Test
    void testGetGameById() throws SQLException {
        // Arrange
        int gameId = 1;
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(gameId);
        when(mockResultSet.getString("name")).thenReturn("GameName");
        when(mockResultSet.getDate("release_date")).thenReturn(java.sql.Date.valueOf(LocalDate.of(2022, 1, 1)));
        when(mockResultSet.getInt("rating")).thenReturn(5);
        when(mockResultSet.getDouble("cost")).thenReturn(59.99);
        when(mockResultSet.getString("description")).thenReturn("Description");

        // Act
        Game game = gameService.getGameById(gameId);

        // Assert
        verify(mockStatement).setInt(1, gameId);
        verify(mockStatement).executeQuery();
        assertNotNull(game);
        assertEquals(gameId, game.getId());
        assertEquals("GameName", game.getName());
        assertEquals(LocalDate.of(2022, 1, 1), game.getReleaseDate());
        assertEquals(5, game.getRating());
        assertEquals(59.99, game.getCost());
        assertEquals("Description", game.getDescription());
    }

    @Test
    void testCreateGame() throws SQLException {
        // Arrange
        Game mockGame = new Game(1,"MockGame",
                LocalDate.of(2023, 2, 1), 4, 49.99, "Mock game description");
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockStatement);

        // Act
        gameService.createGame(mockGame);

        // Assert
        verify(mockStatement).setString(1, mockGame.getName());
        verify(mockStatement).setDate(2, java.sql.Date.valueOf(mockGame.getReleaseDate()));
        verify(mockStatement).setInt(3, mockGame.getRating());
        verify(mockStatement).setDouble(4, mockGame.getCost());
        verify(mockStatement).setString(5, mockGame.getDescription());
        verify(mockStatement).executeUpdate();
    }
}
