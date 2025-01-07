import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Detalii pentru conexiunea la baza de date
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/proiectsala?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";  // Numele utilizatorului MySQL
    private static final String DB_PASSWORD = "denis";  // Lasă gol pentru root fără parolă

    // Metodă pentru obținerea conexiunii
    public static Connection getConnection() {
        try {
            // Returnează conexiunea la baza de date
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            // Gestionează erorile de conexiune
            System.err.println("Eroare la conectarea la baza de date: " + e.getMessage());
            return null;
        }
    }
}
