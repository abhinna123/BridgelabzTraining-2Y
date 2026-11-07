# Problem 3 – N-Queens Optimization and Variants

## (a) Search-space calculations for N = 8

**Brute-force search space stated in the assignment:** `N^N`.

For `N = 8`:

```text
8^8 = 16,777,216
```

The assignment notes `N!` possibilities when the one-queen-per-row constraint is applied:

```text
8! = 40,320
```

**Clarification:** One queen per row by itself gives 8 choices in each of 8 rows (`8^8`). The `8!` count additionally assumes no two queens share a column. Backtracking then checks diagonal conflicts and prunes invalid partial placements. The exact number of positions explored depends on the order of trying columns and when branches are rejected; it is not a fixed estimate supplied by the assignment PDF.

## (b) Optimized algorithm using a 1D array

Represent the board with `queenColumn[row] = col`. This stores the column selected for the queen in each row.

Use three boolean arrays:

- `usedColumn[col]`: whether a column is occupied.
- `usedDiagonalDown[row - col + N - 1]`: whether this diagonal is occupied.
- `usedDiagonalUp[row + col]`: whether this diagonal is occupied.

**Pseudocode**

```text
solve(row):
    if row == N:
        return true

    for col from 0 to N-1:
        d1 = row - col + N - 1
        d2 = row + col

        if square is forbidden or column/d1/d2 is occupied:
            continue

        place queen and mark column, d1, d2

        if solve(row + 1):
            return true

        remove queen and unmark column, d1, d2

    return false
```

This avoids scanning the previously placed queens for every candidate. The 1D representation and tracking arrays use `O(N)` auxiliary space, excluding any optional forbidden-square input grid.

## (c) N = 4 execution trace

One valid solution is:

```text
. Q . .
. . . Q
Q . . .
. . Q .
```

The 1D array is:

```text
[1, 3, 0, 2]
```

A successful placement sequence (zero-based row and column):

| Step | Placement | Board array after placement | Result |
|---|---|---|---|
| 1 | (0,1) | `[1, -1, -1, -1]` | Safe |
| 2 | (1,3) | `[1, 3, -1, -1]` | Safe |
| 3 | (2,0) | `[1, 3, 0, -1]` | Safe |
| 4 | (3,2) | `[1, 3, 0, 2]` | Safe; all queens placed |

Diagonal indexes are calculated by `row - col + N - 1` and `row + col`. At each placement, the corresponding column and diagonal flags become `true`. When a row has no safe column, the algorithm removes the previous queen and resets its flags to `false`, then tries the next column. This is the backtracking step.

## (d) Find only ONE solution quickly

Stop the search as soon as a complete arrangement is found:

- Return `true` when `row == N`.
- Propagate `true` immediately through recursive calls.
- Do not continue exploring other columns after a solution is found.

The Java implementation follows this approach and prints the first solution it finds.

## (e) N-Queens with forbidden squares

Create a boolean matrix:

```java
boolean[][] forbidden = new boolean[n][n];
```

Mark a blocked square as `true`, for example:

```java
forbidden[0][1] = true;
```

Before placing a queen at `(row, col)`, reject the square if `forbidden[row][col]` is true. The remaining column and diagonal checks stay the same.

Forbidden squares can reduce the available choices, but they do not increase the worst-case search-space bound. Some arrangements may become impossible, in which case the algorithm reports that no solution exists.

## Complexity summary

- Basic 2D-board implementation in the assignment: board storage `O(N²)` and recursion stack `O(N)`.
- This optimized 1D implementation: board array, tracking arrays, and recursion stack use `O(N)` auxiliary space (the optional forbidden matrix uses `O(N²)`).
- The search is exponential in the worst case; with one queen per row and unique columns, the candidate arrangements are bounded by `N!`, before diagonal pruning.
