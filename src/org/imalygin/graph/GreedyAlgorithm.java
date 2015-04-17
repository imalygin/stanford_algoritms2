package org.imalygin.graph;

import com.google.common.collect.MinMaxPriorityQueue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.lang.Integer.parseInt;

public class GreedyAlgorithm {

    private final LengthWeightFactory factory;
    private BufferedReader fileReader;

    public GreedyAlgorithm(LengthWeightFactory factory){
        this.factory = factory;
        InputStream inputStream = OrderOfRatio.class.getClassLoader().getResourceAsStream("org/imalygin/graph/jobs.txt");
        fileReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public void doStuff(){
        MinMaxPriorityQueue<LengthWeight> queue = MinMaxPriorityQueue.create();
        int numberOfJobs = numberOfJobs();
        for (int i = 0; i < numberOfJobs; i++) {
            queue.add(getNext());
        }

        long completionTime = 0;
        long sum = 0;
        while(!queue.isEmpty()){
            LengthWeight lengthWeight = queue.pollLast();
            completionTime += lengthWeight.getLength();
            sum += completionTime * lengthWeight.getWeight() ;
        }
//69119377652
//67311454237
        System.out.println(sum);
    }

    private int numberOfJobs() {
        try {
            return parseInt(fileReader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public LengthWeight getNext(){
        try {
            String line = fileReader.readLine();
            String[] split = line.split(" ");
            return factory.createLengthWeight(parseInt(split[1]), parseInt(split[0]));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
