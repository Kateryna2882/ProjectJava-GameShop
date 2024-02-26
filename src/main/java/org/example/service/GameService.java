package org.example.service;

import org.example.model.Game;
import org.example.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameService {

    private final Connection connection;

    public GameService() {
        this.connection = DatabaseUtil.getConnection();
    }

    public List<Game> getAllGames() throws SQLException {
        List<Game> games = new ArrayList<>();
        String query = "SELECT * FROM games";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                games.add(extractGameFromResultSet(resultSet));
            }
        }
        return games;
    }

    public Game getGameById(int gameId) throws SQLException {
        String query = "SELECT * FROM games WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, gameId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractGameFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    public void createGame(Game game) throws SQLException {
        String query = "INSERT INTO games (name, release_date, rating, cost, description) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, game.getName());
            statement.setDate(2, java.sql.Date.valueOf(game.getReleaseDate()));
            statement.setInt(3, game.getRating());
            statement.setDouble(4, game.getCost());
            statement.setString(5, game.getDescription());
            statement.executeUpdate();
        }
    }

    public void deleteGameById(int gameId) throws SQLException {
        String query = "DELETE FROM games WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, gameId);
            statement.executeUpdate();
        }
    }

    public void updateGame(Game game) throws SQLException {
        String query = "UPDATE games SET name = ?, release_date = ?, rating = ?, cost = ?, description = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, game.getName());
            statement.setDate(2, java.sql.Date.valueOf(game.getReleaseDate()));
            statement.setInt(3, game.getRating());
            statement.setDouble(4, game.getCost());
            statement.setString(5, game.getDescription());
            statement.setInt(6, game.getId());
            statement.executeUpdate();
        }
    }

    private Game extractGameFromResultSet(ResultSet resultSet) throws SQLException {
        return new Game(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getDate("release_date").toLocalDate(),
                resultSet.getInt("rating"),
                resultSet.getDouble("cost"),
                resultSet.getString("description")
        );
    }
}

}
