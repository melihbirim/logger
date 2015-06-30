package task3;

import java.util.Arrays;

public class Solution {
    public static void main(String[] args) {
        Solution sol = new Solution();
        int[] arr = {100, 250, 1000};
        sol.solution(arr);
    }

    public int solution(int[] A) {
        if (A == null || A.length == 0)
            return 0;
        // iterate to check whether it is already in ascending order
        int[] results = new int[A.length];
        int[] tmp = Arrays.copyOf(A, A.length);
        for (int i = 0; i < A.length; i++) {
            results[i] = merge(tmp);
            shiftLeft(tmp, tmp[0]);
            System.out.println(results[i]);
        }
        Arrays.sort(results);
        return results[0];

    }

    private void shiftLeft(int[] arr, int last) {
        System.arraycopy(arr, 1, arr, 0, arr.length - 1);
        arr[arr.length - 1] = last;
    }

    private int merge(int[] A) {
        if (A.length == 2)
            return A[0] + A[1];
        else if (A.length > 2) {
            int merge = merge(Arrays.copyOfRange(A, 1, A.length));
            return merge + (merge + A[0]);
        }
        return 0;
    }
}
