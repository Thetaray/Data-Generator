package com.tr.csvgenerator.DataGenerator;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: Naor Ben David
 * Mail: naor.bendaivd@thetaray.com
 */
public class DataGenService {

    //input: json
    public void gen(Data d) throws InterruptedException {
//        long seed = 1;
//        Feature f1 = new DistributionFeature("normal",new ArrayList<Double>(Arrays.asList(0.0,0.2)),seed);
//        Feature f2 = new DistributionFeature("normal",new ArrayList<Double>(Arrays.asList(0.5,0.5)),seed);
//        Container c = new RegularContainer(new ArrayList<Feature>(Arrays.asList(f1,f2)),1000)
//                .setLabelColumn(2)
//                .setLabel("0");
//        Data d = new Data("/home/naor/Desktop/test1", seed, 2, 1, new ArrayList<Container>(Arrays.asList(c)));
//
        ProducerConsumer pc = new ProducerConsumer(d);
    }
}
