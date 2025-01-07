import javax.swing.*;
import java.awt.*;

public class AppStyle {

    public static void applyStyle() {
        // Setare culori globale
        UIManager.put("Label.foreground", Color.WHITE); // Text alb pentru JLabel
        UIManager.put("Panel.background", Color.DARK_GRAY); // Fundal gri pentru JPanel
        UIManager.put("TextField.background", Color.WHITE); // Fundal alb pentru JTextField
        UIManager.put("TextField.foreground", Color.BLACK); // Text negru pentru JTextField
        UIManager.put("ComboBox.background", Color.GRAY); // Fundal gri pentru JComboBox
        UIManager.put("ComboBox.foreground", Color.WHITE); // Text alb pentru JComboBox
        UIManager.put("Button.background", Color.BLACK); // Fundal negru pentru JButton
        UIManager.put("Button.foreground", Color.WHITE); // Text alb pentru JButton
        UIManager.put("OptionPane.background", Color.DARK_GRAY); // Fundal gri pentru JOptionPane
        UIManager.put("OptionPane.foreground", Color.WHITE); // Text alb pentru JOptionPane
        UIManager.put("OptionPane.messageForeground", Color.WHITE); // Text alb în mesaje JOptionPane
    }
}
