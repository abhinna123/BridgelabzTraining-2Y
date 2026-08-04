public class BinarySearch {

    public static int binarySearch(int arr[], int target) {

        int low = 0;
        int high = arr.length - 1;

        while (low <= high) {

            int mid = (low + high) / 2;

            if (arr[mid] == target)
                return mid;

            if (arr[mid] < target)
                low = mid + 1;
            else
                high = mid - 1;
        }

        return -1;
    }

    public static void main(String[] args) {

        int arr[] = {0,2,3,4,8,61};

        System.out.println(binarySearch(arr,4));
    }
}

/*

Searching for 4

Array = {0,2,3,4,8,61}

Step 1:
low=0 high=5
mid=2
arr[mid]=3
Search right half

Step 2:
low=3 high=5
mid=4
arr[mid]=8
Search left half

Step 3:
low=3 high=3
mid=3
arr[mid]=4
Found

Worst Case Equation:
N
N/2
N/4
N/8
...
1

Number of steps = log₂N

Time Complexity = O(log n)

*/