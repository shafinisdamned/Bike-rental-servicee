import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BikeService {
    public List<String> viewAvailableBikes() {
        List<String> bikes = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            String query = "SELECT * FROM Bikes WHERE status = 'available'";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bikes.add(rs.getString("model"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bikes;
    }

    public String viewBikeDetails(int bikeId) {
        String details = "";
        try (Connection conn = DBUtil.getConnection()) {
            String query = "SELECT * FROM Bikes WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, bikeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                details = "ID: " + rs.getInt("id") + ", Model: " + rs.getString("model") + ", Status: " + rs.getString("status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return details;
    }
}

