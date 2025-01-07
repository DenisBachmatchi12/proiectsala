import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Reservation {
    private final Integer trainerId;

    public Reservation(Integer trainerId) {
        this.trainerId = trainerId;
    }

    public void showReservationWindow() {
        JFrame scheduleFrame = new JFrame("Creare Programare");
        scheduleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        scheduleFrame.setSize(400, 500);
        scheduleFrame.setLayout(new GridLayout(7, 2, 10, 10));

        JLabel firstNameLabel = new JLabel("Nume Client:");
        JTextField firstNameField = new JTextField();

        JLabel lastNameLabel = new JLabel("Prenume Client:");
        JTextField lastNameField = new JTextField();

        JLabel trainingLabel = new JLabel("Antrenament:");
        JComboBox<String> trainingComboBox = new JComboBox<>();

        JLabel dateLabel = new JLabel("Data programării (YYYY-MM-DD):");
        JTextField dateField = new JTextField();

        JButton saveButton = new JButton("Salvează");

        // Populare JComboBox cu tipuri de antrenament din baza de date
        populateTrainingComboBox(trainingComboBox);

        scheduleFrame.add(firstNameLabel);
        scheduleFrame.add(firstNameField);
        scheduleFrame.add(lastNameLabel);
        scheduleFrame.add(lastNameField);
        scheduleFrame.add(trainingLabel);
        scheduleFrame.add(trainingComboBox);
        scheduleFrame.add(dateLabel);
        scheduleFrame.add(dateField);
        scheduleFrame.add(new JLabel()); // Spacer
        scheduleFrame.add(saveButton);

        saveButton.addActionListener(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String date = dateField.getText();
            String selectedTraining = (String) trainingComboBox.getSelectedItem();

            if (firstName.isEmpty() || lastName.isEmpty()) {
                JOptionPane.showMessageDialog(scheduleFrame,
                        "Te rog să introduci numele și prenumele clientului.",
                        "Eroare", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (selectedTraining == null) {
                JOptionPane.showMessageDialog(scheduleFrame,
                        "Te rog să selectezi un antrenament.",
                        "Eroare", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidDate(date)) {
                JOptionPane.showMessageDialog(scheduleFrame,
                        "Te rog să introduci o dată validă (YYYY-MM-DD).",
                        "Eroare", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int clientId = getClientId(firstName, lastName);
            if (clientId == -1) {
                JOptionPane.showMessageDialog(scheduleFrame,
                        "Clientul cu acest nume și prenume nu există.",
                        "Eroare", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int trainingId = getTrainingId(selectedTraining);
            if (trainingId == -1) {
                JOptionPane.showMessageDialog(scheduleFrame,
                        "Antrenamentul selectat nu există.",
                        "Eroare", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (createSchedule(date, clientId, trainingId)) {
                JOptionPane.showMessageDialog(scheduleFrame,
                        "Programarea a fost creată cu succes!",
                        "Succes", JOptionPane.INFORMATION_MESSAGE);
                scheduleFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(scheduleFrame,
                        "Eroare la crearea programării.",
                        "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        });

        scheduleFrame.setVisible(true);
    }

    private boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void populateTrainingComboBox(JComboBox<String> trainingComboBox) {
        String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/sala";
        String DATABASE_USER = "root";
        String DATABASE_PASSWORD = "denis";

        String query = "SELECT nume FROM Antrenament";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                trainingComboBox.addItem(resultSet.getString("nume"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private int getClientId(String firstName, String lastName) {
        String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/sala";
        String DATABASE_USER = "root";
        String DATABASE_PASSWORD = "denis";

        String query = "SELECT id_client FROM Client WHERE nume = ? AND prenume = ?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id_client");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    private int getTrainingId(String trainingName) {
        String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/sala";
        String DATABASE_USER = "root";
        String DATABASE_PASSWORD = "denis";

        String query = "SELECT id FROM Antrenament WHERE nume = ?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, trainingName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    private boolean createSchedule(String date, int clientId, int trainingId) {
        String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/sala";
        String DATABASE_USER = "root";
        String DATABASE_PASSWORD = "denis";

        String query = "INSERT INTO Programare (id_client, id_antrenor, id_antrenament, data) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, clientId);
            preparedStatement.setInt(2, trainerId);
            preparedStatement.setInt(3, trainingId);
            preparedStatement.setString(4, date);

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
