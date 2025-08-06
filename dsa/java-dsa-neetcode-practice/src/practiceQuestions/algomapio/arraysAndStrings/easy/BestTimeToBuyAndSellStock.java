package practiceQuestions.algomapio.arraysAndStrings.easy;

public class BestTimeToBuyAndSellStock {

    private static int bruteForce(int[] prices) {
        int maxProfit = 0;
        for(int i = 0; i < prices.length; i++) {
            for(int j = i + 1; j < prices.length; j++) {
                int profit = prices[j] - prices[i];
                if(profit > 0) {
                    maxProfit = Math.max(maxProfit, profit);
                }
            }
        }
        return maxProfit;
    }

    private static int optimal(int[] prices) {
        int minPrice = Integer.MAX_VALUE;

        int maxProfit = 0;

        for(int price: prices) {
            if(price < minPrice) {
                minPrice = price;
            }
            int profit = price - minPrice;
            if(profit > maxProfit) {
                maxProfit = profit;
            }
        }
        return maxProfit;

    }

}


/**
 * Brute Force
 * Understand the Problem:
 * We are tasked with finding the highest possible profit from buying a stock on one day and selling it on a later day.
 * The profit is calculated as prices[j] - prices[i] where i is the buy day and j is the sell day (with j > i).
 * Check All Pairs of Days:
 * We iterate over each possible day i as the buying day.
 * For each buying day i, we iterate over all subsequent days j (where j > i) as the selling day.
 * Calculate Profit for Each Pair:
 * For each pair of buy and sell days, calculate the profit as prices[j] - prices[i].
 * If the profit is positive (i.e., the stock price on day j is higher than on day i), we check if this profit is the highest profit seen so far.
 * Return the Maximum Profit:
 * Once all pairs are checked, the highest value for max_profit will be the answer.
 *
 * Optimal Solution
 * The optimal solution uses a single pass to track the minimum price seen so far, achieving O(n) time complexity:
 *
 * Track the Minimum Price:
 * Instead of checking all pairs of days, we can track the lowest price encountered so far as we iterate through the list.
 * This allows us to calculate the potential profit at each step by subtracting the current minimum price from the current price.
 * Update Maximum Profit Efficiently:
 * As we go through each price, we only calculate the profit for the current day using the minimum price up to that point.
 * If the calculated profit is greater than the previous maximum profit, we update the maximum profit.
 * Single Pass Through the Prices:
 * By iterating through the list once, we avoid the nested loops of the brute force solution, reducing the time complexity.
 * */