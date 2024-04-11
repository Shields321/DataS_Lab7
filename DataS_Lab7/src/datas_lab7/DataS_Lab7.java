package DataS_Lab7;

import javax.swing.*;

public class DataS_Lab7 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI app = new GUI();            
            app.setSize(800, 600);
            app.setVisible(true);
            MyGUI gui1 = new MyGUI();
            gui1.setVisible(true);
        });
    }
}
