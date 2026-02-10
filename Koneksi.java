import java.sql.Connection;
import java.sql.DriverManager;

public class Koneksi {
    public static Connection getConnection() {
        try {
            String url = "jdbc:mariadb://localhost:3306/db_parkir";
            String user = "root";
            String pass = ""; // default XAMPP

            return DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
            return null;
        }
    }
}
