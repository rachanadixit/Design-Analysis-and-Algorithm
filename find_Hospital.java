import java.util.*;
public class find_Hospital {
    static class Edge{
        int src;
        int dest;
        int weight;

        public Edge(int src,int dest,int weight){
            this.src=src;
            this.dest=dest;
            this.weight=weight;
        }
    }

    static class Pair implements Comparable<Pair>{
        int node;
        int path;

        public Pair(int node,int path){
            this.node=node;
            this.path=path;
        }

        public int compareTo(Pair p2){
            return this.path-p2.path;
        }
    }
    public static  int[] dijiikstra(ArrayList<Edge> graph[],int src,int[] parent){
        int dist[]=new int[graph.length];
        Arrays.fill(dist,Integer.MAX_VALUE);
        Arrays.fill(parent,-1);

        dist[src]=0;

        boolean visited[]=new boolean[graph.length];
        PriorityQueue<Pair> pq=new PriorityQueue<>();
        pq.add(new Pair(src,0));

        while(!pq.isEmpty()){
            Pair curr=pq.remove();
            if(!visited[curr.node]){
                visited[curr.node]=true;

              
                for(Edge e:graph[curr.node]){
                    int u=e.src;
                    int v=e.dest;
                    int wt=e.weight;

                    if(dist[u]!=Integer.MAX_VALUE&&dist[u]+wt<dist[v]){
                        dist[v]=dist[u]+wt;
                        parent[v]=u;
                        pq.add(new Pair(v,dist[v]));
                    }

                }
            }

        }
        return dist;

    }

    public static List<Integer> getPath(int[] parent,int target){
        List<Integer> path=new ArrayList<>();

        for(int i=target;i!=-1;i=parent[i]){
            path.add(i);
        }

        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {

        Scanner sc=new Scanner(System.in);

        System.out.println("Enter number of intersections (vertices): ");
        int V = sc.nextInt();

        System.out.println("Enter number of roads (edges): ");
        int E = sc.nextInt();

        ArrayList<Edge> graph[]=new ArrayList[V];

        for(int i=0;i<V;i++){
            graph[i]=new ArrayList<>();
        }

        System.out.println("Enter the Edge (u,v,w)");
        for (int i = 0; i < E; i++) {
            int u=sc.nextInt();
            int v=sc.nextInt();
            int w=sc.nextInt();
            graph[u].add(new Edge(u,v,w));
            graph[v].add(new Edge(v, u, w));
        }

        System.out.println("Enter ambulance start location (source): ");
        int source = sc.nextInt(); 

        System.out.println("Enter number of hospitals: ");
        int H = sc.nextInt();

        int[] hospitals = new int[H];
        System.out.println("Enter hospital nodes: ");
        for (int i = 0; i < H; i++) {
            hospitals[i] = sc.nextInt(); 
        }

        int parent[]=new int[V];
        int dist[]=dijiikstra(graph, source, parent);

        int mintime=Integer.MAX_VALUE;
        int nearestHospital=-1;

        for(int h:hospitals){
            if(dist[h]<mintime){
                mintime=dist[h];
                nearestHospital=h;
            }
        }


        if (nearestHospital == -1 || mintime == Integer.MAX_VALUE) {
            System.out.println("No hospital reachable.");
        } else {
            System.out.println("Nearest hospital is at node " + nearestHospital +
                    " with travel time " + mintime + " minutes.");

        List<Integer> path=getPath(parent,nearestHospital);
        System.out.println("Path: "+path);
        }

        sc.close();               
        
    }
}