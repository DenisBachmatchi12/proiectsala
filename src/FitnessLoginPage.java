import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FitnessLoginPage {

    private JFrame frame;
    private JTextField emailField;
    private JPasswordField passwordField;

    public FitnessLoginPage() {
        // Setări pentru fereastră
        frame = new JFrame("Fitness App - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(4, 1, 10, 10));

        // Panou pentru email
        JPanel emailPanel = new JPanel(new FlowLayout());
        JLabel emailLabel = new JLabel("Email: ");
        emailField = new JTextField(20);
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);

        // Panou pentru parolă
        JPanel passwordPanel = new JPanel(new FlowLayout());
        JLabel passwordLabel = new JLabel("Password: ");
        passwordField = new JPasswordField(20);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        // Buton de logare
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginAction());

        // Adăugăm componentele în fereastră
        frame.add(emailPanel);
        frame.add(passwordPanel);
        frame.add(loginButton);

        frame.setVisible(true);
    }

    // Clasă internă pentru gestionarea acțiunii de logare
    private class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            // Apelăm UserAuthenticator pentru autentificare
            // Aici poți redirecționa utilizatorul către altă fereastră
            if (UserAuthenticator.authenticate(email, password))
                JOptionPane.showMessageDialog(frame, "Login successful!");
            else {
                JOptionPane.showMessageDialog(frame, "Invalid email or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
