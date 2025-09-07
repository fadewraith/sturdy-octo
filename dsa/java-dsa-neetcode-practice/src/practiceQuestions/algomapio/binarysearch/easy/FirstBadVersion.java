package practiceQuestions.algomapio.binarysearch.easy;

public class FirstBadVersion {

    	/* The isBadVersion API is defined in the parent class VersionControl.

	      boolean isBadVersion(int version); */



//    public class Solution extends VersionControl {
//
//        public int firstBadVersion(int n) {
//
//            for (int version = 1; version <= n; version++) {
//
//                if (isBadVersion(version)) {
//
//                    return version;
//
//                }
//
//            }
//
//            return n; // Fallback, though problem guarantees a bad version exists
//
//            // Time: O(n)
//
//            // Space: O(1)
//
//        }
//
//    }

    	/* The isBadVersion API is defined in the parent class VersionControl.

	boolean isBadVersion(int version); */


//
//    public class Solution extends VersionControl {
//
//        public int firstBadVersion(int n) {
//
//            if(n==1) return n;
//
//
//
//            int start =1;
//
//            int end = n;
//
//            int badVersion = 1;
//
//            while(start <= end){
//
//                int mid = start +(end-start)/2;
//
//                if(isBadVersion(mid)){
//
//                    badVersion = mid;
//
//                    end = mid-1;
//
//                }
//
//                else start=mid+1;
//
//            }
//
//            return badVersion;
//
//
//
//        }
//
//    }
}


/**
 * Detailed Explanation
 * Understanding the Problem: First Bad Version
 * The “First Bad Version” problem asks us to find the earliest version of a product that fails quality control. You are given a function isBadVersion(version) which returns true if a version is bad and false otherwise. Versions are developed sequentially, and once a bad version appears, all subsequent versions are also bad.
 *
 * Your task is to determine the first bad version out of n versions with the minimum number of calls to isBadVersion.
 *
 * Why This Problem Matters
 * This problem is an application of binary search — a core algorithmic concept that enables efficient searching over sorted data. It also simulates real-world problems such as debugging a regression bug in a sequence of software builds or identifying the version that introduced a breaking change.
 *
 * Optimal Approach: Binary Search for First Occurrence
 * Since the list of versions has a sorted property (all versions before the first bad one are good, and all after are bad), we can use binary search to efficiently locate the first bad version.
 *
 * Steps:
 * Set two pointers: left = 1 and right = n.
 * While left < right:
 * Compute mid = Math.floor((left + right) / 2).
 * Call isBadVersion(mid):
 * If true, the first bad version could be mid or earlier → set right = mid.
 * If false, the first bad version is later → set left = mid + 1.
 * After the loop, left will point to the first bad version. Return left.
 * Example Walkthrough
 * Suppose n = 5 and isBadVersion returns true starting from version 4:
 *
 * left = 1, right = 5 → mid = 3 → isBadVersion(3) = false → left = 4
 * left = 4, right = 5 → mid = 4 → isBadVersion(4) = true → right = 4
 * left = right = 4 → return 4
 * The first bad version is 4, and we found it using only two API calls instead of five.
 *
 * Time and Space Complexity
 * Time Complexity: O(log n), as binary search cuts the search space in half each time.
 * Space Complexity: O(1), using constant additional space.
 *
 * Edge Cases to Consider
 * First version is bad → should still return 1
 * Last version is bad → should scan correctly through entire list
 * Only one version → return 1 if it is bad
 * Conclusion
 * The “First Bad Version” problem is a classic binary search use case that highlights how to efficiently search for the first occurrence of a condition in a sorted dataset. It's highly relevant for software testing and debugging and teaches how to reduce API calls or checks in performance-critical applications.
 * */