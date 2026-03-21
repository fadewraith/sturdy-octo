package educativetutorials.codingpatterns.threeslidingwindow.easy;

import java.util.Arrays;

public class BestTimeToBuySellStock {

    // Function to calculate the maximum profit
    public static int maxProfit(int[] prices) {
        // Initialize two pointers and maximum profit variable
        int buy = 0;
        int sell = 1;
        int maxprofit = 0;

        // Iterating over the prices array
        while (sell < prices.length) {
            // Calculating the current profit
            int currentProfit = prices[sell] - prices[buy];

            // Setting the maximum profit if price at sell pointer is greater than buy
            if (prices[buy] < prices[sell]) {
                maxprofit = Math.max(currentProfit, maxprofit);
            } else {
                // Otherwise, update buy to be equal to sell
                buy = sell;
            }

            // Moving sell pointer to the next index of the prices array
            sell++;
        }

        return maxprofit;
    }

    public static void main(String[] args) {
        int[][] pricesList = {
                {1, 2, 4, 2, 5, 7, 2, 4, 9, 0, 9},
                {8, 2, 6, 4, 7, 5},
                {7, 6, 4, 3, 1},
                {2, 6, 8, 7, 8, 7, 9, 4, 1, 2, 4, 5, 8},
                {1, 2}
        };

        for (int i = 0; i < pricesList.length; i++) {
            System.out.println((i + 1) + ". Stock prices = " + Arrays.toString(pricesList[i]));
            System.out.println("   Maximum profit = " + maxProfit(pricesList[i]));
            System.out.println(new String(new char[100]).replace('\0', '-'));
        }
    }
}
