package org.imalygin.graph;

public class DiffLengthWeightFactory implements LengthWeightFactory {
    @Override
    public LengthWeight createLengthWeight(int length, int weight) {
        return new DiffLightWeight(length, weight);
    }
}
