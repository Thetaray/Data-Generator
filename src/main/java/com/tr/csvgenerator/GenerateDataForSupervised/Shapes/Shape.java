package com.tr.csvgenerator.GenerateDataForSupervised.Shapes;

/**
 * Created by naor on 01/03/16.
 */
public abstract class Shape {

    protected Double CellMaxBoundary = Double.MAX_VALUE;
    private Integer dimension;

    public Shape(Integer dimension, Double CellMaxBoundary) {
        this.dimension = dimension;
        this.CellMaxBoundary = CellMaxBoundary;
    }

    public Integer getDimension() {
        return dimension;
    }

    public void setDimension(Integer dimension) {
        this.dimension = dimension;
    }
}
