package org.imalygin.clustering;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.Integer.parseInt;

public class KClustering {

    public KClustering() throws IOException{
        InputStream inputStream = KClustering.class.getClassLoader().getResourceAsStream("org/imalygin/clustering/clustering2.txt");
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream));

        int nodeNum = parseInt(fileReader.readLine());
        UnionFind unionFind = new UnionFind(nodeNum);
        String line;
        List<Edge> edges = new ArrayList<>();
        while ((line = fileReader.readLine()) != null){
            edges.add(createEdge(line));
        }
        Collections.sort(edges);
        Iterator<Edge> edgeIterator = edges.iterator();
        Edge maxSpacingEdge = null;
        while (edgeIterator.hasNext()){
            Edge nextEdge = edgeIterator.next();
            if(unionFind.find(nextEdge.start) != unionFind.find(nextEdge.end)){
                if(unionFind.getClusterCount() == 4){
                    maxSpacingEdge = nextEdge;
                }
                unionFind.union(nextEdge.start, nextEdge.end);
            }
        }
        System.out.println(maxSpacingEdge.cost);

 
    }

    private Edge createEdge(String line) {
        String[] splittedLine = line.split(" ");
        return new Edge(parseInt(splittedLine[0]), parseInt(splittedLine[1]), parseInt(splittedLine[2]));
    }

    private static class Edge implements Comparable<Edge> {
        final int start;
        final int end;
        final int cost;

        public Edge(int start, int end, int cost) {
            this.start = start;
            this.end = end;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge that) {
            return this.cost - that.cost;
        }

        @Override
        public String toString() {
            return "start=" + start +
                    ", end=" + end +
                    ", cost=" + cost;
        }
    }

    public static void main(String[] args) throws Exception {
        new KClustering();
    }
}
