package practiceQuestions.algomapio.arraysAndStrings.medium;

public class BestTimeToBuyAndSellStockII {
    private static int solution(int[] prices) {
        int i = 0;
        int lowestPrice = prices[0];
        int highestPrice = prices[0];
        int profit = 0;
        int n = prices.length;

        while(i < n - 1) {

            // look where to buy
            while(i < n - 1 && prices[i] >= prices[i + 1]) {
                i++;
            }
            lowestPrice = prices[i];

            // look where to sell
            while(i < n - 1 && prices[i] <= prices[i + 1]) {
                i++;
            }
            highestPrice = prices[i];

            profit += highestPrice - lowestPrice;
        }

        return profit;
    }
}


/**
 * Step-by-Step Thought Process
 * Understand the problem: Maximize profit by buying and selling stocks multiple times, given daily prices, without holding multiple stocks simultaneously.
 * Initialize variables: set index i to 0, lowest price (lo) and highest price (hi) to the first price, and profit to 0.
 * While i is less than the array length minus 1, iterate to find buy and sell points.
 * To find a buy point, increment i while the current price is greater than or equal to the next price, ensuring a local minimum.
 * Set lo to the price at index i as the buy price.
 * To find a sell point, increment i while the current price is less than or equal to the next price, ensuring a local maximum.
 * Set hi to the price at index i as the sell price.
 * Add the difference (hi - lo) to the total profit.
 * Return the total profit after the loop ends.
 * */

/**
 * Detailed Explanation
 * Understanding the Strategy for Maximum Profit
 * The key to solving the "Best Time to Buy and Sell Stock II" problem lies in understanding that we are allowed to perform multiple transactions (buy one and sell one share multiple times). However, we cannot hold multiple shares simultaneously. The goal is to accumulate profit from every increase in price — every upward trend between two days is a profit opportunity.
 *
 * How the Greedy Algorithm Works
 * We iterate through the array of prices and simply add the profit from every consecutive price increase. That is, if today's price is higher than yesterday's, we "buy" yesterday and "sell" today, adding the difference to the total profit. This greedy strategy ensures that we capture every profitable opportunity without needing to track individual transactions explicitly.
 *
 * Time and Space Complexity
 * Time Complexity: O(n), where n is the number of days, since we traverse the list once.
 * Space Complexity: O(1), as we use only constant extra space for tracking profit.
 * Why This Strategy is Optimal
 * Since we are allowed unlimited transactions and there are no cooldown or fee constraints, the best approach is to collect profits from every upward swing. By treating every rising pair as a potential buy-sell opportunity, we ensure no gains are missed. This greedy method is not only simple but also optimal for this problem scenario.
 *
 * Conclusion
 * The "Best Time to Buy and Sell Stock II" problem is a classic example of when a greedy algorithm delivers an optimal solution. Recognizing the conditions — multiple transactions allowed with no additional constraints — enables a straightforward and efficient solution. This technique is particularly valuable in financial modeling, trading simulations, and competitive coding.
 * */