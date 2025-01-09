import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class CreateAccount {

    public void showCreateAccountWindow(JFrame previousFrame) {
        JFrame frame = new JFrame("Creare Cont");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(7, 1, 10, 10));
        frame.getContentPane().setBackground(Color.DARK_GRAY);

        JLabel nameLabel = new JLabel("Nume:");
        nameLabel.setForeground(Color.WHITE);
        JTextField nameField = new JTextField();
        nameField.setBackground(Color.WHITE);
        nameField.setForeground(Color.BLACK);

        JLabel surnameLabel = new JLabel("Prenume:");
        surnameLabel.setForeground(Color.WHITE);
        JTextField surnameField = new JTextField();
        surnameField.setBackground(Color.WHITE);
        surnameField.setForeground(Color.BLACK);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);
        JTextField emailField = new JTextField();
        emailField.setBackground(Color.WHITE);
        emailField.setForeground(Color.BLACK);

        JLabel phoneLabel = new JLabel("Număr de telefon:");
        phoneLabel.setForeground(Color.WHITE);
        JTextField phoneField = new JTextField();
        phoneField.setBackground(Color.WHITE);
        phoneField.setForeground(Color.BLACK);

        JLabel passwordLabel = new JLabel("Parolă:");
        passwordLabel.setForeground(Color.WHITE);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBackground(Color.GRAY);
        passwordField.setForeground(Color.WHITE);

        JButton createButton = new JButton("Crează Cont");
        createButton.setBackground(Color.BLACK);
        createButton.setForeground(Color.WHITE);

        JButton backButton = new JButton("Înapoi");
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);

        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(surnameLabel);
        frame.add(surnameField);
        frame.add(emailLabel);
        frame.add(emailField);
        frame.add(phoneLabel);
        frame.add(phoneField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(createButton);
        frame.add(backButton);


        createButton.addActionListener(e -> {
            String name = nameField.getText();
            String surname = surnameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String password = new String(passwordField.getPassword());

            if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Toate câmpurile trebuie completate.",
                        "Eroare", JOptionPane.ERROR_MESSAGE);
            } else if (!phone.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(frame,
                        "Numărul de telefon trebuie să conțină doar 10 cifre.",
                        "Eroare", JOptionPane.ERROR_MESSAGE);
            } else {
                createUser(name, surname, email, phone, password);
            }
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            previousFrame.setVisible(true);
        });

        frame.setVisible(true);
    }

    private void createUser(String name, String surname, String email, String phone, String password) {
        String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/sala";
        String DATABASE_USER = "root";
        String DATABASE_PASSWORD = "denis";

        String query = "INSERT INTO Client (nume, prenume, email, numar_telefon, parola) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5, password);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Contul a fost creat cu succes!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Eroare la crearea contului: " + ex.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }
}
