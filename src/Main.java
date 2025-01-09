import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        LoginInterface loginInterface = new LoginInterface();

        JFrame dummyFrame = new JFrame();
        dummyFrame.setSize(400, 300);
        dummyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        loginInterface.showLoginWindow(dummyFrame);
    }
}
