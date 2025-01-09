import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginInterface {

    public void showLoginWindow(JFrame previousFrame) {
        JFrame frame = new JFrame("Autentificare");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(4, 2, 10, 10));
        frame.getContentPane().setBackground(Color.DARK_GRAY);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);

        JTextField emailField = new JTextField();
        emailField.setBackground(Color.WHITE);
        emailField.setForeground(Color.BLACK);

        JLabel passwordLabel = new JLabel("Parolă:");
        passwordLabel.setForeground(Color.WHITE);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBackground(Color.GRAY);
        passwordField.setForeground(Color.WHITE);

        JButton loginButton = new JButton("Autentificare");
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.WHITE);

        JButton createAccountButton = new JButton("Creare Cont");
        createAccountButton.setBackground(Color.BLACK);
        createAccountButton.setForeground(Color.WHITE);

        frame.add(emailLabel);
        frame.add(emailField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(loginButton);
        frame.add(createAccountButton);

        // Eveniment pentru logare
        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (authenticateUser(email, password)) {
                JOptionPane.showMessageDialog(frame,
                        "Autentificare reușită!",
                        "Succes", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();

                MainMenu mainMenu = new MainMenu();
                mainMenu.showMenu();
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Autentificare eșuată. Verifică email-ul și parola.",
                        "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        });


        createAccountButton.addActionListener(e -> {
            frame.dispose();
            CreateAccount createAccountApp = new CreateAccount();
            createAccountApp.showCreateAccountWindow(frame);
        });

        frame.setVisible(true);
    }

    private boolean authenticateUser(String email, String password) {
        String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/sala";
        String DATABASE_USER = "root";
        String DATABASE_PASSWORD = "denis";

        String query = "SELECT * FROM Client WHERE email = ? AND parola = ?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
