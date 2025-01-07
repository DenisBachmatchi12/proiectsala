import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Asigură-te că UI-ul se lansează pe thread-ul evenimentelor Swing
        SwingUtilities.invokeLater(() -> {
            new FitnessLoginPage(); // Creează fereastra de login
        });
    }
}