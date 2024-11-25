package BE.senechal.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BE.senechal.ex1.Accreditation;
import BE.senechal.ex1.Instructor;

public class InstructorAccreditationDAO {

    public void addAccreditationToInstructor(int instructorId, int accreditationId) {
        String sql = "INSERT INTO Instructor_Accreditation (instructor_id, accreditation_id, date_assigned) VALUES (?, ?, SYSDATE)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, instructorId);
            statement.setInt(2, accreditationId);
            statement.executeUpdate();
            System.out.println("Accreditation added for instructor ID: " + instructorId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeAccreditationFromInstructor(int instructorId, int accreditationId) {
        String sql = "DELETE FROM Instructor_Accreditation WHERE instructor_id = ? AND accreditation_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, instructorId);
            statement.setInt(2, accreditationId);
            statement.executeUpdate();
            System.out.println("Accreditation removed for instructor ID: " + instructorId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> getInstructorAccreditations(int instructorId) {
        String sql = "SELECT accreditation_id FROM Instructor_Accreditation WHERE instructor_id = ?";
        List<Integer> accreditations = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, instructorId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                accreditations.add(rs.getInt("accreditation_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accreditations;
    }

    public Instructor getInstructorById(int instructorId) {
        String sql = "SELECT * FROM Instructor WHERE id = ?";
        Instructor instructor = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, instructorId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");

                List<Accreditation> accreditations = getAccreditationsForInstructor(instructorId);

                instructor = new Instructor(instructorId, firstName, lastName, accreditations);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return instructor;
    }

    public List<Accreditation> getAccreditationsForInstructor(int instructorId) {
        List<Accreditation> accreditations = new ArrayList<>();
        String sql = "SELECT a.* FROM Accreditations a " +
                     "JOIN Instructor_Accreditation ia ON a.id = ia.accreditation_id " +
                     "WHERE ia.instructor_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, instructorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int accreditationId = rs.getInt("id");
                String name = rs.getString("name");

                Accreditation accreditation = new Accreditation(accreditationId, name);
                accreditations.add(accreditation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accreditations;
    }
}
