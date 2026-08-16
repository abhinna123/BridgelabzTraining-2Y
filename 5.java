public class LinearSearch {

    public static int linearSearch(int arr[], int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target)
                return i;
        }
        return -1;
    }

    public static void main(String[] args) {

        int arr[] = {8, 61, 2, 3, 4, 0};

        System.out.println(linearSearch(arr, 8));
    }
}

/*
Best Case:
Target = 8
Found at first position.
Time Complexity = O(1)

Worst Case:
Target = 0 or 5
Need to check every element.
Time Complexity = O(n)

Average Case:
Target is at a random position.
On average n/2 comparisons.
Time Complexity = O(n)
*/