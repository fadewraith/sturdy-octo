package neetcodepracticesets.arraysandhashing.ez;

import java.util.ArrayList;
import java.util.List;

public class PascalsTriangle {

    // t - O(n^2)
    // s - O(n^2)

    public static List<List<Integer>> bruteForce(int numRows) {

        // storing res
        List<List<Integer>> data = new ArrayList<>();
        for(int i = 0; i < numRows; i++) {
            List<Integer> row = new ArrayList<>();
            row.add(1);
            int val = 1;
            for(int j = 1; j <= i; j++) {
                val = val * (i - j + 1) / j;
                row.add(val);
            }
            data.add(row);
        }
        return data;
    }

    public List<List<Integer>> dpOne(int numRows) {
        List<List<Integer>> res = new ArrayList<>();
        res.add(new ArrayList<>());
        res.get(0).add(1);

        for (int i = 1; i < numRows; i++) {
            List<Integer> temp = new ArrayList<>(res.get(i - 1));
            temp.add(0, 0);
            temp.add(0);
            List<Integer> row = new ArrayList<>();

            for (int j = 0; j < res.get(i - 1).size() + 1; j++) {
                row.add(temp.get(j) + temp.get(j + 1));
            }

            res.add(row);
        }
        return res;
    }

    public List<List<Integer>> dpTwo(int numRows) {
        List<List<Integer>> res = new ArrayList<>();

        for (int i = 0; i < numRows; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    row.add(1);
                } else {
                    row.add(res.get(i - 1).get(j - 1) + res.get(i - 1).get(j));
                }
            }
            res.add(row);
        }
        return res;
    }



    public static void main(String[] args) {
        System.out.println(bruteForce(5));
    }
}
