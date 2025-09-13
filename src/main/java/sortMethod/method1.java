package sortMethod;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 快速排序
 */
public class method1 {
    public void sort(int[] num) {
        quickSort(num, 0, num.length - 1);
    }
    public void quickSort (int[] num, int left, int right) {
        if (left >= right) {
            return;
        }
        int p = partition(num, left, right);
        quickSort(num, left, p - 1);
        quickSort(num, p + 1, right);
    }
    public void swap(int[] num, int i, int j) {
        int temp = num[i];
        num[i] = num[j];
        num[j] = temp;
    }
    public int partition(int[] num, int left, int right) {
        int index = ThreadLocalRandom.current().nextInt(right - left + 1) + left;
        swap(num, left, index);
        int pv = num[left];
        int i = left + 1;
        int j = right;
        while (i <= j) {
            while (i <= j && num[i] < pv) {
                i++;
            }
            while (i <= j && num[j] > pv) {
                j--;
            }
            if (i <= j) {
                swap(num, i, j);
                i++;
                j--;
            }
        }
        swap(num, left, j);
        return j;
    }
}
