/* 123B5F138 RACHANA DIXIT
    Scenario:
A logistics company, SwiftCargo, specializes in delivering packages across multiple cities.To optimize its delivery process, the company divides the transportation network into
multiple stages (warehouses, transit hubs, and final delivery points). Each package mustfollow the most cost-efficient or fastest route from the source to the destination while
passing through these predefined stages.
As a logistics optimization engineer, you must:
1. Model the transportation network as a directed, weighted multistage graph with
multiple intermediate stages.
2. Implement an efficient algorithm (such as Dynamic Programming or Dijkstra’s
Algorithm) to find the optimal delivery route.
3. Ensure that the algorithm scales for large datasets (handling thousands of cities and
routes).*/

import java.util.*;

class Edge {
    int to;
    int cost;
    Edge(int to, int cost) {
        this.to = to;
        this.cost = cost;
    }
}

public class Routefinder {

    public static int[] shortestPathDP(List<List<Edge>> g, int n, int src, int dst) {
        int[] dist = new int[n];
        int[] next = new int[n];

        for (int i = 0; i < n; i++) {
            dist[i] = Integer.MAX_VALUE;
            next[i] = -1;
        }

        dist[dst] = 0;

        for (int i = n - 1; i >= 0; i--) {
            List<Edge> edges = g.get(i);
            for (int j = 0; j < edges.size(); j++) {
                Edge e = edges.get(j);
                if (dist[e.to] != Integer.MAX_VALUE) {
                    int cand = e.cost + dist[e.to];
                    if (cand < dist[i]) {
                        dist[i] = cand;
                        next[i] = e.to;
                    }
                }
            }
        }

        return next;
    }

    public static void printRoute(int src, int dst, int[] next) {
        if (next[src] == -1 && src != dst) {
            System.out.println("No route found from " + src + " to " + dst);
            return;
        }

        int cur = src;
        System.out.print(cur);
        while (cur != dst && next[cur] != -1) {
            cur = next[cur];
            System.out.print(" -> " + cur);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("How many nodes in the multistage graph? ");
        int n = sc.nextInt();

        List<List<Edge>> graph = new ArrayList<List<Edge>>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<Edge>());
        }

        System.out.print("How many directed edges? ");
        int m = sc.nextInt();

        System.out.println("Enter each edge as: from to cost");
        for (int i = 0; i < m; i++) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            int cost = sc.nextInt();
            graph.get(from).add(new Edge(to, cost));
        }

        System.out.print("Source node: ");
        int source = sc.nextInt();

        System.out.print("Destination node: ");
        int destination = sc.nextInt();

        long start = System.nanoTime();
        int[] nextNode = shortestPathDP(graph, n, source, destination);
        long end = System.nanoTime();

        System.out.println("\nOptimal route (if exists):");
        printRoute(source, destination, nextNode);

        double timeMs = (end - start) / 1_000_000.0;
        System.out.println("Computation time: " + timeMs + " ms");

        sc.close();
    }

}
