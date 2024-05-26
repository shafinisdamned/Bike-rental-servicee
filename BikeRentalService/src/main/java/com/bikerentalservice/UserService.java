import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    private int loggedInUserId = -1;

    public boolean login(String username, String password) {
        try (Connection conn = DBUtil.getConnection()) {
            String query = "SELECT id FROM Users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                loggedInUserId = rs.getInt("id");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void logout() {
        loggedInUserId = -1;
    }

    public int getLoggedInUserId() {
        return loggedInUserId;
    }
}
