/* 123B5F138 RACHANA DIXIT
Scenario: Disaster Relief Resource Allocation
A massive earthquake has struck a remote region, and a relief organization is transporting essential supplies to the affected area. The organization has a limited-capacity relief truck thatcan carry a maximum weight of W kg. They have N different types of essential items, each
with a specific weight and an associated utility value (importance in saving lives and meeting
urgent needs).Since the truck has limited capacity, you must decide which items to include to maximize thetotal utility value while ensuring the total weight does not exceed the truck's limit.
Your Task as a Logistics Coordinator:
1. Model this problem using the 0/1 Knapsack approach, where each item can either be
included in the truck (1) or not (0).
2. Implement an algorithm to find the optimal set of items that maximizes utility while
staying within the weight constraint.
3. Analyze the performance of different approaches (e.g., Brute Force, Dynamic
Programming, and Greedy Algorithms) for solving this problem efficiently.
4. Optimize for real-world constraints, such as perishable items (medicines, food) having
priority over less critical supplies.
Extend the model to consider multiple trucks or real-time decision-making for dynamic supply
chain management.*/



import java.util.*;

public class KnapSack {

    static class Item {
        String name;
        int weight;
        int value;

        public Item(String name, int weight, int value) {
            this.name = name;
            this.weight = weight;
            this.value = value;
        }
    }

    static int[][] dp;

    
    public static int knapsackMemo(Item[] items, int W, int n) {
        if (n == 0 || W == 0)
            return 0;

        if (dp[n][W] != -1)
            return dp[n][W];

        if (items[n - 1].weight > W)
            dp[n][W] = knapsackMemo(items, W, n - 1);
        else
            dp[n][W] = Math.max(
                items[n - 1].value + knapsackMemo(items, W - items[n - 1].weight, n - 1),
                knapsackMemo(items, W, n - 1)
            );

        return dp[n][W];
    }

    
    public static void printSelectedItems(Item[] items, int W, int n) {
        System.out.println("\nItems selected:");
        int totalValue = dp[n][W];
        for (int i = n; i > 0 && totalValue > 0; i--) {
            if (totalValue == dp[i - 1][W]) {
                
                continue;
            } else {
                
                System.out.println(" - " + items[i - 1].name + 
                    " (Weight: " + items[i - 1].weight + ", Value: " + items[i - 1].value + ")");
                totalValue -= items[i - 1].value;
                W -= items[i - 1].weight;
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of items: ");
        int n = sc.nextInt();
        sc.nextLine();

        Item[] items = new Item[n];

        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter details for item " + (i + 1) + ":");
            System.out.print("Name: ");
            String name = sc.nextLine();
            System.out.print("Weight: ");
            int weight = sc.nextInt();
            System.out.print("Utility Value: ");
            int value = sc.nextInt();
            sc.nextLine();
            items[i] = new Item(name, weight, value);
        }

        System.out.print("\nEnter truck capacity (W in kg): ");
        int W = sc.nextInt();

        dp = new int[n + 1][W + 1];
        for (int[] row : dp)
            Arrays.fill(row, -1);

        int maxValue = knapsackMemo(items, W, n);
        printSelectedItems(items, W, n);

        System.out.println("\nMaximum Utility Value: " + maxValue);
        sc.close();
    }
}

