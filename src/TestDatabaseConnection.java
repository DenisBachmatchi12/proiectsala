import java.sql.Connection;

public class TestDatabaseConnection {
    public static void main(String[] args) {
        Connection connection = DatabaseConnection.getConnection();

        if (connection != null) {
            System.out.println("Conexiunea la baza de date a fost realizată cu succes!");
        } else {
            System.err.println("Conexiunea la baza de date a eșuat.");
        }
    }
}