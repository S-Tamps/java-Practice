import java.util.*;

public class HotelReservationSystem {

    static String name, gender, roomType, orders;
    static int age, nights;
    static double roomCost, foodTotal, totalBill;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        guestDetails(sc);
        roomSelection(sc);
        foodOrder(sc);
        billing(sc);

        sc.close();
    }

    private static void guestDetails(Scanner sc) {
        System.out.print("Enter Name: ");
        name = sc.nextLine();

        System.out.print("Enter Age: ");
        age = nxPositiveInt(sc);

        gender = getGender(sc);
    }

    private static void roomSelection(Scanner sc) {
        System.out.println("\nRoom Types:");
        System.out.println("1. Small Room - ₱500/night");
        System.out.println("2. Medium Room - ₱700/night");
        System.out.println("3. Large Room - ₱1000/night");

        System.out.print("Select Room (1-3): ");
        int roomChoice = nxInt(sc);

        double roomPrice = 0;
        boolean validChoice = false;

        while (!validChoice) {
            switch (roomChoice) {
                case 1:
                    roomType = "Small Room";
                    roomPrice = 500;
                    validChoice = true;
                    break;
                case 2:
                    roomType = "Medium Room";
                    roomPrice = 700;
                    validChoice = true;
                    break;
                case 3:
                    roomType = "Large Room";
                    roomPrice = 1000;
                    validChoice = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
                    System.out.print("Select Room (1-3): ");
                    roomChoice = nxInt(sc);
            }
        }

        System.out.print("Enter number of nights: ");
        nights = nxPositiveInt(sc);

        roomCost = roomPrice * nights;
    }

    private static void foodOrder(Scanner sc) {
        int choice;
        orders = "";
        foodTotal = 0;
        do {
            System.out.println("\nSelect from the following menu (0 to finish):");
            System.out.println("1. Coke - ₱50");
            System.out.println("2. Rice - ₱5");
            System.out.println("3. Chicken Adobo - ₱100");
            System.out.println("4. Beef Steak - ₱175");
            System.out.println("5. Halo-Halo - ₱250");
            System.out.print("Choice: ");
            choice = nxInt(sc);

            if (choice == 0) {
                System.out.println("Finished ordering.");
                break;
            }

            System.out.print("Enter quantity: ");
            int qty = nxPositiveInt(sc);

            switch (choice) {
                case 1:
                    foodTotal += 50 * qty;
                    orders += String.format("%-20s %4d\n", "Coke", qty);
                    break;
                case 2:
                    foodTotal += 5 * qty;
                    orders += String.format("%-20s %4d\n", "Rice", qty);
                    break;
                case 3:
                    foodTotal += 100 * qty;
                    orders += String.format("%-20s %4d\n", "Chicken Adobo", qty);
                    break;
                case 4:
                    foodTotal += 175 * qty;
                    orders += String.format("%-20s %4d\n", "Beef Steak", qty);
                    break;
                case 5:
                    foodTotal += 250 * qty;
                    orders += String.format("%-20s %4d\n", "Halo-Halo", qty);
                    break;
                default:
                    System.out.println("Invalid choice! Please select from the menu.");
            }
        } while (true);
    }

    // 4. Billing + Payment
    private static void billing(Scanner sc) {
        totalBill = roomCost + foodTotal;

        System.out.println("\n===== HOTEL SUMMARY =====");
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Gender: " + gender);
        System.out.println("Room Type: " + roomType);
        System.out.println("Number of Nights: " + nights);
        System.out.println("\n" + "-".repeat(25));
        System.out.println(centerTextString("ORDER SUMMARY", 25));
        System.out.println("-".repeat(25));
        if (orders.isEmpty()) {
            System.out.println("No items ordered.");
        } else {
            System.out.printf("%-20s %4s\n", "Item", "Qty");
            System.out.println("-".repeat(25));
            System.out.print(orders);
        }

        System.out.println("-".repeat(25));
        System.out.printf("Food and Drinks: ₱%.2f\n", foodTotal);
        System.out.printf("Room Cost: ₱%.2f\n", roomCost);
        System.out.printf("Total Bill: ₱%.2f\n", totalBill);
        System.out.println("-".repeat(25));

        System.out.print("\nEnter Cash Amount: ₱");
        double cash = sc.nextDouble();

        if (cash >= totalBill) {
            double change = cash - totalBill;
            System.out.printf("Change: ₱%.2f\n", change);
        } else {
            double kulang = totalBill - cash;
            System.out.printf("Insufficient payment. Need ₱%.2f more.\n", kulang);
        }

        System.out.println("\nThank you for staying with us! We hope to see you again!");
    }

    private static String getGender(Scanner sc) {
    while (true) {
        System.out.print("Enter Gender (Male/Female): ");
        String input = sc.nextLine().trim().toLowerCase();

        if (input.equals("male")) {
            return "Male";
        } else if (input.equals("female")) {
            return "Female";
        } else {
            System.out.println("Invalid input. Please enter Male or Female only.");
        }
    }
}

    private static int nxInt(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            sc.nextLine();
        }
        int value = sc.nextInt();
        sc.nextLine();
        return value;
    }

    private static int nxPositiveInt(Scanner sc) {
        int value;
        do {
            value = nxInt(sc);
            if (value <= 0) {
                System.out.print("Please enter a positive number: ");
            }
        } while (value <= 0);
        return value;
    }

    private static String centerTextString(String text, int width) {
        if (text.length() >= width) return text;

        int padding = (width - text.length()) / 2;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < padding; i++) {
            sb.append(" ");
        }
        sb.append(text);
        for (int i = 0; i < padding; i++) {
            sb.append(" ");
        }
        if (sb.length() < width) {
            sb.append(" ");
        }
        return sb.toString();
    }
}