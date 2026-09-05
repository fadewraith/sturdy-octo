package educativetutorials.codingpatterns.onetwopointers.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PartitionLabels {

    public List<Integer> partitionLabels(String s)
    {
        // Step 1: Find the last occurrence of every character
        int[] lastOccurrence = new int[26];
        for (int i = 0; i < s.length(); i++) {
            lastOccurrence[s.charAt(i) - 'a'] = i;
        }

        // Step 2: Traverse and find the partition boundary
        int partitionEnd = 0;
        int partitionStart = 0;
        List<Integer> partitionSizes = new ArrayList<>(); // Stores the sizes of completed partitions

        // Step 3: Traverse the string to form partitions
        for (int i = 0; i < s.length(); i++) {
            // Update the current partition boundary to include all occurrences of the current character
            partitionEnd = Math.max(partitionEnd, lastOccurrence[s.charAt(i) - 'a']);

            // When the current index reaches the partition boundary,
            // it means we’ve covered all characters in this partition
            if (i == partitionEnd) {
                // Calculate partition size and add it to the result list
                partitionSizes.add(i - partitionStart + 1);
                // Move the start pointer to the next position for the new partition
                partitionStart = i + 1;
            }
        }

        // Step 4: Return the list of partition sizes
        return partitionSizes;
    }

    // Driver code
    public static void main(String[] args) {
        List<String> strings = Arrays.asList(
                "ababcbacadefegdehijhklij",
                "eccbbbbdec",
                "caedbdedda",
                "abcdef",
                "bcbcdd"
        );

        int i = 0;
        for (String s : strings) {
            PartitionLabels obj=new PartitionLabels();
            System.out.println((i + 1) + ".\ts: " + s);
            List<Integer> result = obj.partitionLabels(s);
            System.out.println("\n\tPartition sizes: " + result);
            System.out.println("----------------------------------------------------------------------------------------------------");
            i++;
        }
    }
}
