package org.imalygin.graph;

import com.google.common.collect.MinMaxPriorityQueue;
import org.imalygin.util.FileUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.lang.Integer.parseInt;

public class PrimAlgorithm {
    private final int nodeNum;
    private final Node startNode;

    public PrimAlgorithm(){
        BufferedReader fileReader = FileUtils.createReaderForFile("org/imalygin/graph/edges.txt");
        String[] firstLine;
        try {
            firstLine = fileReader.readLine().split(" ");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        nodeNum = Integer.parseInt(firstLine[0]);
        int edgeNum = Integer.parseInt(firstLine[1]);

        Map<Integer, Node> nodeByNum = new HashMap<>();
        for (int i = 0; i < edgeNum; i++) {
            try {
                String line = fileReader.readLine();
                String[] split = line.split(" ");
                int startNode = parseInt(split[0]);
                int endNode = parseInt(split[1]);
                int weight = parseInt(split[2]);
                addNodeIfAbsent(nodeByNum, startNode);
                addNodeIfAbsent(nodeByNum, endNode);
                nodeByNum.get(startNode).addEdge(new Edge(weight, nodeByNum.get(startNode), nodeByNum.get(endNode)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        startNode = nodeByNum.values().iterator().next();
    }

    public int buildMst(){
        Node currentNode = startNode;
        int count = 0;
        int mstWeight = 0;
        MinMaxPriorityQueue<Edge> queue = MinMaxPriorityQueue.create();
        currentNode.visited = true;
        queue.addAll(currentNode.edges);
        Edge lastEdge;
        while (!queue.isEmpty()){
            lastEdge = queue.pollFirst();
            if(!lastEdge.startPoint.visited || lastEdge.endPoint.visited ){
                continue;
            }
            mstWeight+= lastEdge.weight;
            currentNode.removeEdge(lastEdge);

            currentNode = lastEdge.endPoint;
            queue.addAll(currentNode.edges);
            currentNode.visited = true;
            count++;
        }
        assert count == nodeNum - 1;
        return mstWeight;
    }


    private void addNodeIfAbsent(Map<Integer, Node> nodeByNum, int node) {
        if(!nodeByNum.containsKey(node)){
            nodeByNum.put(node, new Node(node));
        }
    }

    private static class Node {
        private final Set<Edge> edges = new HashSet<>();
        private boolean visited;
        private final int num;

        public Node(int num) {
            this.num = num;
        }

        public void addEdge(Edge edge){
            edges.add(edge);
            edge.endPoint.edges.add(new Edge(edge.weight, edge.endPoint, edge.startPoint));
        }

        public void removeEdge(Edge edge){
            edges.remove(edge);
            edge.endPoint.edges.remove(new Edge(edge.weight, edge.endPoint, edge.startPoint));
        }

        @Override
        public String toString() {
            return "n=" + num + ", v=" + visited;
        }
    }

    private static class Edge implements Comparable<Edge> {
        private final int weight;
        private final Node startPoint;
        private final Node endPoint;

        public Edge(int weight, Node startPoint, Node endPoint) {
            this.weight = weight;
            this.startPoint = startPoint;
            this.endPoint = endPoint;
        }

        @Override
        public int compareTo(Edge that) {
            return this.weight - that.weight;
        }

        @Override
        public String toString() {
            return "s=" + startPoint.num + ", e=" + endPoint.num + ", w=" + weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Edge edge = (Edge) o;

            if (weight != edge.weight) return false;
            if (startPoint != null ? !startPoint.equals(edge.startPoint) : edge.startPoint != null) return false;
            return !(endPoint != null ? !endPoint.equals(edge.endPoint) : edge.endPoint != null);

        }

        @Override
        public int hashCode() {
            int result = weight;
            result = 31 * result + (startPoint != null ? startPoint.hashCode() : 0);
            result = 31 * result + (endPoint != null ? endPoint.hashCode() : 0);
            return result;
        }
    }

    public static void main(String[] args) {
        System.out.println(new PrimAlgorithm().buildMst());
    }

}
