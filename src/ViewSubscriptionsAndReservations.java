import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ViewSubscriptionsAndReservations {

    public void showSubscriptionsAndReservationsWindow(JFrame parentFrame) {
        JFrame frame = new JFrame("Informații Abonamente și Rezervări");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.LIGHT_GRAY);

        JLabel titleLabel = new JLabel("Informații Abonamente și Rezervări");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        infoPanel.add(titleLabel);

        displayClientSubscriptionsAndReservations(infoPanel);

        frame.add(infoPanel, BorderLayout.CENTER);

        JButton closeButton = new JButton("Închide");
        closeButton.setBackground(Color.BLACK);
        closeButton.setForeground(Color.WHITE);
        closeButton.addActionListener(e -> {
            frame.dispose();
        });

        frame.add(closeButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void displayClientSubscriptionsAndReservations(JPanel infoPanel) {
        String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/sala";
        String DATABASE_USER = "root";
        String DATABASE_PASSWORD = "denis";

        String subscriptionQuery = "SELECT tip, durata, pret FROM Abonament";
        String reservationQuery = "SELECT p.data, a.nume AS antrenament, an.nume AS antrenor, an.prenume AS antrenor_prenume " +
                "FROM Programare p " +
                "JOIN Antrenament a ON p.id_antrenament = a.id " +
                "JOIN Antrenor an ON p.id_antrenor = an.id_antrenor";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement subscriptionStatement = connection.prepareStatement(subscriptionQuery);
             PreparedStatement reservationStatement = connection.prepareStatement(reservationQuery)) {

            // Preluare abonamente
            try (ResultSet resultSet = subscriptionStatement.executeQuery()) {
                while (resultSet.next()) {
                    String subscriptionType = resultSet.getString("tip");
                    String subscriptionDuration = resultSet.getString("durata");
                    int subscriptionPrice = resultSet.getInt("pret");

                    infoPanel.add(new JLabel("Abonament: " + subscriptionType + " (" + subscriptionDuration + ") - " + subscriptionPrice + " lei"));
                }
            }

            // Preluare programări
            try (ResultSet resultSet = reservationStatement.executeQuery()) {
                while (resultSet.next()) {
                    String scheduleDate = resultSet.getString("data");
                    String trainingName = resultSet.getString("antrenament");
                    String trainerName = resultSet.getString("antrenor") + " " + resultSet.getString("antrenor_prenume");

                    infoPanel.add(new JLabel("Programare: " + trainingName + " pe " + scheduleDate + " cu antrenorul " + trainerName));
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
