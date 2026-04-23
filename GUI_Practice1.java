import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;

import javax.swing.*;

/**
 * Entry point
 */
public class GUI_Practice1 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Frame frame = new Frame();
            frame.setVisible(true);
        });
    }
}

/**
 * Main GUI Window
 */
class Frame extends JFrame {

    private JTextArea textArea;

    public Frame() {
        initFrame();
        initUI();
        simulateError(); // for testing logger
    }

    private void initFrame() {
        setTitle("Test GUI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void initUI() {
        // Text area
        textArea = new JTextArea();
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Panel for buttons
        JPanel panel = new JPanel();

        JButton openBtn = new JButton("Open");
        JButton saveBtn = new JButton("Save");

        panel.add(openBtn);
        panel.add(saveBtn);

        add(panel, BorderLayout.NORTH);

        // Open file action
        openBtn.addActionListener(e -> openFile());

        // Save file action
        saveBtn.addActionListener(e -> saveFile());
    }

    // 🔹 OPEN FILE (basic File handling)
    private void openFile() {
        JFileChooser chooser = new JFileChooser();

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                textArea.setText(""); // clear first
                String line;

                while ((line = reader.readLine()) != null) {
                    textArea.append(line + "\n");
                }

            } catch (IOException e) {
                Logger.logError(e);
                JOptionPane.showMessageDialog(this, "Error reading file");
            }
        }
    }

    // 🔹 SAVE FILE (basic File handling)
    private void saveFile() {
        JFileChooser chooser = new JFileChooser();

        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();

            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.print(textArea.getText());
            } catch (IOException e) {
                Logger.logError(e);
                JOptionPane.showMessageDialog(this, "Error saving file");
            }
        }
    }

    // Simulate an error to test logging
    private void simulateError() {
        try {
            int x = 10 / 0; // intentional error
        } catch (Exception e) {
            Logger.logError(e);
            JOptionPane.showMessageDialog(this, "An error occurred!");
        }
    }
}

/**
 * Logger Class
 */
class Logger {

    private static final String ERROR_PATH = "Test/error_log.txt";

    private static PrintWriter getWriter(String path) throws IOException {
        Path filePath = Paths.get(path);

        Path parentDir = filePath.getParent();
        if (parentDir != null) {
            Files.createDirectories(parentDir);
        }

        BufferedWriter bw = Files.newBufferedWriter(
            filePath,
            StandardCharsets.UTF_8,
            StandardOpenOption.CREATE,
            StandardOpenOption.APPEND
        );

        return new PrintWriter(bw, true);
    }

    public static void logError(Exception e) {
        try (PrintWriter out = getWriter(ERROR_PATH)) {
            out.println("==== ERROR ====");
            out.println("Time: " + LocalDateTime.now());
            out.println("Type: " + e.getClass().getSimpleName());
            out.println("Message: " + e.getMessage());
            out.println("Stack Trace:");
            e.printStackTrace(out);
            out.println("================");
            out.println();
        } catch (IOException ex) {
            System.err.println("Failed to write error log");
        }
    }
}