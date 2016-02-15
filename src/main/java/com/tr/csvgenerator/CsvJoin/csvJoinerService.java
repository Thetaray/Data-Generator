package com.tr.csvgenerator.CsvJoin;

import au.com.bytecode.opencsv.CSVWriter;
import com.tr.csvgenerator.FeatureService.IdFeature;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

/**
 * Created by roman on 09/02/16.
 */
public class csvJoinerService {

    private CSVWriter csvWriter;
    private File csvFilename;



    private String getIntToString(int value) {
        StringBuffer result = new StringBuffer();
        if(value == 0){
            result.append("Header");
        }
        while (--value >= 0) {
            result.append((char) ('A' + value % 26));
            value /= 26;
        }
        return result.toString();
    }



    public void createCsv() throws IOException {
        Random random = new Random();
        int value =1;
        for(int j = 0 ; j < 2 ; j++){
            CSVWriter writer = createWriter();
            IdFeature idFeature = new IdFeature();
            for(int i = 0 ; i < 30000; i ++){
                String[] newRow = new String[150];
                if(i == 0){
                    newRow[0] = idFeature.getFeatureNameHeader();
                    for(int temp = 1 ;temp < newRow.length ;temp++){
                        newRow[temp] = getIntToString(value++);
                    }
                }
                else{
                     newRow[0] = idFeature.getValueForIndex();
                    for(int k = 1 ; k < newRow.length ; k++){
                        newRow[k] = Integer.toString(random.nextInt(1000));
                    }
                }
                writer.writeNext(newRow);
            }
            writer.close();
        }

    }

    private CSVWriter createWriter() throws IOException {
        Date date = new Date();
        Timestamp currentTimestamp = new Timestamp(date.getTime());
        csvFilename = new File("target/" + "JoinTest"+ "_" + formatTimeStap(currentTimestamp) + ".csv");
        csvFilename.setExecutable(true,false);
        csvFilename.setReadable(true, false);
        csvFilename.setWritable(true, false);
        return new CSVWriter(new FileWriter(csvFilename), CSVWriter.DEFAULT_SEPARATOR);
    }

    private String formatTimeStap(Timestamp timestamp){
        return timestamp.toString().replaceAll(":","-").replaceAll(" ","").replaceAll("[\\s.]", "");
    }

}
