package com.tr.csvgenerator.GenerateDataForSupervised.Shapes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Naor on 28/02/16.
 */
public class Rectangle extends Shape {

    private ArrayList<Double> UpperBounds;
    private ArrayList<Double> LowerBounds;
    private ArrayList<Double> IntrvalSize;
    private List<Integer> categoryIndexes;

    public Rectangle(Integer dimension, Double CellMaxBoundary, List<Integer> categoryIndexes) {
        super(dimension, CellMaxBoundary);
        UpperBounds = new ArrayList<Double>(dimension);
        LowerBounds = new ArrayList<Double>(dimension);
        IntrvalSize = new ArrayList<Double>(dimension);
        categoryIndexes = categoryIndexes;
        initialize();
    }

    private void initialize() {
        double r1, r2;
        int n = getDimension();
        Random random = new Random();
        double maxRang = CellMaxBoundary;
        double minRang = maxRang * (-1);
        double Interval = maxRang * 2;
        for (int i = 0; i < n; i++) {
            if (categoryIndexes != null && categoryIndexes.contains(new Integer(i))) {

            } else {
                r1 = minRang + (Interval) * random.nextDouble();    //Double.MAX_VALUE;
                r2 = minRang + (Interval) * random.nextDouble();
                UpperBounds.add(i, Math.max(r1, r2));
                LowerBounds.add(i, Math.min(r1, r2));
                IntrvalSize.add(i, Math.abs(r1 - r2));
            }

        }
    }

    public Double getUpperVal(int index) {
        return UpperBounds.get(index);
    }

    public Double getLowerVal(int index) {
        return LowerBounds.get(index);
    }

    public Double getIntrval(int index) {
        return IntrvalSize.get(index);
    }
}
