package lvc.cds;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;


public class ALGraph {
    private Vertex[] graph;
    private boolean isDirected;

    public ALGraph(int numVertices) {
        this(numVertices, true);
    }

    public ALGraph(int numVertices, boolean isDirected) {
        graph = new Vertex[numVertices];
        for (int i = 0; i < graph.length; ++i) {
            graph[i] = new Vertex();
        }
        this.isDirected = isDirected;
    }

    public void addEdge(int i, int j) {
        addEdge(i, j, 1.0);
    }

    public void addEdge(int i, int j, double w) {
        if (!vertexCheck(i) || !vertexCheck(j))
            throw new ArrayIndexOutOfBoundsException();
        if (i == j || hasEdge(i, j))
            return;
        graph[i].edges.add(new Edge(j, w));

         if (!isDirected) {
            graph[j].edges.add(new Edge(i));
        }
    }

    private boolean vertexCheck(int i) {
        return i >= 0 && i < graph.length;
    }

    private boolean hasEdge(int i, int j) {
        var edges = graph[i].edges;
        for (var e : edges) {
            if (e.target == j) {
                return true;
            }
        }
        return false;
    }

    public double influencerScore(int start){
        Queue<Integer> queue = new ArrayDeque<>();
        int[] distances = new int[graph.length];
        Arrays.fill(distances, -1);
        int totalDist = 0;
        int count = 0;
        double avg = 0.0;
        double proportion = 0.0;
        double score = 0.0;

        //primes data structure
        queue.add(start);
        distances[start] = 0;

        //step 1
        while(!queue.isEmpty()){
            var cur = queue.poll();

            for(var e : graph[cur].edges){
                if(distances[e.target] == -1){
                    distances[e.target] = distances[cur] + 1;
                    queue.add(e.target);
                }
            }
        }
       
        //step 2-3
        for(int i = 0; i < distances.length; i++ ){
            if(distances[i] > 0){
                totalDist += distances[i];
                count += 1;
            }
        }
        avg = (1.0*totalDist)/count;

        //step 4
        proportion = count/(distances.length-1.0);
        score = proportion/avg;

        return score;
    }

    public double[] influencerRanking(){
        double[] ranking = new double[graph.length];
        for(int i = 1; i < graph.length; i++){
            var score = influencerScore(i);
            ranking[i] = score;
        }
        return ranking;
    }

    private static class Vertex {
        ArrayList<Edge> edges;
        // should we store its int id?

        Vertex() {
            edges = new ArrayList<>();
        }
    }

    private static class Edge {
        int target;
        // should we store its source?
        double weight;

        Edge(int target, double weight) {
            this.target = target;
            this.weight = weight;
        }

        Edge(int target) {
            this.target = target;
            this.weight = 1.0;
        }
    }

}
