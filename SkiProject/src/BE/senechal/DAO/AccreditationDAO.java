package BE.senechal.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BE.senechal.ski.Accreditation;

public class AccreditationDAO {
    public List<Accreditation> getAllAccreditations() {
        List<Accreditation> accreditations = new ArrayList<>();
        String sql = "SELECT id, name FROM Accreditations";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Accreditation accreditation = new Accreditation(rs.getInt("id"),rs.getString("name"));
                accreditations.add(accreditation);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accreditations;
    }

    public void addAccreditation(Accreditation accreditation) {
        String sql = "INSERT INTO Accreditations (name) VALUES (?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, accreditation.getName());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeAccreditation(int accreditationId) {
        String sql = "DELETE FROM Accreditations WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, accreditationId);
            statement.executeUpdate();
            System.out.println("Accreditations removed with ID: " + accreditationId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Accreditation getAccreditationById(int accreditationId) {
        Accreditation accreditation = null;
        String sql = "SELECT id, name FROM Accreditations WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, accreditationId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
            	accreditation = new Accreditation(rs.getInt("id"),rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accreditation;
    }
    
    public boolean hasAccreditation(int instructorId, int accreditationId) throws SQLException {
        String sql = "SELECT 1 FROM Instructor_Accreditation WHERE instructor_id = ? AND accreditation_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, instructorId);
            statement.setInt(2, accreditationId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); 
            }
        }
    }

	
}

