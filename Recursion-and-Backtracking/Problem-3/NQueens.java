import java.util.Arrays;

/**
 * Finds the first valid N-Queens arrangement.
 * Uses a 1D board and boolean arrays for columns and diagonals.
 *
 * To add forbidden squares, set forbidden[row][col] = true.
 */
public class NQueens {

    private static int n;
    private static int[] queenColumn;
    private static boolean[] usedColumn;
    private static boolean[] usedDiagonalDown;
    private static boolean[] usedDiagonalUp;
    private static boolean[][] forbidden;

    private static boolean solveRow(int row) {
        // Base case: queens have been placed in all rows.
        if (row == n) {
            return true;
        }

        // Try every column in the current row.
        for (int col = 0; col < n; col++) {
            int diagonalDown = row - col + n - 1;
            int diagonalUp = row + col;

            // Check column, both diagonals, and forbidden squares.
            if (forbidden[row][col] ||
                usedColumn[col] ||
                usedDiagonalDown[diagonalDown] ||
                usedDiagonalUp[diagonalUp]) {
                continue;
            }

            // Make choice: place a queen.
            queenColumn[row] = col;
            usedColumn[col] = true;
            usedDiagonalDown[diagonalDown] = true;
            usedDiagonalUp[diagonalUp] = true;

            // Recurse to the next row.
            if (solveRow(row + 1)) {
                return true;
            }

            // Backtrack: undo the placement.
            queenColumn[row] = -1;
            usedColumn[col] = false;
            usedDiagonalDown[diagonalDown] = false;
            usedDiagonalUp[diagonalUp] = false;
        }

        return false;
    }

    private static void printBoard() {
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                System.out.print(queenColumn[row] == col ? "Q " : ". ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        n = 4; // Change to 8 for the standard 8-Queens problem.

        queenColumn = new int[n];
        Arrays.fill(queenColumn, -1);

        usedColumn = new boolean[n];
        usedDiagonalDown = new boolean[2 * n - 1];
        usedDiagonalUp = new boolean[2 * n - 1];
        forbidden = new boolean[n][n];

        // Example for forbidden squares (uncomment if required):
        // forbidden[0][1] = true;
        // forbidden[2][3] = true;

        if (solveRow(0)) {
            System.out.println("One valid " + n + "-Queens solution:");
            printBoard();
            System.out.println("Queen columns by row: "
                               + Arrays.toString(queenColumn));
        } else {
            System.out.println("No valid solution exists.");
        }
    }
}
