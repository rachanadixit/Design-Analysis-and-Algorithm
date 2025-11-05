/* 123B5F138 RACHANA DIXIT
    Scenario: Optimizing Delivery Routes for a Logistics Company
A leading logistics company, SwiftShip, is responsible for delivering packages to multiple cities.To minimize fuel costs and delivery time, the company needs to find the shortest possible route
that allows a delivery truck to visit each city exactly once and return to the starting point.The company wants an optimized solution that guarantees the least cost route, considering:
● Varying distances between cities.
● Fuel consumption costs, which depend on road conditions.
● Time constraints, as deliveries must be completed within a given period.
Since there are N cities, a brute-force approach checking all (N-1)!permutations is infeasible
for large N (e.g., 20+ cities). Therefore, you must implement an LC (Least Cost) Branch and
Bound algorithm to find the optimal route while reducing unnecessary computations
efficiently.*/

import java.util.Arrays;

public class TSP {
    static final double INF = 1e12;
    static int N;
    static double[][] cost;   
    static double[][] timeM;  
    static boolean[] visited;
    static int[] bestPath;
    static double bestCost = INF;
    static double maxTotalTime;
    static double globalMinCost = INF;
    static double globalMinTime = INF;
    static int start = 0;

    static void dfs(int curr, int level, double curCost, double curTime, int[] path) {
        if (level == N) {
            if (cost[curr][start] >= INF) return;
            double totalCost = curCost + cost[curr][start];
            double totalTime = curTime + timeM[curr][start];
            if (totalTime <= maxTotalTime && totalCost < bestCost) {
                bestCost = totalCost;
                bestPath = Arrays.copyOf(path, N + 1);
                bestPath[N] = start;
            }
            return;
        }

        for (int j = 0; j < N; j++) {
            if (visited[j] || cost[curr][j] >= INF) continue;

            double nextCost = curCost + cost[curr][j];
            double lb = nextCost + (N - level - 1) * globalMinCost;
            if (lb >= bestCost) continue; // prune by cost

            double nextTime = curTime + timeM[curr][j];
            double optimisticTime = nextTime + (N - level - 1) * globalMinTime;
            if (optimisticTime > maxTotalTime) continue; // prune by time

            visited[j] = true;
            path[level] = j;
            dfs(j, level + 1, nextCost, nextTime, path);
            visited[j] = false;
        }
    }

    public static void main(String[] args) {
        double[][] distance = {
            {0, 20, 42, 35, 25},
            {20, 0, 30, 34, 50},
            {42, 30, 0, 12, 31},
            {35, 34, 12, 0, 22},
            {25, 50, 31, 22, 0}
        };

        double[][] fuelFactor = {
            {0, 1.2, 1.1, 1.3, 1.0},
            {1.2, 0, 1.0, 1.1, 1.3},
            {1.1, 1.0, 0, 1.0, 1.2},
            {1.3, 1.1, 1.0, 0, 1.2},
            {1.0, 1.3, 1.2, 1.2, 0}
        };

        // assign static timeM (DO NOT redeclare as a local variable)
        timeM = new double[][] {
            {0, 1.5, 2.0, 2.2, 1.7},
            {1.5, 0, 1.8, 2.1, 2.6},
            {2.0, 1.8, 0, 1.1, 1.9},
            {2.2, 2.1, 1.1, 0, 1.5},
            {1.7, 2.6, 1.9, 1.5, 0}
        };

        N = distance.length;
        cost = new double[N][N];
        visited = new boolean[N];

        // Build cost matrix and find global min values
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == j) cost[i][j] = INF;
                else {
                    cost[i][j] = distance[i][j] * fuelFactor[i][j];
                    globalMinCost = Math.min(globalMinCost, cost[i][j]);
                    globalMinTime = Math.min(globalMinTime, timeM[i][j]);
                }
            }
        }

        maxTotalTime = 8.0; // max allowed total time (hours)
        start = 0;
        visited[start] = true;
        int[] path = new int[N + 1];
        path[0] = start;

        dfs(start, 1, 0.0, 0.0, path);

        if (bestPath == null) {
            System.out.println("No feasible route under time " + maxTotalTime);
        } else {
            System.out.println("Best cost: " + bestCost);
            System.out.println("Route:");
            for (int i = 0; i <= N; i++) {
                if (i > 0) System.out.print(" -> ");
                System.out.print(bestPath[i]);
            }
            System.out.println();
        }
    }
}

