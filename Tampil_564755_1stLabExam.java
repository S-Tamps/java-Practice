import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import javax.swing.*;

public class Tampil_564755_1stLabExam {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}

class MainFrame extends JFrame {
    private JTextField receiptField;

    public MainFrame() {
        initFrame();
        initUI();
    }

    private void initFrame() {
        setTitle("Expense Tracker");
        setSize(350, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
    }

    private void initUI() {
        JLabel titleLabel = new JLabel("Expense Tracker", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        Dimension textFieldSize = new Dimension(300, 25);
        JTextField storeNameField = new JTextField();
        JTextField totalCostField = new JTextField();

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 1, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(inputPanel, BorderLayout.CENTER);

        JLabel receiptLabel = new JLabel("Receipt:");
        receiptField = new JTextField();
        receiptField.setPreferredSize(textFieldSize);
        inputPanel.add(receiptLabel);
        inputPanel.add(receiptField);

        JLabel storeNameLabel = new JLabel("Store Name:");
        storeNameField.setPreferredSize(textFieldSize);
        inputPanel.add(storeNameLabel);
        inputPanel.add(storeNameField);

        JLabel totalCostLabel = new JLabel("Total Cost:");
        totalCostField.setPreferredSize(textFieldSize);
        inputPanel.add(totalCostLabel);
        inputPanel.add(totalCostField);

        inputPanel.add(new JLabel());
        inputPanel.add(new JLabel());

        JLabel taxLabel = new JLabel("Tax (12%): ");
        JTextField taxField = new JTextField();
        taxField.setEditable(false);
        inputPanel.add(taxLabel);
        inputPanel.add(taxField);

        JLabel finalAmountLabel = new JLabel("Final Amount: ");
        JTextField finalAmountField = new JTextField();
        finalAmountField.setEditable(false);
        inputPanel.add(finalAmountLabel);
        inputPanel.add(finalAmountField);

        totalCostField.addActionListener(e -> {
            try {
                double totalCost = Double.parseDouble(totalCostField.getText());
                double tax = totalCost * 0.12;
                double finalAmount = totalCost + tax;
                taxField.setText(String.format("%.2f", tax));
                finalAmountField.setText(String.format("%.2f", finalAmount));
            } catch (NumberFormatException ex) {
                Logger.logError(ex);
            }
        });
        JButton recordButton = new JButton("Record");
        JButton clearButton = new JButton("Clear");
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(recordButton);
        buttonPanel.add(clearButton);
        add(buttonPanel, BorderLayout.SOUTH);

        clearButton.addActionListener(e -> {
            receiptField.setText("");
            storeNameField.setText("");
            totalCostField.setText("");
            taxField.setText("");
            finalAmountField.setText("");
        });

        recordButton.addActionListener(e -> {
            String receipt = receiptField.getText();
            String storeName = storeNameField.getText();
            String totalCost = totalCostField.getText();
            String tax = taxField.getText();
            String finalAmount = finalAmountField.getText();

            String record = String.format("Receipt: %s, Store Name: %s, Total Cost: %s, Tax: %s, Final Amount: %s",
                    receipt, storeName, totalCost, tax, finalAmount);

            try {
                Path logPath = Paths.get("Log Folder/data.txt");
                Files.createDirectories(logPath.getParent());
                Files.write(logPath, (record + System.lineSeparator()).getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException ex) {
                Logger.logError(ex);
            }
        });

        recordButton.addActionListener(e -> {
            try {
                double totalCost = Double.parseDouble(totalCostField.getText());
                double tax = totalCost * 0.12;
                double finalAmount = totalCost + tax;
                taxField.setText(String.format("%.2f", tax));
                finalAmountField.setText(String.format("%.2f", finalAmount));
            } catch (NumberFormatException ex) {
                Logger.logError(ex);
            }
        });
    }
}

class Logger {
    private static final String ERROR_PATH = "Log Folder/error.log";

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
            out.println("===== ERROR =====");
            out.println("Timestamp: " + LocalDateTime.now());
            out.println("Message: " + e.getMessage());
            out.println("Stack Trace:");
            e.printStackTrace(out);
            out.println("=================");
            out.println();
        } catch (IOException ex) {
            System.err.println("Error Writing to Log File: " + e.getMessage());
        }
    }
}