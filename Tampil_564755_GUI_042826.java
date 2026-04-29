package Test;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Tampil_564755_GUI_042826 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}

class MainFrame extends JFrame {

    private JTextField receiptField, storeNameField, totalCostField;
    private JTextField taxField, finalAmountField;
    private DefaultTableModel tableModel;

    private static final String LOG_PATH = "Log Folder/data.log";

    private String[] receiptTab, storeTab, totalTab, taxTab, finalTab;

    public MainFrame() {
        initFrame();
        initUI();
    }

    private void initFrame() {
        setTitle("Expense Tracker");
        setSize(750, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));
    }

    private void initUI() {

        JLabel titleLabel = new JLabel("Expense Tracker", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        add(mainPanel, BorderLayout.CENTER);

        JPanel leftPanel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        receiptField = new JTextField();
        storeNameField = new JTextField();
        totalCostField = new JTextField();
        taxField = new JTextField();
        finalAmountField = new JTextField();

        taxField.setEditable(false);
        finalAmountField.setEditable(false);

        inputPanel.add(new JLabel("Receipt:"));
        inputPanel.add(receiptField);

        inputPanel.add(new JLabel("Store Name:"));
        inputPanel.add(storeNameField);

        inputPanel.add(new JLabel("Total Cost:"));
        inputPanel.add(totalCostField);

        inputPanel.add(new JLabel(""));
        inputPanel.add(new JLabel(""));

        inputPanel.add(new JLabel("Tax (12%):"));
        inputPanel.add(taxField);

        inputPanel.add(new JLabel("Final Amount:"));
        inputPanel.add(finalAmountField);

        leftPanel.add(inputPanel, BorderLayout.CENTER);

        JButton recordButton = new JButton("Record");
        JButton clearButton = new JButton("Clear");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(recordButton);
        buttonPanel.add(clearButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0));

        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        String[] columns = {"Receipt", "Store", "Total", "Tax", "Final"};
        tableModel = new DefaultTableModel(columns, 0);

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JSplitPane splitPane = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            leftPanel,
            scrollPane
        );

        splitPane.setDividerLocation(300);
        splitPane.setResizeWeight(0);
        splitPane.setDividerSize(8);
        splitPane.setOneTouchExpandable(true);

        mainPanel.add(splitPane, BorderLayout.CENTER);

        totalCostField.addActionListener(e -> calculate());

        recordButton.addActionListener(e -> {
            if (!isValidInput()) return;

            calculate();
            saveRecord();
            addToTable();
        });

        clearButton.addActionListener(e -> clearFields());
    }

    private boolean isValidInput() {
        String receipt = receiptField.getText().strip();
        String store = storeNameField.getText().strip();
        String total = totalCostField.getText().strip();

        if (receipt.isBlank() || store.isBlank() || total.isBlank()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.");
            return false;
        }

        try {
            double totalCost = Double.parseDouble(total);
            if (totalCost < 0) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for Total Cost.");
                return false;
            }
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for Total Cost.");
            return false;
        }
    }

    private void calculate() {
        try {
            double totalCost = Double.parseDouble(totalCostField.getText());
            double tax = totalCost * 0.12;
            double finalAmount = totalCost + tax;

            taxField.setText(String.format("%.2f", tax));
            finalAmountField.setText(String.format("%.2f", finalAmount));

        } catch (NumberFormatException ex) {
            Logger.logError(ex);
        }
    }

    private void addToTable() {
        try {
            tableModel.setRowCount(0);

            if (checkFile()) {
                for (int i = 0; i < receiptTab.length; i++) {
                    tableModel.addRow(new Object[]{
                        receiptTab[i], storeTab[i], totalTab[i], taxTab[i], finalTab[i]
                    });
                }
            }
        } catch (Exception e) {
            Logger.logError(e);
        }
    }

    private boolean checkFile() {
        try {
            String line;
            int count = 0;

            try (BufferedReader br = new BufferedReader(new FileReader(LOG_PATH))) {
                while ((line = br.readLine()) != null) {
                    if (!line.trim().isEmpty()) count++;
                }
            }

            receiptTab = new String[count];
            storeTab = new String[count];
            totalTab = new String[count];
            taxTab = new String[count];
            finalTab = new String[count];

            int idx = 0;

            try (BufferedReader br = new BufferedReader(new FileReader(LOG_PATH))) {
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;

                    String[] data = line.trim().split(",");
                    if (data.length != 5) continue;

                    receiptTab[idx] = data[0].trim();
                    storeTab[idx] = data[1].trim();
                    totalTab[idx] = data[2].trim();
                    taxTab[idx] = data[3].trim();
                    finalTab[idx] = data[4].trim();

                    idx++;
                }
            }

            return idx > 0;

        } catch (Exception e) {
            Logger.logError(e);
            return false;
        }
    }

    private void saveRecord() {

        if (
            receiptField.getText().isBlank() ||
            storeNameField.getText().isBlank() ||
            totalCostField.getText().isBlank()
        ) return;

        String record = String.format(
            "%s, %s, %s, %s, %s",
            receiptField.getText(),
            storeNameField.getText(),
            totalCostField.getText(),
            taxField.getText(),
            finalAmountField.getText()
        );

        try {
            Path path = Paths.get(LOG_PATH);

            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }

            Files.write(path,
                (record + System.lineSeparator()).getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            );

        } catch (IOException ex) {
            Logger.logError(ex);
        }
    }

    private void clearFields() {
        receiptField.setText("");
        storeNameField.setText("");
        totalCostField.setText("");
        taxField.setText("");
        finalAmountField.setText("");
    }
}

class Logger {
    private static final String ERROR_PATH = "Log Folder/error.log";

    private static PrintWriter getWriter(String path) throws IOException {
        Path filePath = Paths.get(path);

        if (filePath.getParent() != null) {
            Files.createDirectories(filePath.getParent());
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
            System.err.println("Logging failed: " + ex.getMessage());
        }
    }
}