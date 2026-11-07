import java.util.ArrayList;
import java.util.List;

/**
 * Finds all paths that form a target word in a character grid.
 * Movement is allowed horizontally, vertically, and diagonally.
 * A cell cannot be used more than once in one path.
 */
public class WordSearch {

    private static final int[][] DIRECTIONS = {
        {-1, -1}, {-1, 0}, {-1, 1},
        { 0, -1},           { 0, 1},
        { 1, -1}, { 1, 0},  { 1, 1}
    };

    private static boolean[][] visited;
    private static List<String> currentPath;
    private static List<List<String>> allPaths;

    public static List<List<String>> findAllPaths(char[][] grid, String word) {

        allPaths = new ArrayList<>();

        if (grid == null || grid.length == 0 ||
            word == null || word.isEmpty()) {
            return allPaths;
        }

        int rows = grid.length;
        int cols = grid[0].length;

        visited = new boolean[rows][cols];
        currentPath = new ArrayList<>();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {

                if (grid[row][col] == word.charAt(0)) {
                    search(grid, word, row, col, 0);
                }
            }
        }

        return allPaths;
    }

    private static void search(char[][] grid, String word,
                               int row, int col, int index) {

        if (row < 0 || row >= grid.length ||
            col < 0 || col >= grid[0].length ||
            visited[row][col] ||
            grid[row][col] != word.charAt(index)) {
            return;
        }

        visited[row][col] = true;

        currentPath.add("(" + row + "," + col + ")");

        // Complete word found: save the path only once.
        if (index == word.length() - 1) {

            allPaths.add(new ArrayList<>(currentPath));

        } else {

            // Explore all 8 neighbouring directions.
            for (int[] direction : DIRECTIONS) {

                search(grid, word,
                       row + direction[0],
                       col + direction[1],
                       index + 1);
            }
        }

        // Backtracking: undo the current choice.
        currentPath.remove(currentPath.size() - 1);

        visited[row][col] = false;
    }

    public static void main(String[] args) {

        char[][] grid = {
            {'C', 'A', 'T', 'S'},
            {'O', 'R', 'E', 'A'},
            {'D', 'E', 'A', 'M'},
            {'E', 'L', 'L', 'S'}
        };

        String target = "DREAM";

        List<List<String>> paths = findAllPaths(grid, target);

        if (paths.isEmpty()) {

            System.out.println(target + " was not found.");

        } else {

            System.out.println("Found " + target + " in "
                               + paths.size() + " path(s):");

            for (List<String> path : paths) {
                System.out.println(path);
            }
        }
    }
}