import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class TrainerSelection {

    public void showTrainerSelection(MainMenu mainMenu) {
        JFrame frame = new JFrame("Selectare Antrenor");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);

        // Folosim BoxLayout pentru a pune butoanele unul sub altul
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.getContentPane().setBackground(Color.DARK_GRAY);

        JLabel trainerLabel = new JLabel("Alege un antrenor:");
        trainerLabel.setForeground(Color.WHITE);

        JComboBox<String> trainerComboBox = new JComboBox<>();
        trainerComboBox.setBackground(Color.GRAY);
        trainerComboBox.setForeground(Color.WHITE);

        JButton selectButton = new JButton("Selectează");
        selectButton.setBackground(Color.BLACK);
        selectButton.setForeground(Color.WHITE);
        JButton backButton = new JButton("Înapoi");
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);

        // Populăm ComboBox-ul cu antrenori din baza de date
        populateTrainerComboBox(trainerComboBox);

        frame.add(trainerLabel);
        frame.add(trainerComboBox);
        frame.add(selectButton);
        frame.add(backButton);

        selectButton.addActionListener(e -> {
            String selectedTrainer = (String) trainerComboBox.getSelectedItem();
            if (selectedTrainer != null) {
                JOptionPane.showMessageDialog(frame,
                        "Ai selectat antrenorul: " + selectedTrainer,
                        "Succes", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();

                // Notificăm MainMenu despre antrenorul selectat
                Integer trainerId = getTrainerId(selectedTrainer);
                if (trainerId != null) {
                    mainMenu.setSelectedTrainerId(trainerId); // Setăm ID-ul antrenorului în MainMenu

                    // Redeschidem meniul principal
                    mainMenu.showMenu(); // Acum fără argument
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Eroare la găsirea antrenorului.",
                            "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Te rog să selectezi un antrenor.",
                        "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            frame.dispose(); // Închide fereastra curentă
            mainMenu.showMenu(); // Revine la meniul principal
        });

        frame.setVisible(true);
    }

    // Funcție pentru a popula ComboBox cu antrenori
    private void populateTrainerComboBox(JComboBox<String> trainerComboBox) {
        String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/sala";
        String DATABASE_USER = "root";
        String DATABASE_PASSWORD = "denis";

        String query = "SELECT nume, prenume FROM Antrenor";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String trainerName = resultSet.getString("nume") + " " + resultSet.getString("prenume");
                trainerComboBox.addItem(trainerName); // Adăugăm numele antrenorilor în ComboBox
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Funcție pentru a obține ID-ul antrenorului pe baza numelui complet
    private Integer getTrainerId(String trainerName) {
        String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/sala";
        String DATABASE_USER = "root";
        String DATABASE_PASSWORD = "denis";

        String query = "SELECT id_antrenor FROM Antrenor WHERE CONCAT(nume, ' ', prenume) = ?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, trainerName); // Setăm numele complet al antrenorului

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id_antrenor"); // Returnăm ID-ul antrenorului
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null; // Returnăm null dacă nu găsim un antrenor
    }
}
