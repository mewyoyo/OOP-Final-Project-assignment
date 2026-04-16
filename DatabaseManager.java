import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:pets.db";


    public static void initialize() {
        try (Connection conn = DriverManager.getConnection(URL)) {
            String sql = "CREATE TABLE IF NOT EXISTS pets (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "type TEXT, " +
                    "name TEXT, " +
                    "age INTEGER, " +
                    "info TEXT)";
            conn.createStatement().execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void savePet(String type, String name, String age, String info) {
        String sql = "INSERT INTO pets(type, name, age, info) VALUES(?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, type);
            pstmt.setString(2, name);
            pstmt.setInt(3, Integer.parseInt(age));
            pstmt.setString(4, info);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList<Object[]> loadAllPets() {
        ArrayList<Object[]> data = new ArrayList<>();
        String sql = "SELECT * FROM pets";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                data.add(new Object[]{
                        rs.getInt("id"),
                        rs.getString("type"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("info")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void deletePet(int id) {
        String sql = "DELETE FROM pets WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
