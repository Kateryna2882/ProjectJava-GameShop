package org.example.contlollerTests;

import org.example.controller.GameController;
import org.example.model.Game;
import org.example.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class GameControllerTests {
    @Mock
    private GameService mockGameService;

    @InjectMocks
    private GameController gameController;

    @BeforeEach
    void setUp() {
        openMocks(this);
        gameController.scanner = new Scanner(System.in);
    }

    @Test
    void testDisplayAllGames() throws SQLException {

        List<Game> games = new ArrayList<>();
        games.add(new Game(1, "Game 1", LocalDate.now(), 5, 29.99, "Description 1"));
        games.add(new Game(2, "Game 2", LocalDate.now(), 4, 19.99, "Description 2"));
        when(mockGameService.getAllGames()).thenReturn(games);


        gameController.displayAllGames();


        verify(mockGameService).getAllGames();

    }

    @Test
    void testDisplayGameById() throws SQLException {

        int gameId = 1;
        Game game = new Game(gameId, "Game 1", LocalDate.now(), 5, 29.99, "Description 1");
        when(mockGameService.getGameById(gameId)).thenReturn(game);


        gameController.displayGameById();


        verify(mockGameService).getGameById(gameId);

    }

    @Test
    void testCreateGame() throws SQLException {

        String input = "Game 1\n2023-02-20\n5\n29.99\nDescription 1\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        doNothing().when(mockGameService).createGame(any(Game.class));


        gameController.createGame();


        verify(mockGameService).createGame(any(Game.class));

    }

    @Test
    void testDeleteGameById() throws SQLException {

        int gameId = 1;
        doNothing().when(mockGameService).deleteGameById(gameId);


        gameController.deleteGameById();


        verify(mockGameService).deleteGameById(gameId);
    }

    @Test
    void testUpdateGame() throws SQLException {

        int gameId = 1;
        Game game = new Game(gameId, "Game 1", LocalDate.now(), 5, 29.99, "Description 1");
        when(mockGameService.getGameById(gameId)).thenReturn(game);

        String newGameName = "New Game Name";
        String input = newGameName + "\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        doNothing().when(mockGameService).updateGame(any(Game.class));


        gameController.updateGame();


        verify(mockGameService).updateGame(any(Game.class));
        assertEquals(newGameName, game.getName());
    }
}
