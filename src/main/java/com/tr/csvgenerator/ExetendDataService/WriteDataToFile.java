package com.tr.csvgenerator.ExetendDataService;

import au.com.bytecode.opencsv.CSVWriter;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.concurrent.Callable;

/**
 * Created by roman on 27/02/16.
 */
public class WriteDataToFile implements Callable<Boolean> {


    private  CSVWriter m_Writer;
    private ObjectArrayList<String []> m_dataFromFile;
    public WriteDataToFile(CSVWriter csvWriter,ObjectArrayList<String []> dataFromFile){
        m_Writer = csvWriter;
        m_dataFromFile = dataFromFile.clone();
    }

    @Override
    public Boolean call() throws Exception {
        m_Writer.writeAll(m_dataFromFile);
        return  true;
    }
}
