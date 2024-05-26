import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RentalService {
    public boolean rentBike(int userId, int bikeId) {
        try (Connection conn = DBUtil.getConnection()) {
            // Check if bike is available
            String checkQuery = "SELECT status FROM Bikes WHERE id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, bikeId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getString("status").equals("available")) {
                // Update bike status
                String updateQuery = "UPDATE Bikes SET status = 'rented' WHERE id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setInt(1, bikeId);
                updateStmt.executeUpdate();

                // Create rental record
                String insertQuery = "INSERT INTO Rentals (user_id, bike_id) VALUES (?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                insertStmt.setInt(1, userId);
                insertStmt.setInt(2, bikeId);
                insertStmt.executeUpdate();

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean returnBike(int userId, int bikeId) {
        try (Connection conn = DBUtil.getConnection()) {
            // Check if bike is rented by the user
            String checkQuery = "SELECT * FROM Rentals WHERE user_id = ? AND bike_id = ? AND return_date IS NULL";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, userId);
            checkStmt.setInt(2, bikeId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                // Update bike status
                String updateQuery = "UPDATE Bikes SET status = 'available' WHERE id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setInt(1, bikeId);
                updateStmt.executeUpdate();

                // Update rental record
                String updateRentalQuery = "UPDATE Rentals SET return_date = CURRENT_TIMESTAMP WHERE id = ?";
                PreparedStatement updateRentalStmt = conn.prepareStatement(updateRentalQuery);
                updateRentalStmt.setInt(1, rs.getInt("id"));
                updateRentalStmt.executeUpdate();

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<String> viewMyRentals(int userId) {
        List<String> rentals = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            String query = "SELECT * FROM Rentals WHERE user_id = ? AND return_date IS NULL";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rentals.add("Rental ID: " + rs.getInt("id") + ", Bike ID: " + rs.getInt("bike_id") + ", Rental Date: " + rs.getTimestamp("rental_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentals;
    }
}
