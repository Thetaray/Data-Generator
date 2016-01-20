package com.tr.csvgenerator.ExetendDataService;

import au.com.bytecode.opencsv.CSVWriter;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.concurrent.Callable;

/**
 * Created by roman on 19/01/16.
 */
public class CreateDuplicateParallelFiles implements Callable<Boolean> {

    private CSVWriter writer;
    private ObjectList<String[]> dataDemo;
    private AnalyzeOfFieldInCsv parseFieldsOfCsv;

    public CreateDuplicateParallelFiles(ObjectList<String[]> list, CSVWriter i_writer){
        this.dataDemo = list;
        this.writer = i_writer;
        parseFieldsOfCsv = new AnalyzeOfFieldInCsv();
    }



    @Override
    public Boolean call() throws Exception {
        boolean finish = true;
        for(String[] row : dataDemo){
            String [] newRow = new String[row.length];
            int indexOfForRow = 0;
            for(String charFromRow : row) {
                parseFieldsOfCsv.parseField(newRow,charFromRow,indexOfForRow);
                indexOfForRow ++;
            }
            writer.writeNext(newRow);
        }
        writer.flush();
        writer.close();
        return finish;
    }
}
