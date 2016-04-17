package com.tr.csvgenerator.DataGenerator;

import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
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
        Consumer2 producer2 = new Consumer2();
        for (int i = 0 ; i < 2 ; i++){
            Future<Boolean> submit = threadpool.submit(consumer2);
            jobsIsDone.add(submit);
        }
        for(Future<Boolean> fut : jobsIsDone){
            try {
                //print the return value of Future, notice the output delay in console
                // because Future.get() waits for task to get completed
                System.out.println(new Date()+ "::"+fut.get()
                );
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        //shut down the executor service now
        threadpool.shutdown();
    }
        /*Thread producer = new Producer();
        Thread consumer = new Consumer();
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();*/




    public  class  Consumer2 implements Callable<Boolean>{

        @Override
        public Boolean call() throws Exception {
            return null;
        }
    }


    public class Consumer extends Thread {
        Consumer() {
            super("Consumer");
        }

        @Override
        public void run() {

            CSVWriter writer = null;
            //String FilePath = "/home/naor/Desktop/test1";
            File file = new File(dataToGen.getOutputFile());
            file.getParentFile().mkdirs();
            try {
                    //TODO: detriment the spliter
                    writer = new CSVWriter(new FileWriter(file), ',', CSVWriter.NO_QUOTE_CHARACTER);
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<Object> nextLine;
            while (!ProducerFinish || !buffer.isEmpty())
            {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized(buffer)
                {
                    Iterator it = buffer.iterator();
                    while (it.hasNext())
                    {
                        nextLine = (List<Object>) it.next();
                        //System.out.println(nextLine);
                        String[] arr = new String[nextLine.size()];
                        for(int i=0;i<arr.length;i++)
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
            }
        }
    }

    public class Producer extends Thread
    {
        int i = 0;
        Producer() {
            super("Producer");
        }

        @Override
        public void run()
        {
            try
            {
                boolean insert = false;
                while (dataToGen.hasNext())
                {
                    insert = false ;
                    i++;
                    while(insert!=true)
                    {
                        //TODO: clculate buffer_Size
                        if (buffer.size() < 1000) {
                            //buffer.add(new String(i + ""));
                            buffer.add(dataToGen.Next());
                            insert = true;
                        }
                        else
                            Thread.sleep(10);
                    }
                }
                ProducerFinish = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}