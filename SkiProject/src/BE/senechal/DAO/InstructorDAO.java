package BE.senechal.DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import BE.senechal.ski.Accreditation;
import BE.senechal.ski.Instructor;
import BE.senechal.ski.LessonType;
import BE.senechal.ski.Person;

public class InstructorDAO {
    public List<Instructor> getAllInstructors() {
        String sql = "SELECT * FROM INSTRUCTORS";
        List<Instructor> instructors = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String firstName = rs.getString("FIRST_NAME");
                String lastName = rs.getString("LAST_NAME");
                List<Accreditation> accreditations = getAccreditationsByInstructorId(id);
                instructors.add(new Instructor(id, firstName, lastName, accreditations));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return instructors;
    }

    private List<Accreditation> getAccreditationsByInstructorId(int instructorId) {
        String sql = "SELECT * FROM ACCREDITATIONS WHERE ID = ?";
        List<Accreditation> accreditations = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, instructorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString("NAME"); 
                accreditations.add(new Accreditation(rs.getInt("id"),rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accreditations;
    }

    public boolean addInstructor(Instructor instructor) {
        String sql = "INSERT INTO INSTRUCTORS (FIRST_NAME, LAST_NAME) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, instructor.getFirstName());
            stmt.setString(2, instructor.getLastName());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Instructor getInstructorById(int instructorId) {
        String sql = "SELECT * FROM Instructors WHERE id = ?";
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
