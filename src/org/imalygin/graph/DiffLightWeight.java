package org.imalygin.graph;

public class DiffLightWeight implements LengthWeight {
    private final int length;
    private final int weight;
    private final int diff;

    public DiffLightWeight(int length, int weight) {
        this.length = length;
        this.weight = weight;
        this.diff = weight - length;
    }

    @Override
    public int compareTo(LengthWeight that) {
        if(this.getOrder() == that.getOrder() ){
            return this.getWeight() - that.getWeight();
        } else {
            return (int) (this.getOrder() - that.getOrder());
        }

    }
    @Override
    public int getLength() {
        return length;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public double getOrder() {
        return diff;
    }

}
