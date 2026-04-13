import javax.swing.*;
import java.awt.*;

public class Tampil_564755_GUI_041426 {
    static int[][] hotel = new int[7][5];

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            int choice = showMenu();

            if (choice == -1) break;

            switch (choice) {
                case 0:
                    displayRooms();
                    break;
                case 1:
                    processRoom(true);
                    break;
                case 2:
                    processRoom(false);
                    break;
                case 3:
                    running = false;
                    JOptionPane.showMessageDialog(null, "Exiting the program. Goodbye!");
                    break;
                default:
                    break;
            }
        }
    }

    private static void processRoom(boolean isCheckIn) {
        while (true) {
            int floor = getIntInput("Enter floor (1-7): ");
            if (floor == -1) return;
            int room = getIntInput("Enter room number (1-5): ");
            if (room == -1) return;

            if (!isValid(floor, room)) {
                JOptionPane.showMessageDialog(null, "Invalid floor or room number.");
                continue;
            }

            boolean occupied = hotel[floor - 1][room - 1] == 1;
            if (isCheckIn && occupied) {
                JOptionPane.showMessageDialog(null, "Room is already occupied.");
                continue;
            }
            if (!isCheckIn && !occupied) {
                JOptionPane.showMessageDialog(null, "Room is already vacant.");
                continue;
            }

            String action = isCheckIn ? "Checked in" : "Checked out";

            int confirm = JOptionPane.showConfirmDialog(
                null,
                "Confirm " + action + " for Floor " + floor + ", Room " + room + "?",
                action + " Confirmation",
                JOptionPane.YES_NO_CANCEL_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                hotel[floor - 1][room - 1] = isCheckIn ? 1 : 0;
                JOptionPane.showMessageDialog(null, action + " successfully.");
                return;
            } else if (confirm == JOptionPane.NO_OPTION) {
                continue;
            } else {
                return;
            }
        }
    }

    private static void displayRooms() {
        StringBuilder sb = new StringBuilder();
        for (int i = 6; i >= 0; i--) {
            sb.append("Floor ").append(i + 1).append(": ");
            for (int j = 0; j < hotel[i].length; j++) {
                sb.append(hotel[i][j] == 1 ? "[1] " : "[0] ");
            }
            sb.append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Hotel Room Status", JOptionPane.INFORMATION_MESSAGE);
    }

    private static int showMenu() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JButton viewBtn = new JButton("View Rooms");
        JButton checkInBtn = new JButton("Check In");
        JButton checkOutBtn = new JButton("Check Out");
        JButton exitBtn = new JButton("Exit");

        Dimension btnSize = new Dimension(200, 40);
        viewBtn.setPreferredSize(btnSize);
        checkInBtn.setPreferredSize(btnSize);
        checkOutBtn.setPreferredSize(btnSize);
        exitBtn.setPreferredSize(btnSize);

        panel.add(viewBtn);
        panel.add(checkInBtn);
        panel.add(checkOutBtn);
        panel.add(exitBtn);

        JDialog dialog = new JDialog();
        dialog.setTitle("Hotel Management System");
        dialog.setModal(true);
        dialog.setContentPane(panel);
        dialog.setSize(300, 250);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        final int[] choice = {-1};

        viewBtn.addActionListener(e -> { choice[0] = 0; dialog.dispose(); });
        checkInBtn.addActionListener(e -> { choice[0] = 1; dialog.dispose(); });
        checkOutBtn.addActionListener(e -> { choice[0] = 2; dialog.dispose(); });
        exitBtn.addActionListener(e -> { choice[0] = 3; dialog.dispose(); });

        dialog.setVisible(true);

        return choice[0];
    }

    private static int getIntInput(String message) {
        while (true) {
            String input = JOptionPane.showInputDialog(message);
            if (input == null) return -1;
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number.");
            }
        }
    }

    private static boolean isValid(int floor, int room) {
        return floor >= 1 && floor <= 7 && room >= 1 && room <= 5;
    }
}
