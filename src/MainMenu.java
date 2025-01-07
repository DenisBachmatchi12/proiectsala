import javax.swing.*;
import java.awt.*;

public class MainMenu {

    private Integer selectedTrainerId = null; // Variabilă pentru a reține ID-ul antrenorului selectat

    public void showMenu() {
        JFrame frame = new JFrame("Meniu Principal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(6, 1, 10, 10));
        frame.getContentPane().setBackground(Color.DARK_GRAY);

        JButton createSubscriptionButton = new JButton("Creare Abonament");
        createSubscriptionButton.setBackground(Color.BLACK);
        createSubscriptionButton.setForeground(Color.WHITE);
        JButton makeReservationButton = new JButton("Creare Rezervare");
        makeReservationButton.setBackground(Color.BLACK);
        makeReservationButton.setForeground(Color.WHITE);
        JButton selectTrainerButton = new JButton("Selectare Antrenor");
        selectTrainerButton.setBackground(Color.BLACK);
        selectTrainerButton.setForeground(Color.WHITE);
        JButton exitButton = new JButton("Ieșire");
        exitButton.setBackground(Color.BLACK);
        exitButton.setForeground(Color.WHITE);

        frame.add(createSubscriptionButton);
        frame.add(makeReservationButton);
        frame.add(selectTrainerButton);
        frame.add(exitButton);

        // Eveniment pentru Creare Abonament
        createSubscriptionButton.addActionListener(e -> {
            frame.dispose(); // Închide meniul principal
            CreateSubscription subscriptionApp = new CreateSubscription();
            subscriptionApp.showSubscriptionForm(); // Trimite frame-ul curent pentru a reveni
        });

        // Eveniment pentru Creare Rezervare
        makeReservationButton.addActionListener(e -> {
            if (selectedTrainerId == null) { // Verifică dacă antrenorul a fost selectat
                JOptionPane.showMessageDialog(frame,
                        "Te rog să selectezi un antrenor înainte de a crea o rezervare.",
                        "Eroare", JOptionPane.ERROR_MESSAGE);
            } else {
                frame.dispose(); // Închide meniul principal
                Reservation reservationApp = new Reservation(selectedTrainerId);
                reservationApp.showReservationWindow(); // Trimite frame-ul curent pentru a reveni
            }
        });

        // Eveniment pentru Selectare Antrenor
        selectTrainerButton.addActionListener(e -> {
            frame.dispose(); // Închide meniul principal
            TrainerSelection trainerSelectionApp = new TrainerSelection();
            trainerSelectionApp.showTrainerSelection(this); // Trimite referința către MainMenu
        });

        // Eveniment pentru Ieșire
        exitButton.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
    }

    // Setează ID-ul antrenorului selectat
    public void setSelectedTrainerId(Integer trainerId) {
        this.selectedTrainerId = trainerId;
    }
}
