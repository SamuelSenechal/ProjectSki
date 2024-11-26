package BE.senechal.DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import BE.senechal.ex1.Lesson;

public class LessonDAO {

    public void addLesson(Lesson lesson) {
        String sql = "INSERT INTO Lessons (lesson_type_id, instructor_id, min_booking, max_booking, is_morning, start_date) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, lesson.getLessonTypeId());
            statement.setInt(2, lesson.getInstructorId());
            statement.setInt(3, lesson.getMinBooking());
            statement.setInt(4, lesson.getMaxBooking());
            statement.setBoolean(5, lesson.isMorning());
            statement.setDate(6, new java.sql.Date(lesson.getStartDate().getTime()));

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Lesson> getAllLessons() {
        List<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT * FROM Lessons";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int lessonTypeId = resultSet.getInt("lesson_type_id");
                int instructorId = resultSet.getInt("instructor_id");
                int minBooking = resultSet.getInt("min_booking");
                int maxBooking = resultSet.getInt("max_booking");
                boolean isMorning = resultSet.getInt("is_morning") == 1;
                Date startDate = resultSet.getDate("start_date");

                Lesson lesson = new Lesson(id, lessonTypeId, instructorId, minBooking, maxBooking, isMorning, startDate);
                lessons.add(lesson);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lessons;
    }

    public List<Lesson> getLessonsForInstructorWithAccreditation(int instructorId, int accreditationId) {
        List<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT l.* FROM Lessons l " +
                     "JOIN Instructor_Accreditation ia ON l.instructor_id = ia.instructor_id " +
                     "WHERE ia.accreditation_id = ? AND l.instructor_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, accreditationId);
            statement.setInt(2, instructorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int lessonTypeId = resultSet.getInt("lesson_type_id");
                    int instructorIdResult = resultSet.getInt("instructor_id");
                    int minBooking = resultSet.getInt("min_booking");
                    int maxBooking = resultSet.getInt("max_booking");
                    boolean isMorning = resultSet.getBoolean("is_morning");
                    Date startDate = resultSet.getDate("start_date");

                    Lesson lesson = new Lesson(id, lessonTypeId, instructorIdResult, minBooking, maxBooking, isMorning, startDate);
                    lessons.add(lesson);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lessons;
    }
}
