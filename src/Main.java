import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Creăm o instanță a ferestrei de logare
        LoginInterface loginInterface = new LoginInterface();

        // Creăm un JFrame (care este fereastra principală a aplicației)
        JFrame dummyFrame = new JFrame();
        dummyFrame.setSize(400, 300); // Setăm dimensiunea ferestrei de logare temporare
        dummyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Închidem aplicația dacă se închide fereastra

        // Arătăm fereastra de logare
        loginInterface.showLoginWindow(dummyFrame);  // Trimitem dummyFrame pentru a reveni la el
    }
}
