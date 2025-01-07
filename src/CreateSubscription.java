import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

public class CreateSubscription {

    public void showSubscriptionForm() {
        JFrame frame = new JFrame("Creare Abonament");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel typeLabel = new JLabel("Tip Abonament:");
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"Lunar", "6 luni", "1 an"});

        JLabel durationLabel = new JLabel("Durata:");
        JTextField durationField = new JTextField();
        durationField.setEditable(false); // Câmp calculat automat, nu editabil

        JLabel priceLabel = new JLabel("Preț:");
        JTextField priceField = new JTextField();
        priceField.setEditable(false); // Prețul este calculat automat, nu editabil

        JButton calculateButton = new JButton("Calculează Durata");
        JButton saveButton = new JButton("Salvează");

        frame.add(typeLabel);
        frame.add(typeComboBox);
        frame.add(durationLabel);
        frame.add(durationField);
        frame.add(priceLabel);
        frame.add(priceField);
        frame.add(calculateButton);
        frame.add(saveButton);

        // Eveniment pentru calcularea duratei și prețului
        calculateButton.addActionListener(e -> {
            String selectedType = (String) typeComboBox.getSelectedItem();
            if (selectedType != null) {
                durationField.setText(calculateDuration(selectedType));
                priceField.setText(String.valueOf(calculatePrice(selectedType)));
            }
        });

        // Eveniment pentru salvarea abonamentului
        saveButton.addActionListener(e -> {
            String selectedType = (String) typeComboBox.getSelectedItem();
            String duration = durationField.getText();
            String priceText = priceField.getText();

            if (selectedType == null || duration.isEmpty() || priceText.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Te rog să completezi toate câmpurile și să calculezi durata și prețul.",
                        "Eroare", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int price = Integer.parseInt(priceText);

                if (createSubscription(selectedType, duration, price)) {
                    JOptionPane.showMessageDialog(frame,
                            "Abonamentul a fost creat cu succes!",
                            "Succes", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Eroare la crearea abonamentului.",
                            "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame,
                        "Prețul trebuie să fie un număr valid.",
                        "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.setVisible(true);
    }

    private String calculateDuration(String type) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate;

        switch (type) {
            case "Lunar":
                endDate = startDate.plusDays(30);
                break;
            case "6 luni":
                endDate = startDate.plusDays(180);
                break;
            case "1 an":
                endDate = startDate.plusDays(365);
                break;
            default:
                throw new IllegalArgumentException("Tip de abonament necunoscut: " + type);
        }

        return startDate + " până la " + endDate;
    }

    private int calculatePrice(String type) {
        switch (type) {
            case "Lunar":
                return 150;
            case "6 luni":
                return 800;
            case "1 an":
                return 1500;
            default:
                throw new IllegalArgumentException("Tip de abonament necunoscut: " + type);
        }
    }

    private boolean createSubscription(String type, String duration, int price) {
        String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/sala";
        String DATABASE_USER = "root";
        String DATABASE_PASSWORD = "denis";

        String query = "INSERT INTO Abonament (tip, durata, pret) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, type);
            preparedStatement.setString(2, duration);
            preparedStatement.setInt(3, price);

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
