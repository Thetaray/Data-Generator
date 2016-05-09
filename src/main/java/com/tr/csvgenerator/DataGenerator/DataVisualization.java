package com.tr.csvgenerator.DataGenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.math.plot.Plot2DPanel;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by IntelliJ IDEA.
 * User: Naor Ben David
 * Mail: naor.bendaivd@thetaray.com
 */
@Service
public class DataVisualization implements DataVisualizationService {

    private static String[] Colors = new String[]{"MediumSeaGreen", "DarkCyan", "DarkTurquoise", "DarkViolet", "LightSlateGray", "Gold", "GreenYellow", "AntiqueWhite"};

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
                "\t\t\"numberOfElements\":200,\n" +
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
                "\t\t\"numberOfElements\":200,\n" +
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
                "\t\t\"numberOfElements\":200,\n" +
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
                "\t\t\"numberOfElements\":200,\n" +
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
                "\t\t\"numberOfElements\":200,\n" +
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
                "\t\t\"numberOfElements\":200,\n" +
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
                "\t\t\t\t\"params\":[1000.0,1500.0]\n" +
                "\t\t\t}\n" +
                "\t\t]\n" +
                "\t}\t\n" +
                "]\n" +
                "}";
        Data data = mapper.readValue(json, Data.class);
        return data;
    }

    public void HistogramPlot(double[] x) {
        Plot2DPanel panel = new Plot2DPanel();
        panel.addHistogramPlot("histogram", x, 100);
        JFrame frame = new JFrame("a plot panel");
        frame.setSize(300, 300);
        frame.setContentPane(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    //@Test
    //public void testDrawData() throws Exception {
    //public static void main(String[] args) throws Exception {

    //    Data d;

//        long seed = 1;
//
//
//        DistributionFeature x1 = new DistributionFeature("uniform", new ArrayList<Double>(Arrays.asList(100.0, 300.0)), seed);
//        DistributionFeature y1 = new DistributionFeature("uniform", new ArrayList<Double>(Arrays.asList(200.0, 400.0)), seed);
//
//        RegularContainer c1 = new RegularContainer(new ArrayList<Feature>(Arrays.asList(x1, y1)), 700);
//        c1.setLabelColumn(2);
//        c1.setLabel("0");
//
//
//        DistributionFeature x2 = new DistributionFeature("uniform", new ArrayList<Double>(Arrays.asList(300.0, 500.0)), seed);
//        DistributionFeature y2 = new DistributionFeature("uniform", new ArrayList<Double>(Arrays.asList(400.0, 600.0)), seed);
//
//
//        RegularContainer c2 = new RegularContainer(new ArrayList<Feature>(Arrays.asList(x2, y2)), 1000);
//        c2.setLabelColumn(2);
//        c2.setLabel("2");
//
//        Data d = new Data("/home/naor/Desktop/test1", seed, 2, 1, new ArrayList<Container>(Arrays.asList(c1,c2)));


//        d = jsonToObject();
//        JFrame frame = new JFrame("a plot panel");
//        frame.setContentPane(drawData(d,0,1,2));
//        frame.setSize(300, 300);
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);

    //}

    @Override
    public void drawData(Data data, int x, int y, int z) throws Exception {

        Map<String, Integer> cluster = new HashMap<>();
        List<Object> element = null;

        //get number of clusters with there's name
        for (Container c : data.getContainers()) {
            if (c.getLabel() == null)
                c.setLabel("NO_LABEL");
            Integer sum = cluster.get(c.getLabel());
            if (sum == null) sum = 0;
            cluster.put(c.getLabel(), c.getNumberOfElements() + sum);
        }

        //build the Data[#cluster][#feature]
        double finData[][][] = new double[cluster.size()][3][];

        //PlotlyData
        PlotlyCluster[] plotlyclustr = new PlotlyCluster[cluster.size()];

        Object[] keys = cluster.keySet().toArray();
        for (int i = 0; i < keys.length; i++) {
            int numOfElement = cluster.get(keys[i]);
            //Used it for plot at panel
            //finData[i][0] = new double[numOfElement];
            //finData[i][1] = new double[numOfElement];
            //finData[i][2] = new double[numOfElement];

            plotlyclustr[i] = new PlotlyCluster();
            plotlyclustr[i].x = new double[numOfElement];
            plotlyclustr[i].y = new double[numOfElement];
            plotlyclustr[i].z = new double[numOfElement];
            plotlyclustr[i].marker = new Marker();

            int elementNum = 0;

            //foreach container classify to cluster

            for (Container c : data.getContainers()) {
                if (c.getLabel().equals(keys[i])) {
                    for (int j = 0; j < c.getNumberOfElements(); j++, elementNum++) {
                        if (c.hasNext())
                            element = c.Next();
                        else break;

                        if (element.size() > 0)
                            plotlyclustr[i].x[elementNum] = (double) element.get(x);
                        if (element.size() > 1)
                            plotlyclustr[i].y[elementNum] = (double) element.get(y);
                        if (element.size() > 2 && z > 0)
                            plotlyclustr[i].z[elementNum] = (double) element.get(z);
                    }
                }
            }
        }



        String jsonOfMyData = "[";
        for (int i = 0; i < plotlyclustr.length; i++) {
            plotlyclustr[i].getMarker().setColor(Colors[i]);
            if (i < plotlyclustr.length - 1)
                jsonOfMyData += plotlyclustr[i] + ",";
            else
                jsonOfMyData += plotlyclustr[i] + "]";
        }


        File f = new File((DataVisualization.class.getResource("../../../../static/")).getPath() + "LastRun/plotlyData.js");
        f.getParentFile().mkdirs();
        PrintWriter out = new PrintWriter(f);
        out.println("var myData = " + jsonOfMyData);
        out.close();

        f = new File((DataVisualization.class.getResource("../../../../static/")).getPath() + "LastRun/index.html");
        out = new PrintWriter(f);
        out.println("<head>\n" +
                "<!-- Plotly.js -->\n" +
                "<script src=\"https://cdn.plot.ly/plotly-latest.min.js\"></script>\n" +
                "<script src=\"plotlyData.js\"></script>\n" +
                "</head>\n" +
                "<body>\n" +
                "<!-- Plotly chart will be drawn inside this DIV -->\n" +
                "<div id=\"myDiv\" style=\"width:100%;height:100%\"></div>\n" +
                "<script>\n" +
                "/* JAVASCRIPT CODE GOES HERE */\n" +
                "var layout = {margin: {\n" +
                "    l: 0,\n" +
                "    r: 0,\n" +
                "    b: 0,\n" +
                "    t: 0\n" +
                "  }};\n" +
                "  //alert(myData);\n" +
                "Plotly.newPlot('myDiv', myData, layout);\n" +
                "</script>\n" +
                "</body>");
        out.close();

    }


}