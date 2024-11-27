package BE.senechal.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import BE.senechal.ski.LessonType;

public class LessonTypeDAO {

    public LessonType getLessonTypeById(int id) {
        String sql = "SELECT * FROM LessonType WHERE id = ?";
        LessonType lessonType = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("PRICE_PER_WEEK");
                int accreditationid = rs.getInt("accreditation_id");
                String sportType = rs.getString("sport_type");
                String category = rs.getString("category");
                lessonType = new LessonType(id, name, price, accreditationid, sportType, category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lessonType;
    }

    public List<LessonType> getAllLessonTypes() {
        String sql = "SELECT * FROM lessonType";
        List<LessonType> lessonTypes = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price_per_week");
                int accreditationid = rs.getInt("accreditation_id");
                String sportType = rs.getString("sport_type");
                String category = rs.getString("category");
                
                lessonTypes.add(new LessonType(id, name, price,accreditationid,sportType,category));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return lessonTypes;
    }

    public void addLessonType(LessonType lessonType) {
        String sql = "INSERT INTO LessonType (name, price) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lessonType.getName());
            stmt.setDouble(2, lessonType.getPrice());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateLessonType(LessonType lessonType) {
        String sql = "UPDATE LessonType SET name = ?, price = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lessonType.getName());
            stmt.setDouble(2, lessonType.getPrice());
            stmt.setInt(3, lessonType.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteLessonType(int id) {
        String sql = "DELETE FROM LessonType WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
