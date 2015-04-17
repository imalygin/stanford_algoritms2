package org.imalygin.graph;

public class RateLightWeight implements LengthWeight {
    private final int length;
    private final int weight;
    private final double rate;

    public RateLightWeight(int length, int weight) {
        this.length = length;
        this.weight = weight;
        this.rate = (double)weight / length;
    }

    @Override
    public int compareTo(LengthWeight that) {
       /* if(this.getOrder() == that.getOrder() ){
            return this.getWeight() - that.getWeight();
        } else {*/
        if(this.getOrder() > that.getOrder()){
            return 1;
        } else if(this.getOrder() < that.getOrder()){
            return -1;
        } else {
            return 0;
        }
       // }

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
        return rate;
    }

}
