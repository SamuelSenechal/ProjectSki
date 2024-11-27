package BE.senechal.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import BE.senechal.ski.Skier;

public class SkierDAO {
    public void addSkier(Skier skier) throws SQLException {
        String query = "INSERT INTO Skier (first_name, last_name) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, skier.getFirstName());
            statement.setString(2, skier.getLastName());
            statement.executeUpdate();
        }
    }

    public List<Skier> getAllSkiers() throws SQLException {
        List<Skier> skiers = new ArrayList<>();
        String query = "SELECT id, first_name, last_name FROM Skier";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Skier skier = new Skier(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name")
                );
                skiers.add(skier);
            }
        }
        return skiers;
    }
}
