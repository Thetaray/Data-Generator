package com.tr.test.service;

import au.com.bytecode.opencsv.CSVWriter;
import com.tr.csvgenerator.ExetendDataService.ExtendCsvServiceServiceImpl;
import com.tr.csvgenerator.dto.CsvConfigDTO;
import com.tr.csvgenerator.dto.CsvExtendableDTO;
import com.tr.csvgenerator.service.CsvGeneratorService;
import com.tr.csvgenerator.service.CsvGeneratorServiceImpl;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by erez on 11/26/15.
 */
public class CsvGeneratorTest /*extends AbstractTest*/ {


    @Test
    public void setMoreColumns() throws Exception {
        String[] listOfColumns = {"Test,Test3,Test1"};
        int numberOfColumns = 500;
        String[] columnList = new String[numberOfColumns];
        for (int i = 0; i < listOfColumns.length; i++) {
            columnList[i] = listOfColumns[i];
        }
        for (int i = listOfColumns.length; i < numberOfColumns; i++) {
            columnList[i] = getIntToString(i);
        }
        for (String s : columnList) {
            System.out.print(s + " ,");
        }
    }

    private String getIntToString(int value){

        StringBuffer result = new StringBuffer();
        if(value == 0){
            result.append("Header");
        }
        while (--value >= 0)
        {
            result.append((char)('A' + value % 26 ));
            value /= 26;
        }
        return result.toString();
    }


    @Test
    public void csvTest() throws Exception {
        long time = new Date().getTime();
        int colNumber = 20;
        Random r = new Random();
        String[] stringsColumn;
        int i = 0;
        List<String[]> data = new ArrayList<>();
        List<String[]> biggerData = new ArrayList<>();
        int fileSize = 0;
        while(fileSize < 10) {
            if(fileSize > 0){
                data.clear();
//                System.out.println("clearing data");
            }
            while (i < 100000) {
                stringsColumn = new String[colNumber];
                for (int col = 0; col < colNumber; col++) {
                    if (i == 0 && fileSize == 0) {
                        stringsColumn[col] = Character.toString((char) (col + 65));
                    } else {
                        if (false && col == 0) {
                            stringsColumn[0] = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS").format(new Date());
                        } else {
                            int random = r.nextInt(1000);
                            stringsColumn[col] = Integer.toString(random);
                        }
                    }
                }

                data.add(stringsColumn);
                i++;
            }
            i=0;
            biggerData.addAll(data);
            fileSize++;
            System.out.println(fileSize);
        }

//        for (String[] strings : biggerData) {
//            for (String string : strings) {
//                System.out.print(string + " , ");
//            }
//            System.out.println();
//        }

        File csvFilename = new File("target/test.csv");
        CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFilename), ',');
        csvWriter.writeAll(biggerData);
        csvWriter.flush();
        csvWriter.close();
        long time2 = new Date().getTime();
        System.out.println("Took " + TimeUnit.SECONDS.convert(time2 - time, TimeUnit.MILLISECONDS) + " Seconds");
    }


    @Test
    public void createCsv() throws Exception {
        CsvGeneratorService generatorService = new CsvGeneratorServiceImpl();
        CsvConfigDTO configDTO = new CsvConfigDTO();
        configDTO.setNumberOfFilesToGen(15);
        configDTO.setRandomBatch(5000);
        configDTO.setFileSizeInMB(15);
        generatorService.validateCsvConfig(configDTO);
        generatorService.createCsv(configDTO);
    }

    @Test
    public void createMultiFolders() throws Exception{
        CsvGeneratorService generatorService = new CsvGeneratorServiceImpl();
        CsvConfigDTO configDTO = new CsvConfigDTO();
        configDTO.setNumberOfFoldersToGen(1);
        configDTO.setMultiFolderWriting(1);
        configDTO.setNumberOfFoldersToGen(4);
        configDTO.setNumberOfFilesToGen(15);
        configDTO.setRandomBatch(5000);
        configDTO.setFileSizeInMB(15);
        generatorService.validateCsvConfig(configDTO);
        generatorService.createCsv(configDTO);
    }

}
