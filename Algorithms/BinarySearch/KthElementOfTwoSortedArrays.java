package Algorithms.BinarySearch;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 17 April 2026
 * @link K-th element of two sorted Arrays <a href="https://www.geeksforgeeks.org/problems/k-th-element-of-two-sorted-array1317/1">GFG Link</a>
 * @topics Arrays, Divide and Conquer, Binary Search
 * @companies Flipkart, Microsoft, NPCI
 * @see Algorithms.BinarySearch.MedianOfTwoSortedArrays
 * @link <a href="https://www.geeksforgeeks.org/dsa/k-th-element-two-sorted-arrays/">Article</a>
 */
public class KthElementOfTwoSortedArrays {
    public static void main(String[] args) {
        int nums1[] = {2, 3, 6, 7, 9}, nums2[] = {1, 4, 8, 10}, k = 5;
        System.out.println(findKthElement(nums1, nums2, k));
    }

    /**
    <pre>

        Here we have to maintain k elements on the left and n-k elements on the right

        nums1 = [2, 3, 6, 15], nums2 = [1, 3, 4, 7, 10, 13]

                  |
        1 2 3 3 4 | 6 7 10 12 15 ----> sorted array with 10 eles
                  |

        which is same as

             l1  |  r1
           2, 3  |  6, 15
        1, 3, 4  |  7, 10, 13
              l2 |  r2

        or

            l1    r1
               |
        [2, 3, |  6, 15]
               |

               l2    r2
                  |
        [1, 3, 4, |  7, 10, 13]
                  |


     So, there will be definitely one symmetry - to cut

     and that's why we get max(l1, l2) and min(r1, r2)

     👉 low and high represent how many elements you can pick from nums1, not indices.

     cut1 + cut2 = k

     we already know that l1<=r1 and l2<=r2, so we have to check l1 <= r2 && l2 <= r1

     And we cross check all cuts like
     when cut1 = 0, cut2 = k = n/2 = 5

             l1  |  r1
              -  |  2, 3, 6, 15
  1, 3, 4, 7, 10 |  13
              l2 |  r2

     l2 > r1 ---> low = cut1 + 1 ---> low = 1

             l1  |  r1
              2  |  3, 6, 15
     1, 3, 4, 7  |  10, 13
              l2 |  r2

     l2 > r1 ---> low = cut1 + 1 ---> low = 1

             l1  |  r1
           2, 3  |  6, 15
        1, 3, 4  |  7, 10, 13
              l2 |  r2

     l1 <= r2 && l2 <= r1

    </pre>

     * @TimeComplexity O(log(min(m,n)))
     * @SpaceComplexity O(1)
     * @see Algorithms.BinarySearch.MedianOfTwoSortedArrays#findMedianSortedArrays1
     */
    public static int findKthElement(int[] nums1, int[] nums2, int k) {
        if (nums1.length > nums2.length) {
            return findKthElement(nums2, nums1, k);
        }
        int low = Math.max(0, k - nums2.length); //  ---> Eg: low = k - n2 = 3 - 2 = 1
        int high = Math.min(k, nums1.length); // you can pick max k eles or all eles from nums1
        while (low <= high) {
            int cut1 = (low + high) / 2; // elements from nums1
            int cut2 = k - cut1; // cut1 + cut2 = k
            int l1 = cut1 == 0 ? Integer.MIN_VALUE : nums1[cut1 - 1];
            int r1 = cut1 == nums1.length ? Integer.MAX_VALUE : nums1[cut1];
            int l2 = cut2 == 0 ? Integer.MIN_VALUE : nums2[cut2 - 1];
            int r2 = cut2 == nums2.length ? Integer.MAX_VALUE : nums2[cut2];
            if (l1 <= r2 && l2 <= r1)
                return Math.max(l1, l2);
            else if (l1 > r2)
                high = cut1 - 1;
            else
                low = cut1 + 1;
        }
        return 0;
    }
}
