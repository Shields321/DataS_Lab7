package DataS_Lab7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyGUI extends JFrame {
    private JTextField textField1;
    private JTextField textField2;

    public MyGUI() {
        // Set up the frame
        setTitle("Shortest Path");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Create a panel to hold components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        // Create labels
        JLabel label1 = new JLabel("Enter Vertex 1:");
        JLabel label2 = new JLabel("Enter Vertex 2:");

        // Create text fields
        textField1 = new JTextField(20);
        textField2 = new JTextField(20);

        // Create submit button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text1 = textField1.getText(); // Get text from textField1
                String text2 = textField2.getText(); // Get text from textField2
                // Pass both texts to another class or perform any other operation
                GUI.processText(text1, text2);
                // Clear text fields
                textField1.setText("");
                textField2.setText("");
            }
        });

        // Add components to the panel
        panel.add(label1);
        panel.add(textField1);
        panel.add(label2);
        panel.add(textField2);
        panel.add(new JLabel()); // Empty label for alignment
        panel.add(submitButton); // Add submit button

        // Add the panel to the frame
        add(panel);

        // Make the frame visible
        setVisible(true);
    }
}
