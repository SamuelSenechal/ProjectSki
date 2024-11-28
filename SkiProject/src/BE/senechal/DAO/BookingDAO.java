package BE.senechal.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import BE.senechal.ski.Booking;
public class BookingDAO {
	public void saveBooking(Booking booking) {
        String sql = "INSERT INTO BOOKINGS (SKIER_ID, LESSON_ID, INSURANCE) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            System.out.println(booking.getSkier().getId());
            System.out.println(booking.getLesson().getId());
            stmt.setInt(1, booking.getSkier().getId());
            
            stmt.setInt(2, booking.getLesson().getId());
            stmt.setBoolean(3, booking.hasInsurance());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
