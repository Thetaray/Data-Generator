package com.tr.csvgenerator.RandomDataGenerator;

import RandomDataGeneratorObject.Data;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.*;

/**
 * Created by IntelliJ IDEA.
 * User: Naor Ben David
 * Mail: naor.bendaivd@thetaray.com
 */
public class ProducerConsumer {

    private Vector<List<Object>> buffer = new Vector<>();
    private Data dataToGen;
    private boolean ProducerFinish = false;
    private ExecutorService threadpool = Executors.newFixedThreadPool(2);
    private ArrayList<Boolean> jobIsDone = new ArrayList<>();
    private ArrayList<Future<Boolean>> jobsIsDone = new ArrayList<>();

    public ProducerConsumer(Data dataToGen) throws InterruptedException {
        this.dataToGen = dataToGen;
        Consumer2 consumer2 = new Consumer2();
        Producer2 producer2 = new Producer2();
        Future<Boolean> call1 = threadpool.submit(consumer2);
        Future<Boolean> call2 = threadpool.submit(producer2);
        jobsIsDone.add(call1);
        jobsIsDone.add(call2);

        for (Future<Boolean> fut : jobsIsDone) {
            try {
                //print the return value of Future, notice the output delay in console
                // because Future.get() waits for task to get completed
                fut.get();
                //System.out.println(new Date()+ "::"+fut.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        //shut down the executor service now
        threadpool.shutdown();
    }


    private class Consumer2 implements Callable {

        public Consumer2() {
        }

        @Override
        public Boolean call() throws IOException {

            //String FilePath = "/home/naor/Desktop/test1";
            File file = new File(dataToGen.getOutputFile());
            //TODO: detriment the spliter
            file.getParentFile().mkdirs();
            CSVWriter writer = new CSVWriter(new FileWriter(file), ',', CSVWriter.NO_QUOTE_CHARACTER);
            List<Object> nextLine;
            while (!ProducerFinish || !buffer.isEmpty()) {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (buffer) {
                    Iterator it = buffer.iterator();
                    while (it.hasNext()) {
                        nextLine = (List<Object>) it.next();
                        //System.out.println(nextLine);
                        String[] arr = new String[nextLine.size()];
                        for (int i = 0; i < arr.length; i++)
                            arr[i] = nextLine.get(i).toString();

                        writer.writeNext((String[]) arr);
                        it.remove();
                    }
                }
            }
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        private long factorial(int number) throws InterruptedException {
            if (number < 0) {
                throw new IllegalArgumentException("Number must be greater than zero");
            }
            long result = 1;

            while (number > 0) {

                Thread.sleep(1); // adding delay for example
                result = result * number;
                //System.out.println(result+"*"+number);
                number--;
            }
            return result;
        }
    }

    private class Producer2 implements Callable {

        int i = 0;

        public Producer2() {
        }

        @Override
        public Boolean call() {
            try {
                boolean insert = false;
                while (dataToGen.hasNext()) {
                    insert = false;
                    i++;
                    while (insert != true) {
                        //TODO: calculate buffer_Size, write rate
                        if (buffer.size() < 1000) {
                            //buffer.add(new String(i + ""));
                            buffer.add(dataToGen.Next());
                            insert = true;
                        } else
                            Thread.sleep(10);
                    }
                }
                ProducerFinish = true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

    }
}