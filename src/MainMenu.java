import javax.swing.*;
import java.awt.*;

public class MainMenu {

    private Integer selectedTrainerId = null;

    public void showMenu() {
        JFrame frame = new JFrame("Meniu Principal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(7, 1, 10, 10));
        menuPanel.setBackground(Color.DARK_GRAY);

        JButton createSubscriptionButton = new JButton("Creare Abonament");
        createSubscriptionButton.setBackground(Color.BLACK);
        createSubscriptionButton.setForeground(Color.WHITE);
        JButton makeReservationButton = new JButton("Creare Rezervare");
        makeReservationButton.setBackground(Color.BLACK);
        makeReservationButton.setForeground(Color.WHITE);
        JButton selectTrainerButton = new JButton("Selectare Antrenor");
        selectTrainerButton.setBackground(Color.BLACK);
        selectTrainerButton.setForeground(Color.WHITE);
        JButton viewInfoButton = new JButton("Vezi Abonamente și Rezervări");
        viewInfoButton.setBackground(Color.BLACK);
        viewInfoButton.setForeground(Color.WHITE);
        JButton exitButton = new JButton("Ieșire");
        exitButton.setBackground(Color.BLACK);
        exitButton.setForeground(Color.WHITE);

        menuPanel.add(createSubscriptionButton);
        menuPanel.add(makeReservationButton);
        menuPanel.add(selectTrainerButton);
        menuPanel.add(viewInfoButton);
        menuPanel.add(exitButton);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.LIGHT_GRAY);

        // Adăugăm panourile în fereastra principală
        frame.add(menuPanel, BorderLayout.WEST);
        frame.add(infoPanel, BorderLayout.EAST);

        createSubscriptionButton.addActionListener(e -> {
            frame.dispose();
            CreateSubscription subscriptionApp = new CreateSubscription();
            subscriptionApp.showSubscriptionForm();
        });

        makeReservationButton.addActionListener(e -> {
            if (selectedTrainerId == null) {
                JOptionPane.showMessageDialog(frame,
                        "Te rog să selectezi un antrenor înainte de a crea o rezervare.",
                        "Eroare", JOptionPane.ERROR_MESSAGE);
            } else {
                frame.dispose();
                Reservation reservationApp = new Reservation(selectedTrainerId);
                reservationApp.showReservationWindow();
            }
        });

        // Eveniment pentru Selectare Antrenor
        selectTrainerButton.addActionListener(e -> {
            frame.dispose();
            TrainerSelection trainerSelectionApp = new TrainerSelection();
            trainerSelectionApp.showTrainerSelection(this);
        });


        viewInfoButton.addActionListener(e -> {
            frame.dispose();
            ViewSubscriptionsAndReservations viewInfoApp = new ViewSubscriptionsAndReservations();
            viewInfoApp.showSubscriptionsAndReservationsWindow(frame);
        });


        exitButton.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
    }

    public void setSelectedTrainerId(Integer trainerId) {
        this.selectedTrainerId = trainerId;
    }
}
