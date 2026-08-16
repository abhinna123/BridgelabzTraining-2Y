public class MergeSort {
    // Merge Sort implementation
}

/*

Division Tree

                 [8 61 2 3 4 0]

            /                   \

        [8 61 2]             [3 4 0]

       /      \             /      \

    [8]     [61 2]       [3]      [4 0]

            /   \                 /   \

         [61] [2]             [4]   [0]

-------------------------------------

Recurrence Relation

T(n)=2T(n/2)+n

Level 0 : n
Level 1 : n
Level 2 : n
...
Total levels = log₂n

Total Work

n × log₂n

Time Complexity = O(n log n)

*/