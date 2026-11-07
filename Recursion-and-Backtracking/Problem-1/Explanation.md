# Problem 1 – Directory Size Calculator

## (a) Recursive function structure

**Pseudocode**

```text
directorySize(path, visited):
    if path is a regular file:
        return size of file                 // Base case

    if path is not a directory:
        return 0

    realPath = canonical path of directory
    if realPath is already in visited:
        return 0                            // Cycle/duplicate guard
    add realPath to visited

    total = 0
    for each child inside path:
        total = total + directorySize(child, visited)  // Recursive case

    return total
```

- **Base case:** If the current path is a file, return its size. If it is not a file or directory, return 0.
- **Recursive case:** If it is a directory, recursively calculate the size of each child and add the results.

The Java implementation is in `DirectorySizeCalculator.java`.

## (b) Execution trace for the supplied directory

Given:

```text
project/
├── src/
│   ├── main.java       100 KB
│   └── utils.java       50 KB
├── docs/
│   ├── readme.txt       10 KB
│   └── guides/
│       └── setup.pdf   200 KB
└── config.xml           20 KB
```

Trace (calls are made while going down; values return while the calls unwind):

| Call | Work | Returned size |
|---|---|---:|
| `directorySize(project)` | Calls `src`, `docs`, and counts `config.xml` | 380 KB |
| `directorySize(src)` | `main.java` + `utils.java` | 150 KB |
| `directorySize(docs)` | `readme.txt` + `guides` | 210 KB |
| `directorySize(guides)` | `setup.pdf` | 200 KB |
| `directorySize(main.java)` | File base case | 100 KB |
| `directorySize(utils.java)` | File base case | 50 KB |
| `directorySize(readme.txt)` | File base case | 10 KB |
| `directorySize(setup.pdf)` | File base case | 200 KB |
| `directorySize(config.xml)` | File base case | 20 KB |

Call-stack shape:

```text
project
├── src
│   ├── main.java  -> 100 KB
│   └── utils.java ->  50 KB
│   src returns       150 KB
├── docs
│   ├── readme.txt ->  10 KB
│   └── guides
│       └── setup.pdf -> 200 KB
│       guides returns 200 KB
│   docs returns       210 KB
└── config.xml         20 KB

project returns 150 + 210 + 20 = 380 KB
```

**Final answer: 380 KB.**

## (c) Time complexity

Let `F` be the number of files and `D` the number of directories.

- **Time:** `O(F + D)`, because each file and directory is processed once.
- **Auxiliary space:** `O(D)` for the recursion stack and the set of visited canonical directories in the worst case.

The exact time also depends on filesystem operations such as reading directory entries and file metadata.

## (d) Can it be solved iteratively?

Yes. Use an explicit **Stack** (or Queue) of directory paths:

1. Push the starting directory.
2. Pop one path.
3. If it is a file, add its size to the total.
4. If it is a directory, push its children.
5. Track canonical directory paths in a visited set to avoid cycles.
6. Continue until the stack is empty.

| Recursive approach | Iterative approach |
|---|---|
| Uses the language call stack | Uses an explicit stack or queue |
| Often shorter and naturally follows nested directories | Makes stack management explicit |
| Very deep directory nesting can risk stack overflow | Avoids call-stack overflow, though the explicit stack still uses memory |
| Easier to express recursively | Can be easier to control traversal |

## (e) Symbolic-link cycle

A symbolic link can point to an ancestor directory. Following it without protection may cause the same directories to be visited repeatedly, resulting in infinite recursion.

**Modification:** Resolve each directory to its canonical/real path and store it in a `Set`. Before visiting a directory, check whether that real path is already present. If it is, return 0 and do not traverse it again.

The Java program uses `toRealPath()` and a `HashSet<Path>` for this protection.
