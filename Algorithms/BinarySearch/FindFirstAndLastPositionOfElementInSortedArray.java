package Algorithms.BinarySearch;

import java.util.Arrays;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 06 Feb 2025
 * @link 34. Find First and Last Position of Element in Sorted Array <a href="https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/">LeetCode link</a>
 * @topics Array, Binary Search
 * @companies Google(10), Meta(8), Amazon(6), Apple(4), Microsoft(3), Instacart(2), LinkedIn(7), Bloomberg(4), Oracle(3), TikTok(7), Applied Intuition(7), Uber(5), TCS(4), Tinkoff(4), Goldman Sachs(3), Citadel(3), Splunk(3), NetApp(3), Infosys(2)
 */
public class FindFirstAndLastPositionOfElementInSortedArray {
    public static void main(String[] args) {
        int[] nums = {5,7,7,8,8,10};
        int target = 8;
        System.out.println("searchRange MyApproach => " + Arrays.toString(searchRangeMyApproach(nums, target)));
        System.out.println("searchRange 1 => " + Arrays.toString(searchRange1(nums, target)));
        System.out.println("searchRange 2 => " + Arrays.toString(searchRange2(nums, target)));
    }


    /**
     * @TimeComplexity O(logn)
     * @SpaceComplexity O(1)
     * same as {@link #searchRange1}
     */
    public static int[] searchRangeMyApproach(int[] nums, int target) {
        int[] res = {-1, -1};
        int n = nums.length, l = 0, r = n-1;

        while (l<=r) {
            int m = l + (r-l)/2;
            if (nums[m] < target) l = m+1;
            else r = m-1;
        }

        if (l==n || nums[l] != target) return res; // not found - so, no need to calculate the right boundary
        res[0] = l;
        r = n-1; // no need to update l ---> cause l = Left boundary is already found

        while (l<r) {
            int m = l + (r-l+1)/2; // cause [2,2] -> (l+r)/2 = 0i and -> (l+r+1)/2 = 1i
            if (nums[m] == target) l = m;
            else r = m-1;
        }
        res[1] = l;

        return res;
    }







    /**
     * @TimeComplexity O(logn)
     * @SpaceComplexity O(1)
     * same as {@link #searchRangeMyApproach}
     */
    public static int[] searchRange1(int[] nums, int target) {
        int n = nums.length;
        int[] res = new int[2];
        res[0] = findFirst(nums, target, 0, n-1);
        res[1] = findLast(nums, target, res[0], n-1);

        return res;
    }
    private static int findFirst(int[] nums, int target, int l, int r) {
        int n = nums.length;

        while(l<=r) {
            int m = l + (r-l)/2;
            if(nums[m] < target) {
                l = m+1;
            } else {
                r = m-1;
            }
        }
        return (l == n || nums[l] != target)? -1 : l;
    }
    private static int findLast(int[] nums, int target, int l, int r){
        if(l == -1) {
            return -1;
        }

        while(l<=r) {
            int mid = l + (r-l)/2;
            if(nums[mid]==target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return r;
    }








    /**
     * @TimeComplexity O(logn)
     * @SpaceComplexity O(1)
     */
    public static int[] searchRange2(int[] nums, int target) {
        int[] res = new int[2];
        res[0] = findFirstOccurence(nums, target); // or findBound1(nums, target, true);
        res[1] = findLastOccurence(nums, target); // or findBound1(nums, target, false);
        return res;
    }
    private static int findFirstOccurence(int[] nums, int target) {
        int l = 0, r = nums.length - 1, res = -1;
        while (l <= r) {
            int mid = (r + l) / 2;
            if (nums[mid] == target) {
                res = mid;
                r = mid - 1;
            } else if (nums[mid] < target) l = mid + 1;
            else r = mid - 1;
        }
        return res;
    }
    private static int findLastOccurence(int[] nums, int target) {
        int l = 0, r = nums.length - 1, res = -1;
        while (l <= r) {
            int mid = (r + l) / 2;
            if (nums[mid] == target) {
                res = mid;
                l = mid + 1;
            } else if (nums[mid] < target) l = mid + 1;
            else r = mid - 1;
        }
        return res;
    }
        private static int findBound1(int[] nums, int target, boolean isFirst) { // this is the combination of below #findFirst and #findLast methods
        int n = nums.length, l = 0, r = n-1, res = -1, mid;
        while(l<=r) {
            mid = l + (r-l)/2;
            if(nums[mid]==target) {
                res = mid;
                if(isFirst) {
                    r=mid-1;
                } else {
                    l=mid+1;
                }
            } else if(nums[mid] < target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return res;
    }
    private static int findBound2(int[] nums, int target, boolean isFirst) { // this is the combination of below #findFirst and #findLast methods
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int m = (l + r) / 2;
            if (nums[m] == target) {
                if (isFirst) {
                    if (m == l || nums[m - 1] != target) return m;
                    r = m - 1;
                } else {
                    if (m == r || nums[m + 1] != target) return m;
                    l = m + 1;
                }
            } else if (nums[m] > target) r = m - 1;
            else l = m + 1;
        }
        return -1;
    }







    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static int[] searchRangeMyApproachOld(int[] nums, int target) {
        int l=0, r=nums.length-1;

        while(l<=r) {
            int mid = (l+r)/2;
            if (target == nums[mid]) {
                l = r = mid;
                break;
            } else if (target > nums[mid]) {
                l = mid + 1;
                if (l < nums.length && target == nums[l]){
                    r = l;
                    break;
                }
                // optional: if (l > nums.length-1) continue; int num = nums[l]; while (l+1 < nums.length && nums[l+1] == num) l++;
            }
            else {
                r = mid-1;
                if (r >= 0 && target == nums[r]){
                    l = r;
                    break;
                }
                // optional: if (r < 0) continue; int num = nums[r]; while (r-1 >= 0 && nums[r-1] == num) r--;
            }
        }

        if (l>r) return new int[]{-1, -1}; // not found

        while (l-1 >= 0 && nums[l-1] == target) l--;
        while (r+1 < nums.length && nums[r+1] == target) r++;

        return new int[]{l,r};
    }
}
