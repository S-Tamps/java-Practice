import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;

public class Tampil_564755_GUI_043026 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
        new EmployeeManagementSystem().setVisible(true));
    }
}

class EmployeeManagementSystem extends JFrame {

    private static JTextField txtID, txtName, txtAge, txtDOB, txtNationality,
            txtContact, txtEmail, txtDepartment, txtJobTitle;

    private static JComboBox<String> cmbCivilStatus;
    private static JRadioButton rbMale, rbFemale;
    private static ButtonGroup genderGroup;

    private static JTable table;
    private static DefaultTableModel model;

    private static final File file = new File("Log Folder/employees.log");

    public EmployeeManagementSystem() {
        initFrame();
        initUI();
    }

    private void initFrame() {
        setTitle("Employee Management System");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
    }

    private void initUI() {
        JPanel formPanel = new JPanel(new GridLayout(6, 4, 10, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        txtID = new JTextField();
        txtName = new JTextField();
        txtAge = new JTextField();
        txtDOB = new JTextField();
        txtNationality = new JTextField();
        txtContact = new JTextField();
        txtEmail = new JTextField();
        txtDepartment = new JTextField();
        txtJobTitle = new JTextField();

        cmbCivilStatus = new JComboBox<>(new String[]{
                "Single", "Married", "Widowed", "Separated", "Divorced"
        });

        rbMale = new JRadioButton("Male");
        rbFemale = new JRadioButton("Female");

        genderGroup = new ButtonGroup();
        genderGroup.add(rbMale);
        genderGroup.add(rbFemale);

        JPanel genderPanel = new JPanel();
        genderPanel.add(rbMale);
        genderPanel.add(rbFemale);

        formPanel.add(new JLabel("Employee ID"));
        formPanel.add(txtID);
        formPanel.add(new JLabel("Age"));
        formPanel.add(txtAge);

        formPanel.add(new JLabel("Full Name"));
        formPanel.add(txtName);
        formPanel.add(new JLabel("Civil Status"));
        formPanel.add(cmbCivilStatus);

        formPanel.add(new JLabel("Date of Birth"));
        formPanel.add(txtDOB);
        formPanel.add(new JLabel("Nationality"));
        formPanel.add(txtNationality);

        formPanel.add(new JLabel("Gender"));
        formPanel.add(genderPanel);
        formPanel.add(new JLabel("Contact Number"));
        formPanel.add(txtContact);

        formPanel.add(new JLabel("Email"));
        formPanel.add(txtEmail);
        formPanel.add(new JLabel("Department"));
        formPanel.add(txtDepartment);

        formPanel.add(new JLabel("Job Title / Position"));
        formPanel.add(txtJobTitle);

        JButton btnAdd = new JButton("Add Employee");
        formPanel.add(btnAdd);

        add(formPanel, BorderLayout.NORTH);

        String[] columns = {
                "Employee ID", "Full Name", "Birth", "Age", "Civil Status",
                "Nationality", "Gender", "Contact", "Email", "Department", "Job Title"
        };

        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        btnAdd.addActionListener(e -> addEmployee());

        loadData();
    }

    private void addEmployee() {
        try {
            String gender = rbMale.isSelected() ? "Male" :
                            rbFemale.isSelected() ? "Female" : "";

            String[] data = {
                    txtID.getText(),
                    txtName.getText(),
                    txtDOB.getText(),
                    txtAge.getText(),
                    cmbCivilStatus.getSelectedItem().toString(),
                    txtNationality.getText(),
                    gender,
                    txtContact.getText(),
                    txtEmail.getText(),
                    txtDepartment.getText(),
                    txtJobTitle.getText()
            };

            String line = String.join("#", data);

            try (PrintWriter out = getWriter(file.toPath().toString())) {
                out.println(line);
            }

            model.addRow(data);
            clearFields();

        } catch (Exception e) {
            Logger.logError(e);
            JOptionPane.showMessageDialog(this, "Error saving employee data.");
        }
    }

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

        return new PrintWriter(bw);
    }

    private void loadData() {
        try {
            if (!file.exists()) return;

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    model.addRow(line.split("#"));
                }
            }

        } catch (Exception e) {
            Logger.logError(e);
            JOptionPane.showMessageDialog(this, "Error loading data.");
        }
    }

    private void clearFields() {
        txtID.setText("");
        txtName.setText("");
        txtAge.setText("");
        txtDOB.setText("");
        txtNationality.setText("");
        txtContact.setText("");
        txtEmail.setText("");
        txtDepartment.setText("");
        txtJobTitle.setText("");
        genderGroup.clearSelection();
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