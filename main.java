import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class main {
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("KCRJTE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setLayout(new BorderLayout()); // Use BorderLayout for main layout

        // Create a label for the header
        JLabel label = new JLabel("KCR Java Text Editor - KCRJTE", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 40));
        label.setForeground(Color.BLACK);

        // Create a JTextArea for editing text
        JTextArea textField = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textField);

        // Load previous content from buffer.dat if it exists
        File bufferFile = new File("buffer.dat");
        if (bufferFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(bufferFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    textField.append(line + "\n");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error loading buffer: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        // Create buttons
        JButton exitButton = new JButton("Exit");
        JButton saveButton = new JButton("Save");

        // Add functionality to the Save button
        saveButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showSaveDialog(frame);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write(textField.getText());
                    JOptionPane.showMessageDialog(frame, "File saved successfully!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error saving file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add functionality to the Exit button
        exitButton.addActionListener(e -> {
            // Save the current text to buffer.dat
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(bufferFile))) {
                writer.write(textField.getText());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error saving buffer: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Exit the application
            System.exit(0);
        });

        // Add components to the frame
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Space between buttons
        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createVerticalGlue());

        frame.add(label, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER); // Add scrollable text area
        frame.add(buttonPanel, BorderLayout.EAST);

        // Set frame visibility
        frame.setVisible(true);
    }
}
