package org.imalygin.clustering;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import java.util.*;

public class UnionFind {

    private Map<Integer, Node> map;
    private int clusterCount;

    public UnionFind(int initNumberOfNodes) {
        map = new HashMap<>();
        for (int i = 1; i < initNumberOfNodes + 1; i++) {
            map.put(i, new Node(i));
        }
        clusterCount = initNumberOfNodes;
    }

    public int find(int node){
        return map.get(node).leaderPtr.id;
    }

    public void union(int nodeNum1, int nodeNum2){
        Node node1 = map.get(nodeNum1);
        Node node2 = map.get(nodeNum2);
        Node biggerLeader;
        Node lesserLeader;
        if(node1.leaderPtr.count > node2.leaderPtr.count) {
            biggerLeader = node1.leaderPtr;
            lesserLeader = node2.leaderPtr;
        } else {
            biggerLeader = node2.leaderPtr;
            lesserLeader = node1.leaderPtr;
        }
        lesserLeader.leaderPtr = biggerLeader;
        for (Node subNode : lesserLeader.subNodes) {
            subNode.leaderPtr = biggerLeader;
        }
        biggerLeader.subNodes.add(lesserLeader);
        biggerLeader.subNodes.addAll(lesserLeader.subNodes);
        biggerLeader.count = biggerLeader.subNodes.size();
        clusterCount--;
        lesserLeader.subNodes = Collections.emptyList();
        lesserLeader.count = 0;
    }

    public int getClusterCount(){
        return clusterCount;
    }

    private static class Node {
        Node leaderPtr;
        int id;
        int count;
        List<Node> subNodes = new ArrayList<>();

        public Node(int id) {
            this.id = id;
            this.leaderPtr = this;
            this.count = 0;
        }

        @Override
        public String toString() {
            return "Node{" +
                    ", id=" + id +
                    ", count=" + count +
                    ", subNodes=" + Collections2.transform(subNodes, new Function<Node, String>() {
                @Override
                public String apply(Node node) {
                    return Integer.toString(node.id);
                }
            });
        }
    }
}
