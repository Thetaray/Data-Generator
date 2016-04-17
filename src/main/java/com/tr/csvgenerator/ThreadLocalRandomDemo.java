package com.tr.csvgenerator;

/**
 * Created by naor on 4/10/16.
 */
import org.apache.commons.math3.random.RandomDataGenerator;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.ThreadLocalRandom;
public class ThreadLocalRandomDemo {
    public static void main(String[] args) {

        //Performance testing

        int NumThread = 10000000;
        int i=0;
        //Test1
        long startTime = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool();
        for(; i< NumThread; i++)
            pool.invoke(new TestTask("Task: "+i));
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Total time:= "+ totalTime);
        Random r = new Random();
        i=0;
        //Test2
        startTime = System.currentTimeMillis();
        for(i=0; i< NumThread; i++)r.nextInt();
            //System.out.print(+",");
        endTime   = System.currentTimeMillis();
        totalTime = endTime - startTime;
        System.out.println("\nTotal time:= "+ totalTime);
        //Test3
        RandomDataGenerator ra = new RandomDataGenerator();
        startTime = System.currentTimeMillis();
        for(i=0; i< NumThread; i++)ra.nextInt(0,1);

        endTime   = System.currentTimeMillis();
        totalTime = endTime - startTime;
        System.out.println("\nTotal time:= "+ totalTime);
        //Test 4
        startTime = System.currentTimeMillis();
        for(i=0; i< NumThread; i++)ra.nextGaussian(0.5,1);

        endTime   = System.currentTimeMillis();
        totalTime = endTime - startTime;
        System.out.println("\nTotal time:= "+ totalTime);
    }
}
class TestTask extends ForkJoinTask<String> {
    private String msg = null;
    public TestTask(String msg){
        this.msg = msg;
    }
    private static final long serialVersionUID = 1L;
    @Override
    protected boolean exec() {
        int i = ThreadLocalRandom.current().nextInt(1, 10);
        //System.out.println("ThreadLocalRandom for "+msg+":"+i);
        return true;
    }
    @Override
    public String getRawResult() {
        return null;
    }
    @Override
    protected void setRawResult(String value) {
    }
}