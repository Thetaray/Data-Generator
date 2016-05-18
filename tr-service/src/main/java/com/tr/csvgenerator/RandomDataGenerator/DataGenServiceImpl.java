package com.tr.csvgenerator.RandomDataGenerator;

import RandomDataGeneratorObject.Data;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User: Naor Ben David
 * Mail: naor.bendaivd@thetaray.com
 */
@Service
public class DataGenServiceImpl implements DataGenService {

    //input: json
    @Override
    public String gen(Data d) throws InterruptedException {

        ProducerConsumer pc = new ProducerConsumer(d);

        return d.getOutputFile();
    }
}
