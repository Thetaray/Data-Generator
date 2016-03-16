package com.tr.csvgenerator.GenerateDataForSupervised;

import com.tr.csvgenerator.GenerateDataForSupervised.Shapes.Rectangle;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by naor on 28/02/16.
 */
public class SmallDataSetGenerator {

    private Double[][] matrix;
    private int n;
    private Rectangle rec;
    private Integer pos;
    private Integer neg;


    public SmallDataSetGenerator(Integer pos, Integer neg, Rectangle rec) {
        this.pos = pos;
        this.neg = neg;
        this.rec = rec;
        this.n = rec.getDimension();
        int m = pos + neg;
        this.matrix = new Double[m - 1][n + 1];

        addPositivesItems();
        setLabelAtt();
        addNegativeItems();

    }

    private void addPositivesItems() {
        //fill att1, att2,..,attN for pos Items
        int n = rec.getDimension();
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            Double rangeMin = rec.getUpperVal(i);
            Double rangeMax = rec.getLowerVal(i);
            Double Interval = rec.getIntrval(i);

            for (int j = 0; j < pos; j++) {
                matrix[j][i] = rangeMin + (Interval) * r.nextDouble();
            }
        }

    }

    private void setLabelAtt() {
        for (int i = 0; i < pos; i++)
            matrix[i][n] = 1.0;
    }

    private void addNegativeItems() {
        Random r = new Random();

        for (int i = pos; i < pos + neg - 1; i++) {
            int randPosIndex = r.nextInt(pos);
            int NumOfAttChange = r.nextInt(n);
            ArrayList<Integer> indexToChange = new ArrayList(NumOfAttChange);

            for (int j = 0; j < NumOfAttChange; j++) {
                indexToChange.add(r.nextInt(n));
            }

            for (int j = 0; j < n; j++) {
                if (indexToChange.contains(j)) {
                    if (r.nextBoolean())
                        matrix[i][j] = matrix[randPosIndex][j] + rec.getIntrval(j);
                    else
                        matrix[i][j] = matrix[randPosIndex][j] - rec.getIntrval(j);
                } else {
                    matrix[i][j] = matrix[randPosIndex][j];
                }
            }
            //negative label
            matrix[i][n] = 0.0;
        }
    }

    public int getDimension() {
        return rec.getDimension();
    }

    public int numOfSamples() {
        return pos + neg - 1;
    }

    public Double valAt(int i, int j) {
        return matrix[i][j];
    }
}
