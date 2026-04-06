public class Tampil_564755 {
    public static void main(String[] args) {
        int[][] matrix = {
            {100, 200, 300, 400},
            {10, 20, 30, 40},
            {1, 2, 3, 4},
            {5, 15, 30, 90}
        };

        //print the current matrix
        System.out.println("Current Matrix:");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        //find index of number 15
        //print indices of number 15
        //get x and y coordinates of number 15
        int x = -1; // Row index
        int y = -1; // Column index
        int target = 15;
        boolean found = false;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == target) {
                    System.out.println("Number " + target + " found at index: (" + i + ", " + j + ")");
                    x = i; // Row index
                    y = j; // Column index
                    found = true;
                    break;
                }
            }
            if (found) {
                break;
            }
        }

        //update 15 to 99
        //print the updated 2d matrix
        if (found) {
            matrix[x][y] = 99; // Update the value at the found index
            System.out.println("Updated Matrix:");
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    System.out.print(matrix[i][j] + " ");
                }
                System.out.println();
            }
        } else {
            System.out.println("Number " + target + " not found in the matrix.");
        }
    }
}
