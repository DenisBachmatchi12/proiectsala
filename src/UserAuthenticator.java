import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAuthenticator {
    public static boolean authenticate(String email, String password) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) {
            System.err.println("Conexiunea la baza de date este null. Nu se poate autentifica utilizatorul.");
            return false;
        }

        String query = "SELECT * FROM utilizator WHERE email = ? AND parola = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Returnează true dacă există un utilizator
        } catch (SQLException e) {
            System.err.println("Eroare la executarea interogării: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}