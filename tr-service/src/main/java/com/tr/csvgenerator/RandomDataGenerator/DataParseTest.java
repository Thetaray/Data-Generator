package com.tr.csvgenerator.RandomDataGenerator;

import CommonObjects.ContainerPackage.RegularContainer;
import CommonObjects.Data;
import CommonObjects.FeatureBuilderPackage.DistributionFeature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tr.csvgenerator.dto.GenerateDataForSupervisedDTO;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: Naor Ben David
 * Mail: naor.bendaivd@thetaray.com
 */
public class DataParseTest {

    @Test
    public void testObjectToJson() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        String value;

        long seed = 1;
        DistributionFeature f1 = new DistributionFeature("normal", new ArrayList<>(Arrays.asList(0.0, 0.2)), seed);
        DistributionFeature f2 = new DistributionFeature("normal", new ArrayList<>(Arrays.asList(0.5, 0.5)), seed);

        value = mapper.writeValueAsString(f1);
        System.out.println(value);

        RegularContainer c = new RegularContainer(new ArrayList<>(Arrays.asList(f1, f2)), 1000);
        c.setLabelColumn(2);
        c.setLabel("0");


        value = mapper.writeValueAsString(c);
        System.out.println(value);

        Data d = new Data("/home/naor/Desktop/test1", seed, 2, new ArrayList<>(Arrays.asList(c)));


        value = mapper.writeValueAsString(d);
        System.out.println(value);


//        GenerateDataForSupervisedDTO d2 = new GenerateDataForSupervisedDTO();
//        d2.setTotal_Size(50);
//        d2.setRank(3);
//        d2.setPositive_Percent(70);
//        d2.setCell_Max_Boundary(100.0);
//
//        d2.setTests(new ArrayList<Jumpable>(Arrays.asList(new Monky("math",80),new Monky("java",100),new Kengero(7,2))));
//
//        value = mapper.writeValueAsString(d2);
//        System.out.println(value);
    }

    @Test
    public void testJsonToObject() throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        String json = "{\"outputFile\":\"/home/naor/Desktop/test1\",\"numOfFeature\":0,\"seed\":1,\"containers\":[{\"@class\":\"com.tr.csvgenerator.DataGenerator.RegularContainer\",\"numberOfElements\":1000,\"features\":[{\"@class\":\"com.tr.csvgenerator.DataGenerator.DistributionFeature\",\"model\":\"normal\",\"params\":[0.0,0.2]},{\"@class\":\"com.tr.csvgenerator.DataGenerator.DistributionFeature\",\"model\":\"normal\",\"params\":[0.5,0.5]}]}]}\n";
        //String json = "{\"outputFile\":\"/home/naor/Desktop/test1\",\"numOfFeature\":0,\"seed\":1,\"containers\":[{\"numOfElements\":1000,\"features\":[{\"model\":\"normal\",\"params\":[0.0,0.2]},{\"model\":\"normal\",\"params\":[0.5,0.5]}]}]}\n";
        //String json = "{\"outputFile\":\"/home/naor/Desktop/test1\",\"numOfFeature\":0,\"seed\":1}";

        //List<DistributionFeature> DistributionFeatureList =  mapper.readValue(json, new TypeReference<List<DistributionFeature>>(){});

        //List<RegularContainer> containerList =  mapper.readValue(json, new TypeReference<List<RegularContainer>>(){});


        Data value = mapper.readValue(json, Data.class);
        System.out.println(value);
    }

    @Test
    public void testJsonToObject2() throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        String json = "{\"tests\":[{\"@class\":\"com.tr.csvgenerator.dto.Monky\",\"name\":\"math\",\"gread\":80},{\"@class\":\"com.tr.csvgenerator.dto.Monky\",\"name\":\"java\",\"gread\":100},{\"@class\":\"com.tr.csvgenerator.dto.Kengero\",\"age\":7,\"kids\":2}],\"total_Size\":50,\"rank\":3,\"positive_Percent\":70,\"cell_Max_Boundary\":100.0}\n";
        System.out.println(json);


        GenerateDataForSupervisedDTO value = mapper.readValue(json, GenerateDataForSupervisedDTO.class);
        System.out.println(value);


    }
}
