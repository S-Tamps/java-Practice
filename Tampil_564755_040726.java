import java.util.*;

public class Tampil_564755_040726 {

    static int[][] hotel = new int[7][5];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        boolean running = true;

        while (running) {
            displayMenu();
            System.out.print("Enter your choice: ");
            int choice = nxValidInt(sc);
            System.out.println();

            switch (choice) {
                case 1:
                    displayRooms();
                    break;
                case 2:
                    hotel = checkIn(hotel, sc);
                    break;
                case 3:
                    hotel = checkOut(hotel, sc);
                    break;
                case 4:
                    running = false;
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.\n");
            }
        }

        sc.close();
    }

    private static int[][] checkIn(int[][] hotel, Scanner sc) {
        System.out.print("Enter floor (1-7): ");
        int floor = nxValidInt(sc);
        System.out.print("Enter room number (1-5): ");
        int roomNum = nxValidInt(sc);
        if (floor < 1 || floor > 7 || roomNum < 1 || roomNum > 5) {
            System.out.println("Invalid floor or room number. Please try again.\n");
        } else if (hotel[floor - 1][roomNum - 1] == 0) {
            hotel[floor - 1][roomNum - 1] = 1;
            System.out.println("Checked in successfully.\n");
        } else {
            System.out.println("Room is already occupied.\n");
        }
        return hotel;
    }

    private static int[][] checkOut(int[][] hotel, Scanner sc) {
        System.out.print("Enter floor (1-7): ");
        int floor = nxValidInt(sc);
        System.out.print("Enter room number (1-5): ");
        int roomNum = nxValidInt(sc);
        if (floor < 1 || floor > 7 || roomNum < 1 || roomNum > 5) {
            System.out.println("Invalid floor or room number. Please try again.\n");
        } else if (hotel[floor - 1][roomNum - 1] == 1) {
            hotel[floor - 1][roomNum - 1] = 0;
            System.out.println("Checked out successfully.\n");
        } else {
            System.out.println("Room is already vacant.\n");
        }
        return hotel;
    }

    private static void displayRooms() {
        System.out.println("Rooms: ");
        for (int i = 0; i < hotel.length; i++) {
            System.out.print("Floor " + (7 - i) + ": ");
            for (int j = 0; j < hotel[i].length; j++) {
                System.out.print("[" + hotel[6 - i][j] + "] ");
            }
            System.out.println("");
        }
        System.out.println();
    }

    private static void displayMenu() {
        System.out.println("=== Hotel Management System ===");
        System.out.println("[1] View Rooms");
        System.out.println("[2] Check In");
        System.out.println("[3] Check Out");
        System.out.println("[4] Exit");
    }

    private static int nxValidInt(Scanner sc) {
        while (true) {
            if (!sc.hasNextInt()) {
                System.out.print("Invalid input. Please enter a number: ");
                sc.next();
                continue;
            }
            return sc.nextInt();
        }
    }
}