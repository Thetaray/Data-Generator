package com.tr.csvgenerator.ExetendDataService;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by roman on 19/01/16.
 */
public class CreateDuplicateParallelFiles implements Callable<Boolean> {

    private CSVWriter writer;
    private AnalyzeOfFieldInCsv parseFieldsOfCsv;
    private CSVReader m_Reader;
    private Lock readLock;

    public CreateDuplicateParallelFiles(CSVReader i_reader, CSVWriter i_writer) throws FileNotFoundException {
       m_Reader = i_reader;
        this.writer = i_writer;
        parseFieldsOfCsv = new AnalyzeOfFieldInCsv();
    }



    @Override
    public Boolean call() throws Exception {
        boolean finish = true;
        String[] rowFromFile = null;
        do{
            rowFromFile = m_Reader.readNext();
            if(rowFromFile != null){
                String[] newRow = new String[rowFromFile.length];
                int indexOfForRow = 0;
                for(String valueFromValue : rowFromFile){
                    parseFieldsOfCsv.parseField(newRow,valueFromValue,indexOfForRow);
                    indexOfForRow ++;
                }
                writer.writeNext(newRow);
            }
        }while (rowFromFile != null);
        writer.close();
        return finish;
    }
}
