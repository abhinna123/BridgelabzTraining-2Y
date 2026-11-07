import java.io.IOException;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Recursively calculates the total size of files inside a directory.
 *
 * Usage:
 *   java DirectorySizeCalculator /path/to/directory
 *
 * Symbolic links are followed, but a real directory is visited only once.
 * This prevents infinite recursion caused by symbolic-link cycles.
 */
public class DirectorySizeCalculator {

    public static long calculateSize(Path path, Set<Path> visitedDirectories)
            throws IOException {

        // Base case: a regular file contributes its own size.
        if (Files.isRegularFile(path)) {
            return Files.size(path);
        }

        // Ignore items that are neither regular files nor directories.
        if (!Files.isDirectory(path)) {
            return 0;
        }

        // Resolve the canonical path to detect the same directory
        // reached through a symbolic link.
        Path realPath = path.toRealPath();

        // Base/guard case: do not visit a directory more than once.
        if (!visitedDirectories.add(realPath)) {
            return 0;
        }

        long total = 0;

        // Recursive case: add the size of every child.
        try (DirectoryStream<Path> children = Files.newDirectoryStream(path)) {
            for (Path child : children) {
                total += calculateSize(child, visitedDirectories);
            }
        }

        return total;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println(
                "Usage: java DirectorySizeCalculator <directory-path>"
            );
            return;
        }

        Path root = Paths.get(args[0]);

        try {
            if (!Files.isDirectory(root)) {
                System.out.println("The supplied path is not a directory.");
                return;
            }

            long totalBytes = calculateSize(root, new HashSet<>());
            double totalKB = totalBytes / 1024.0;

            System.out.printf("Total size: %.2f KB (%d bytes)%n",
                              totalKB, totalBytes);
        } catch (IOException e) {
            System.out.println("Unable to read the directory: " + e.getMessage());
        }
    }
}
