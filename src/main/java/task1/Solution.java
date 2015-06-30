package task1;

import java.util.Arrays;

public class Solution {
    public static void main(String[] args) {
        Solution sol = new Solution();
        int[] arr = {1, 2, 4, 5, 6};
        System.out.println(sol.solution(arr)); // true
        int[] arr2 = {1, 10, 3, 4, 2};
        System.out.println(sol.solution(arr2)); // true
        int[] arr3 = {1, 2, 3, 4};
        System.out.println(sol.solution(arr3)); // true
        int[] arr4 = {11, 10, 3, 4, 2};
        System.out.println(sol.solution(arr4)); // false
        int[] arr5 = {1, 3, 5, 3, 4};
        System.out.println(sol.solution(arr4)); // false
        /*
		 * System.out.println(sol.solution([1,10,3,4,2])); // true
		 * System.out.println(sol.solution([1,1,2,3,9,5,4])); // true
		 * System.out.println(sol.solution([1,2,3,4])); // true
		 * System.out.println(sol.solution([11,10,3,4,2])); // false
		 * System.out.println(sol.solution([1,3,5,3,4])); // false
		 */
    }

    public boolean solution(int[] A) {
        if (A == null || A.length == 0)
            return false;
        // iterate to check whether it is already in ascending order

        int[] copyOfA = Arrays.copyOf(A, A.length);
        Arrays.sort(copyOfA);

        if (Arrays.equals(copyOfA, A))
            return true;

        int swapCount = 0;

        for (int i = 0; i < copyOfA.length; i++) {
            if (A[i] == copyOfA[i])
                continue;
            else
                swapCount++;
        }
        return swapCount == 2;

    }
}
