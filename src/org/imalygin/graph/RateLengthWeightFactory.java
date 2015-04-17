package org.imalygin.graph;

public class RateLengthWeightFactory implements LengthWeightFactory {
    @Override
    public LengthWeight createLengthWeight(int length, int weight) {
        return new RateLightWeight(length, weight);
    }
}
