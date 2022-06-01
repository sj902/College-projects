import java.util.*;

public class TwoNumberSum {

    static HashSet<Integer> hm = new HashSet<>();

    static int tns(int[] arr, int sum) {
        for (int i = 0; i < arr.length; i++) {
            if (hm.contains(sum - arr[i])) {
                return i;
            }
            hm.add(arr[i]);
        }
        return arr.length;
    }

    static int tnsSort(int[] arr, int sum, int left, int right) {

        if (left < 0 || right > arr.length) {
            return arr.length;
        }

        if (arr[left] + arr[right - 1] == sum) {
            return left;
        } else if (arr[left] + arr[right - 1] > sum) {
            return tnsSort(arr, sum, left, right - 1);
        } else {
            return tnsSort(arr, sum, left + 1, right);
        }

    }

    public static void main(String[] args) {
        int[] arr = new int[]{3, 5, -4, 8, 11, 1, -1, 6};
        int sum = 13;
        Arrays.sort(arr);
        int res = tnsSort(arr, sum, 0, arr.length);
        if (res < arr.length)
            System.out.println(arr[res] + ", " + (sum - arr[res]));
    }
}
