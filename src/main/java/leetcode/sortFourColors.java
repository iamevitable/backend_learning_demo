package leetcode;

/**
 * @Desc * @param
 * @return
 * @date:
 * @Author
 */
public class sortFourColors {
    public static void sort(int[] nums) {
        int p0 = 0;
        int p1 = 0;
        int p2 = 0;
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            nums[i] = 3;
            if (x <= 2) {
                nums[p2++] = 2;
            }
            if (x <= 1) {
                nums[p1++] = 1;
            }
            if (x == 0) {
                nums[p0++] = 0;
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        sort(nums);
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i] + " ");
        }
    }
}
