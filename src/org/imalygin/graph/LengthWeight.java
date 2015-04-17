package org.imalygin.graph;

/**
 * Created by wax on 3/28/2015.
 */
public interface LengthWeight extends Comparable<LengthWeight> {

    int getLength();
    int getWeight();
    double getOrder();
}
