package org.imalygin.clustering;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.Integer.parseInt;

public class BigDataClustering {

    public static void main(String[] args) throws Exception{

        InputStream inputStream = KClustering.class.getClassLoader().getResourceAsStream("org/imalygin/clustering/clustering_big.txt");
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream));

        String[] firstLineSplitted = fileReader.readLine().split(" ");
        int nodeNum = Integer.parseInt(firstLineSplitted[0]);
        Set<Integer> distances = generateListOfRelevantDistances(Integer.parseInt(firstLineSplitted[1]));
        Map<Node, Integer> allNodes = new HashMap<>();
        Set<Edge> relevantEdges = new HashSet<>();
        String line;

        int count = 1;
        while ((line = fileReader.readLine()) != null) {
            allNodes.put(new Node(Integer.parseInt(line.replaceAll(" ", ""), 2), line), count++);
        }
        int duplicatedRepresentations = nodeNum - allNodes.size();
        Node tempNode = new Node(0, "");
        Edge reverseEdge = new Edge(0, 0, 0);
        for (Map.Entry<Node, Integer> entry : allNodes.entrySet()) {
            for (Integer distance : distances) {
                tempNode.representation = entry.getKey().representation ^ distance;
                Integer endNode = allNodes.get(tempNode);
                if(endNode != null) {
                    Edge edge = new Edge(entry.getValue(), endNode, Integer.bitCount(distance));
                    reverseEdge.end = edge.start;
                    reverseEdge.start = edge.end;
                    if(!relevantEdges.contains(reverseEdge)){
                        relevantEdges.add(edge);
                    }
                }
            }
        }
        List<Edge> sortedRelevantEdges = new ArrayList<>(relevantEdges);
        Collections.sort(sortedRelevantEdges);
        UnionFind unionFind = new UnionFind(nodeNum);
        for (Edge relevantEdge : sortedRelevantEdges) {
            if(unionFind.find(relevantEdge.start) != unionFind.find(relevantEdge.end)){
                unionFind.union(relevantEdge.start, relevantEdge.end);
            }
        }
        System.out.println(unionFind.getClusterCount() - duplicatedRepresentations);
    }


    private static Set<Integer> generateListOfRelevantDistances(int numOfBits){
        Set<Integer> oneBitResult = new HashSet<>();
                 for (int i = 0; i < numOfBits; i++) {
             oneBitResult.add(1 << i);
        }
        Set<Integer> twoBitResult = new HashSet<>(oneBitResult);
        for (Integer integer : oneBitResult) {
            for (int i = 0; i <numOfBits; i++) {
                twoBitResult.add(integer | (1 << i));
            }
        }

        return twoBitResult;
    }

    private static class Edge implements Comparable<Edge>{
        int start;
        int end;
        int cost;

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
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Edge edge = (Edge) o;

            if (start != edge.start) return false;
            return end == edge.end;

        }

        @Override
        public int hashCode() {
            int result = start;
            result = 31 * result + end;
            return result;
        }

        @Override
        public String toString() {
            return "start=" + start +
                    ", end=" + end + ", cost=" + cost;
        }
    }

    private static class Node {
        private int representation;
        private String stringRepresentation;

        public Node(int representation, String stringRepresentation) {
            this.representation = representation;
            this.stringRepresentation = stringRepresentation;
        }

        @Override
        public String toString() {
            return stringRepresentation;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node node = (Node) o;

            return representation == node.representation;

        }

        @Override
        public int hashCode() {
            return representation;
        }
    }
}
