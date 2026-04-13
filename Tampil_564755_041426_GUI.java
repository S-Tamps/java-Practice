import javax.swing.*;

public class Tampil_564755_041426_GUI {

    static int[][] hotel = new int[7][5];

    public static void main(String[] args) {

        boolean running = true;

        while (running) {
            String menu = """
                    === Hotel Management System ===
                    [1] View Rooms
                    [2] Check In
                    [3] Check Out
                    [4] Exit
                    """;

            String input = JOptionPane.showInputDialog(menu + "\nEnter your choice:");

            if (input == null) break;

            int choice = nxValidInt(input);

            switch (choice) {
                case 1:
                    displayRooms();
                    break;
                case 2:
                    checkIn();
                    break;
                case 3:
                    checkOut();
                    break;
                case 4:
                    running = false;
                    JOptionPane.showMessageDialog(null, "Exiting the system. Goodbye!");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice. Please try again.");
            }
        }
    }

    private static void checkIn() {
        int floor = getInput("Enter floor (1-7): ");
        int roomNum = getInput("Enter room number (1-5): ");

        if (floor < 1 || floor > 7 || roomNum < 1 || roomNum > 5) {
            JOptionPane.showMessageDialog(null, "Invalid floor or room number.");
        } else if (hotel[floor - 1][roomNum - 1] == 0) {
            hotel[floor - 1][roomNum - 1] = 1;
            JOptionPane.showMessageDialog(null, "Checked in successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Room is already occupied.");
        }
    }

    private static void checkOut() {
        int floor = getInput("Enter floor (1-7): ");
        int roomNum = getInput("Enter room number (1-5): ");

        if (floor < 1 || floor > 7 || roomNum < 1 || roomNum > 5) {
            JOptionPane.showMessageDialog(null, "Invalid floor or room number.");
        } else if (hotel[floor - 1][roomNum - 1] == 1) {
            hotel[floor - 1][roomNum - 1] = 0;
            JOptionPane.showMessageDialog(null, "Checked out successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Room is already vacant.");
        }
    }

    private static void displayRooms() {
        StringBuilder sb = new StringBuilder("Rooms:\n");

        for (int i = 0; i < hotel.length; i++) {
            sb.append("Floor ").append(7 - i).append(": ");
            for (int j = 0; j < hotel[i].length; j++) {
                sb.append("[").append(hotel[6 - i][j]).append("] ");
            }
            sb.append("\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private static int getInput(String message) {
        while (true) {
            String input = JOptionPane.showInputDialog(message);
            if (input == null) return -1;

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number.");
            }
        }
    }

    private static int nxValidInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}