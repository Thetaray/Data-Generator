package com.tr.csvgenerator.RandomDataGenerator.Visualization;

import com.tr.csvgenerator.RandomDataGenerator.Data;

/**
 * Created by IntelliJ IDEA.
 * User: Naor Ben David
 * Mail: naor.bendaivd@thetaray.com
 * Date: 5/1/16
 * Time: 2:49 PM
 */
public interface DataVisualizationService {
    void drawData(Data data, int x, int y, int z) throws Exception;
}
