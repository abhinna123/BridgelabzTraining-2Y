# Problem 2 – Word Search Using Backtracking

## (a) Backtracking algorithm

**Pseudocode**

```text
search(row, col, index):
    if index == length(word):
        save current path
        return

    if position is outside grid:
        return
    if cell is already visited:
        return
    if grid[row][col] != word[index]:
        return

    mark cell visited                  // Make choice
    add cell to current path

    for each of the 8 neighbouring directions:
        search(nextRow, nextCol, index + 1)

    remove cell from current path      // Undo choice
    mark cell unvisited
```

- **State representation:** Current row, current column, index of the next character, visited cells, and the current path.
- **Choices:** Move to any of the 8 neighbouring cells (horizontal, vertical, or diagonal).
- **Constraints:** The next cell must contain the required character, remain inside the grid, and must not have been used earlier in this path.
- **Goal:** Match every character of `DREAM`.

The Java implementation searches from every cell matching the first letter and collects all valid paths.

## (b) Decision path for DREAM

One successful path using zero-based `(row, column)` coordinates is:

```text
D (2,0)
   ↘ R (1,1)
      → E (1,2)
         → A (1,3)
            ↙ M (2,3)
```

The letters are adjacent under the allowed 8-direction movement, and no cell is repeated.

## (c) Execution trace

| Step | Position | Character | Action |
|---|---|---|---|
| 1 | (2,0) | D | Match; mark visited |
| 2 | (1,1) | R | Match; mark visited |
| 3 | (1,2) | E | Match; mark visited |
| 4 | (1,3) | A | Match; mark visited |
| 5 | (2,3) | M | Match; complete word |

When a selected neighbour is outside the grid, already visited, or contains the wrong character, that recursive branch returns. The algorithm then removes the current cell from the path and marks it unvisited so that another possible path can use it. This is **backtracking**.

## (d) Worst-case time complexity

Let the grid have `N` rows and `M` columns, and let the word length be `L`.

- There are at most `N × M` starting cells.
- From a starting cell, there are at most 8 choices for each next character.
- Therefore, a simple upper bound is **`O(N × M × 8^(L-1))`**.
- The visited-cell rule can reduce the actual number of explored branches.
- Auxiliary space is `O(N × M + L)` for the visited grid, current path, and recursion depth (excluding the storage of all returned paths).

## (e) Finding ALL possible ways

To find all paths rather than stopping at the first one:

1. Continue searching after a complete word is found.
2. Copy the current path into a result list.
3. Backtrack and explore the remaining directions and starting cells.
4. Return the result list.

The Java program uses `List<List<String>> allPaths` to store every valid path. Each saved path is copied so later backtracking does not change an already recorded result.
