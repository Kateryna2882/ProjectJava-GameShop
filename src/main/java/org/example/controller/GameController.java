package org.example.controller;

import org.example.model.Game;
import org.example.service.GameService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class GameController {

    GameService gameService;
    Scanner scanner;

    public GameController() {
        this.gameService = new GameService();
        this.scanner = new Scanner(System.in);
    }
    public void displayAllGames() {
        try {
            List<Game> games = gameService.getAllGames();
            if (!games.isEmpty()) {
                System.out.println("Available games:");
                for (Game game : games) {
                    System.out.println(game);
                }
            } else {
                System.out.println("No games available.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve games: " + e.getMessage());
        }
    }
    public void displayGameById() {
        System.out.println("Enter game ID:");
        int gameId = Integer.parseInt(scanner.nextLine());
        try {
            Game game = gameService.getGameById(gameId);
            if (game != null) {
                System.out.println("Game details:");
                System.out.println(game);
            } else {
                System.out.println("Game not found.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve game details: " + e.getMessage());
        }
    }
    public void createGame() {
        System.out.println("Enter game name:");
        String name = scanner.nextLine();
        System.out.println("Enter release date (yyyy-MM-dd):");
        LocalDate releaseDate = LocalDate.parse(scanner.nextLine());
        System.out.println("Enter rating:");
        int rating = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter cost:");
        double cost = Double.parseDouble(scanner.nextLine());
        System.out.println("Enter description:");
        String description = scanner.nextLine();

        Game game = new Game(0, name, releaseDate, rating, cost, description);

        try {
            gameService.createGame(game);
            System.out.println("Game created successfully!");
        } catch (SQLException e) {
            System.out.println("Failed to create game: " + e.getMessage());
        }
    }
    public void deleteGameById() {
        System.out.println("Enter game ID to delete:");
        int gameId = Integer.parseInt(scanner.nextLine());
        try {
            gameService.deleteGameById(gameId);
            System.out.println("Game deleted successfully!");
        } catch (SQLException e) {
            System.out.println("Failed to delete game: " + e.getMessage());
        }
    }
    public void updateGame() {
        System.out.println("Enter game ID to update:");
        int gameId = Integer.parseInt(scanner.nextLine());
        try {
            Game game = gameService.getGameById(gameId);
            if (game != null) {
                System.out.println("Enter new name (leave empty to keep current):");
                String newName = scanner.nextLine();
                if (!newName.isEmpty()) {
                    game.setName(newName);
                }
                gameService.updateGame(game);
                System.out.println("Game updated successfully!");
            } else {
                System.out.println("Game not found.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to update game: " + e.getMessage());
        }
    }

}
