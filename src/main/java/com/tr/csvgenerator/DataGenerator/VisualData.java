package com.tr.csvgenerator.DataGenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.math.plot.Plot2DPanel;
import org.math.plot.Plot3DPanel;
import javax.swing.*;
import java.io.IOException;
import java.util.*;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: Naor Ben David
 * Mail: naor.bendaivd@thetaray.com
 */
public class VisualData {


    public void HistogramPlot(double[] x) {
        Plot2DPanel panel = new Plot2DPanel();
        panel.addHistogramPlot("histogram", x, 100);
        JFrame frame = new JFrame("a plot panel");
        frame.setSize(300, 300);
        frame.setContentPane(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void drawData(Data data,int x,int y,int z) throws Exception {

        Map<String,Integer> cluster = new HashMap<>();
        List<Object> element = null;

        //get number of clusters with there's name
        for(Container c : data.getContainers())
        {
            if(c.getLabel()==null)
                c.setLabel("NO_LABEL");
            Integer sum = cluster.get(c.getLabel());
            if(sum==null)sum=0;
            cluster.put(c.getLabel(),c.getNumberOfElements()+sum);
        }

        //build the Data[#cluster][#feature]
        double finData[][][] = new double[cluster.size()][3][];

        Object[] keys = cluster.keySet().toArray();
        for (int i=0; i < keys.length; i++)
        {
            int numOfElement = cluster.get(keys[i]);
            finData[i][0] = new double[numOfElement];
            finData[i][1] = new double[numOfElement];
            finData[i][2] = new double[numOfElement];

            int elementNum = 0;

            //foreach container classify to cluster

            for (Container c: data.getContainers())
            {
                if(c.getLabel().equals(keys[i]))
                {
                    for (int j=0 ; j < c.getNumberOfElements();j++, elementNum++)
                    {
                        if(c.hasNext()) {
                            element = c.Next();
                        }
                        finData[i][0][elementNum] = (double) element.get(x);
                        finData[i][1][elementNum] = (double) element.get(y);
                        if(z>0)
                            finData[i][2][elementNum] = (double) element.get(z);
                    }
                }
            }
        }

        JFrame frame = new JFrame("a plot panel");
        if(z>0)
        {
            Plot3DPanel panel = new Plot3DPanel();

            for (int i = 0; i < keys.length; i++)
                panel.addScatterPlot(keys[i].toString(), finData[i][0], finData[i][1], finData[i][2]);

            frame.setContentPane(panel);
        }
        else
        {
            Plot2DPanel panel = new Plot2DPanel();

            for (int i = 0; i < keys.length; i++)
                panel.addScatterPlot(keys[i].toString(), finData[i][0], finData[i][1]);

            frame.setContentPane(panel);
        }

        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);



    }

    //@Test
    //public void testDrawData() throws Exception {
    public static void main1(String[] args) throws Exception {
        long seed = 1;


        DistributionFeature x1 = new DistributionFeature("uniform", new ArrayList<Double>(Arrays.asList(100.0, 300.0)), seed);
        DistributionFeature y1 = new DistributionFeature("uniform", new ArrayList<Double>(Arrays.asList(200.0, 400.0)), seed);

        RegularContainer c1 = new RegularContainer(new ArrayList<Feature>(Arrays.asList(x1, y1)), 700);
        c1.setLabelColumn(2);
        c1.setLabel("0");




        DistributionFeature x2 = new DistributionFeature("uniform", new ArrayList<Double>(Arrays.asList(300.0, 500.0)), seed);
        DistributionFeature y2 = new DistributionFeature("uniform", new ArrayList<Double>(Arrays.asList(400.0, 600.0)), seed);


        RegularContainer c2 = new RegularContainer(new ArrayList<Feature>(Arrays.asList(x2, y2)), 1000);
        c2.setLabelColumn(2);
        c2.setLabel("2");

        Data d = new Data("/home/naor/Desktop/test1", seed, 2, 1, new ArrayList<Container>(Arrays.asList(c1,c2)));




        d = jsonToObject();
        drawData(d,0,1,2);

    }


    public static Data jsonToObject() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        String json = "{\n" +
                "\"numOfFeature\":0,\n" +
                "\"seed\":2,\n" +
                "\"shuffle\":2,\n" +
                "\"pk_column\":2,\n" +
                "\"containers\":\n" +
                "[\n" +
                "\t{\n" +
                "\t\t\"@class\":\"com.tr.csvgenerator.DataGenerator.RegularContainer\",\n" +
                "\t\t\"numberOfElements\":1000,\n" +
                "\t\t\"label\": \"1\",\n" +
                "      \t\"labelColumn\": 3,\n" +
                "\t\t\"features\":\n" +
                "\t\t[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"@class\":\"com.tr.csvgenerator.DataGenerator.DistributionFeature\",\n" +
                "\t\t\t\t\"model\":\"uniform\",\n" +
                "\t\t\t\t\"params\":[500.0,1000.0]\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"@class\":\"com.tr.csvgenerator.DataGenerator.DistributionFeature\",\n" +
                "\t\t\t\t\"model\":\"uniform\",\n" +
                "\t\t\t\t\"params\":[500.0,1000.0]\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"@class\":\"com.tr.csvgenerator.DataGenerator.DistributionFeature\",\n" +
                "\t\t\t\t\"model\":\"uniform\",\n" +
                "\t\t\t\t\"params\":[500.0,1000.0]\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"@class\":\"com.tr.csvgenerator.DataGenerator.RegularContainer\",\n" +
                "\t\t\"numberOfElements\":1000,\n" +
                "\t\t\"label\": \"0\",\n" +
                "      \t\"labelColumn\": 3,\n" +
                "\t\t\"features\":\n" +
                "\t\t[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"@class\":\"com.tr.csvgenerator.DataGenerator.DistributionFeature\",\n" +
                "\t\t\t\t\"model\":\"uniform\",\n" +
                "\t\t\t\t\"params\":[0.0,500.0]\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"@class\":\"com.tr.csvgenerator.DataGenerator.DistributionFeature\",\n" +
                "\t\t\t\t\"model\":\"uniform\",\n" +
                "\t\t\t\t\"params\":[500.0,1000.0]\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"@class\":\"com.tr.csvgenerator.DataGenerator.DistributionFeature\",\n" +
                "\t\t\t\t\"model\":\"uniform\",\n" +
                "\t\t\t\t\"params\":[500.0,1000.0]\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"@class\":\"com.tr.csvgenerator.DataGenerator.RegularContainer\",\n" +
                "\t\t\"numberOfElements\":1000,\n" +
                "\t\t\"label\": \"0\",\n" +
                "      \t\"labelColumn\": 3,\n" +
                "\t\t\"features\":\n" +
                "\t\t[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"@class\":\"com.tr.csvgenerator.DataGenerator.DistributionFeature\",\n" +
                "\t\t\t\t\"model\":\"uniform\",\n" +
                "\t\t\t\t\"params\":[1000.0,1500.0]\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"@class\":\"com.tr.csvgenerator.DataGenerator.DistributionFeature\",\n" +
                "\t\t\t\t\"model\":\"uniform\",\n" +
                "\t\t\t\t\"params\":[500.0,1000.0]\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"@class\":\"com.tr.csvgenerator.DataGenerator.DistributionFeature\",\n" +
                "\t\t\t\t\"model\":\"uniform\",\n" +
                "\t\t\t\t\"params\":[500.0,1000.0]\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"@class\":\"com.tr.csvgenerator.DataGenerator.RegularContainer\",\n" +
                "\t\t\"numberOfElements\":1000,\n" +
                "\t\t\"label\": \"0\",\n" +
                "      \t\"labelColumn\": 3,\n" +
                "\t\t\"features\":\n" +
                "\t\t[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"@class\":\"com.tr.csvgenerator.DataGenerator.DistributionFeature\",\n" +
                "\t\t\t\t\"model\":\"uniform\",\n" +
                "\t\t\t\t\"params\":[500.0,1000.0]\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"@class\":\"com.tr.csvgenerator.DataGenerator.DistributionFeature\",\n" +
                "\t\t\t\t\"model\":\"uniform\",\n" +
                "\t\t\t\t\"params\":[0.0,500.0]\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"@class\":\"com.tr.csvgenerator.DataGenerator.DistributionFeature\",\n" +
                "\t\t\t\t\"model\":\"uniform\",\n" +
                "\t\t\t\t\"params\":[500.0,1000.0]\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"@class\":\"com.tr.csvgenerator.DataGenerator.RegularContainer\",\n" +
                "\t\t\"numberOfElements\":1000,\n" +
                "\t\t\"label\": \"0\",\n" +
                "      \t\"labelColumn\": 3,\n" +
                "\t\t\"features\":\n" +
                "\t\t[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"@class\":\"com.tr.csvgenerator.DataGenerator.DistributionFeature\",\n" +
                "\t\t\t\t\"model\":\"uniform\",\n" +
                "\t\t\t\t\"params\":[500.0,1000.0]\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"@class\":\"com.tr.csvgenerator.DataGenerator.DistributionFeature\",\n" +
                "\t\t\t\t\"model\":\"uniform\",\n" +
                "\t\t\t\t\"params\":[1000.0,1500.0]\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"@class\":\"com.tr.csvgenerator.DataGenerator.DistributionFeature\",\n" +
                "\t\t\t\t\"model\":\"uniform\",\n" +
                "\t\t\t\t\"params\":[500.0,1000.0]\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"@class\":\"com.tr.csvgenerator.DataGenerator.RegularContainer\",\n" +
                "\t\t\"numberOfElements\":1000,\n" +
                "\t\t\"label\": \"0\",\n" +
                "      \t\"labelColumn\": 3,\n" +
                "\t\t\"features\":\n" +
                "\t\t[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"@class\":\"com.tr.csvgenerator.DataGenerator.DistributionFeature\",\n" +
                "\t\t\t\t\"model\":\"uniform\",\n" +
                "\t\t\t\t\"params\":[500.0,1000.0]\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"@class\":\"com.tr.csvgenerator.DataGenerator.DistributionFeature\",\n" +
                "\t\t\t\t\"model\":\"uniform\",\n" +
                "\t\t\t\t\"params\":[500.0,1000.0]\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"@class\":\"com.tr.csvgenerator.DataGenerator.DistributionFeature\",\n" +
                "\t\t\t\t\"model\":\"uniform\",\n" +
                "\t\t\t\t\"params\":[0.0,500.0]\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"@class\":\"com.tr.csvgenerator.DataGenerator.RegularContainer\",\n" +
                "\t\t\"numberOfElements\":1000,\n" +
                "\t\t\"label\": \"0\",\n" +
                "      \t\"labelColumn\": 3,\n" +
                "\t\t\"features\":\n" +
                "\t\t[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"@class\":\"com.tr.csvgenerator.DataGenerator.DistributionFeature\",\n" +
                "\t\t\t\t\"model\":\"uniform\",\n" +
                "\t\t\t\t\"params\":[500.0,1000.0]\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"@class\":\"com.tr.csvgenerator.DataGenerator.DistributionFeature\",\n" +
                "\t\t\t\t\"model\":\"uniform\",\n" +
                "\t\t\t\t\"params\":[500.0,1000.0]\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"@class\":\"com.tr.csvgenerator.DataGenerator.DistributionFeature\",\n" +
                "\t\t\t\t\"model\":\"uniform\",\n" +
                "\t\t\t\t\"params\":[500.0,1000.0]\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t}\t\n" +
                "]\n" +
                "}";
        Data data = mapper.readValue(json, Data.class);
        return data;
    }

}