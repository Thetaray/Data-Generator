package com.tr.csvgenerator.DataGenerator;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.math.plot.Plot2DPanel;
import org.math.plot.Plot3DPanel;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: Naor Ben David
 * Mail: naor.bendaivd@thetaray.com
 */
public class VisualData {

    public void XYClassters(String[] Names,double[][] x, double[][] y)
    {
        Plot2DPanel panel = new Plot2DPanel();
        for (int i =0; i<Names.length ; i++)
            panel.addScatterPlot(Names[i], x[i],y[i]);


        JFrame frame = new JFrame("a plot panel");
        frame.setSize(300,300);
        frame.setContentPane(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void XYZClassters(String[] Names,double[][] x, double[][] y,double[][] z)
    {
        Plot3DPanel panel = new Plot3DPanel();

        for (int i =0; i<Names.length ; i++)
            panel.addScatterPlot(Names[i], x[i],y[i],z[i]);

        JFrame frame = new JFrame("a plot panel");
        frame.setSize(300,300);
        frame.setContentPane(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public void HistogramPlot(double[] x)
    {
        Plot2DPanel panel = new Plot2DPanel();
        panel.addHistogramPlot("histogram", x,100);
        JFrame frame = new JFrame("a plot panel");
        frame.setSize(300,300);
        frame.setContentPane(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    public static void main(String[] args) throws Exception {

        //SpringApplication.run(Application.class, args);


//        RandomDataGenerator randomData = new RandomDataGenerator();
//        randomData.reSeed(1000);
//        for (int i = 0; i < 1000; i++) {
//            //System.out.print(randomData.nextLong(1, 1000000)+",");
//            //System.out.print(randomData.nextGaussian(0,1)+",");
//            System.out.print(randomData.nextSecureLong(1, 1000000)+",");
//
//        }

//        ForkJoinPool commonPool = ForkJoinPool.commonPool();
//        System.out.println(commonPool.getParallelism());
        //RandomDataGenerator r1 = new RandomDataGenerator();
//       long startTime = System.currentTimeMillis();
////
//
//        Stream.generate(() -> java.util.concurrent.ThreadLocalRandom.current().nextInt(1, 10))//nextGaussian(0,1))
//                .limit(100000000)
//                .collect (Collectors.toList());
//
////        IntStream.range(1, 100000000)
////                .forEach(i -> r1.nextGaussian(0,1));
////
//        long endTime   = System.currentTimeMillis();
//        long totalTime = endTime - startTime;
//       System.out.println("\nTotal time:= "+ totalTime);
//
//
//
//
//        startTime = System.currentTimeMillis();
////
////        for(int j=0 ; j<100000000;j++)
////            java.util.concurrent.ThreadLocalRandom.current().nextInt(1, 10);
//
//        new Random().ints(100000000, 0, 10)
//                .boxed()
//                .collect(Collectors.toCollection(ArrayList::new));
//
////        IntStream.range(1, 100000000)
////                .forEach(i -> r1.nextGaussian(0,1));
////
//        endTime   = System.currentTimeMillis();
//        totalTime = endTime - startTime;
        // System.out.println("\nTotal time:= "+ totalTime);

//        Arrays.asList("a1", "a2", "b1", "c2", "c1")
//                .parallelStream()
//                .filter(s -> {
//                    System.out.format("filter: %s [%s]\n",
//                            s, Thread.currentThread().getName());
//                    return true;
//                })
//                .map(s -> {
//                    System.out.format("map: %s [%s]\n",
//                            s, Thread.currentThread().getName());
//                    return s.toUpperCase();
//                })
//                .forEach(s -> System.out.format("forEach: %s [%s]\n",
//                        s, Thread.currentThread().getName()));

//        double range = 100/3.0;
//        System.out.println(range);
//        System.out.println(Double.BYTES);
//        double maxD = new Double(Double.MAX_VALUE);
//        System.out.println(maxD+":"+System.currentTimeMillis() );
//        maxD++;
//        System.out.println(maxD+":A:"+System.currentTimeMillis() );
//        int x1 = 0;
//        maxD++;
//        System.out.println(maxD+":B:"+System.currentTimeMillis() );
//        x1*=15;
//        maxD++;
//        System.out.println(maxD+":C:"+System.currentTimeMillis() );
//        maxD--;
//        System.out.println(maxD+":D:"+System.currentTimeMillis() );
//        x1-=20;
//        maxD--;
//        System.out.println(maxD+":E"+System.currentTimeMillis() );

//        for(x1=0;x1<10;x1++)
//        {
//            java.lang.Thread.sleep(1000);
//            System.out.println(System.currentTimeMillis() );
//
//        }


        int Low = 10;
        int High = 100;
        RandomDataGenerator r = new RandomDataGenerator();
        //r.reSeed(1000);

        int i = 1000;
//
        double[][] x = new double[4][i];
        double[][] y = new double[4][i];
        String[] names = {"positive","negative","noise","last"};
//       double[] x2 = new double[i];
//       double[] y2 = new double[i];
//
//        ArrayList xN = new ArrayList<Double>();
//        ArrayList yN = new ArrayList<Double>();
//        ArrayList zN = new ArrayList<Double>();
//
//        ArrayList xU = new ArrayList<Double>();
//        ArrayList yU = new ArrayList<Double>();
//        ArrayList zU = new ArrayList<Double>();

        //startTime = System.currentTimeMillis();
        i--;
        double min=High,max=-1000;
        for(;i>=0;i--)
        {
            //x[i - 1] = r.nextGamma(5,1);

            //x[i - 1] = (High-Low)*(r.nextGaussian(0,1)-Double.MIN_VALUE)/(Double.MAX_VALUE-Double.MIN_VALUE)+Low;
            //x[i - 1] = r.nextGaussian(0,2000);

            //x[i - 1] = r.nextGaussian(0,1);

            x[0][i] = r.nextGaussian(0,1);
            y[0][i] = r.nextGaussian(0,1);
            x[1][i] = r.nextGaussian(5,1);
            y[1][i] = r.nextGaussian(0,1);
            x[2][i] = r.nextGaussian(2.5,5);
            y[2][i] = r.nextGaussian(0,1);
            x[3][i] = r.nextGaussian(6,5);
            y[3][i] = r.nextGaussian(0,1);
//            x[0][i] = r.nextUniform(40,60);
//            y[0][i] = r.nextUniform(40,60);
//            switch (i%4)
//            {
//                case 0: {
//                    x[1][i] = r.nextUniform(10, 20);
//                    y[1][i] = r.nextUniform(20, 80);
//                    break;
//                }
//                case 1: {
//                    x[1][i] = r.nextUniform(10, 90);
//                    y[1][i] = r.nextUniform(10, 20);
//                    break;
//                }
//                case 2: {
//                    x[1][i] = r.nextUniform(10, 90);
//                    y[1][i] = r.nextUniform(80, 90);
//                    break;
//                }
//                case 3: {
//                    x[1][i] = r.nextUniform(80, 90);
//                    y[1][i] = r.nextUniform(20, 80);
//                    break;
//                }
//            }

            //y[i - 1] = r.nextGamma(5,1);
            // x2[i - 1] = r.nextUniform(15,40);
            // y2[i - 1] = r.nextUniform(0,20);

//            xN.add(r.nextGaussian(0,1));
//            yN.add(r.nextGaussian(0,1));
//            zN.add(r.nextGaussian(0,1));
//
//
//            xN.add(r.nextUniform(0,0.2));
//            yN.add(r.nextUniform(0,0.2));
//            zN.add(r.nextUniform(0,0.2));

            //x[i - 1] = r.nextT(10000);
            //if(x[i-1]<min)
            //    min = x[i-1];
            // if(x[i-1]>max)
            //    max = x[i-1];

        }
        //for(i = 10000000;i>0;i--)y[i - 1] = r.nextGamma(5,1);
        //endTime   = System.currentTimeMillis();
        // totalTime = endTime - startTime;
        // System.out.println("\nTotal time:= "+ totalTime);

        new VisualData().XYClassters(names,x,y);


        //Normalized
//        System.out.println("min: "+min+" max: "+max+" Str: "+r.nextHexString(10));
//        double maxNew=-1;
//        for(i= 10000;i>0;i--)
//        {
//            x[i - 1] = ((x[i - 1]-min)/(max-min))*(High-Low) + Low;
//            if(x[i-1]<min)
//                min = x[i-1];
//            if(x[i-1]>maxNew)
//                maxNew = x[i-1];
//        }
//        System.out.println("min: "+min+" maxNew: "+maxNew);



    }
}
