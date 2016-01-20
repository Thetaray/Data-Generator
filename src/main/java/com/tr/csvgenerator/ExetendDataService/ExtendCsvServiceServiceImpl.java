package com.tr.csvgenerator.ExetendDataService;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.tr.csvgenerator.dto.CsvExtendableDTO;
import it.unimi.dsi.fastutil.objects.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by roman on 18/01/16.
 */
@Service
public class ExtendCsvServiceServiceImpl implements ExtendCsvService {

    private CsvExtendableDTO extensionDto;
    private File csvFilename;
    private CSVReader reader;
    private CSVWriter writer;
    private  AnalyzeOfFieldInCsv analyzeOfFieldInCsv = new AnalyzeOfFieldInCsv();
    private String demo_File_Name;

    @Override
    public String validateInput(CsvExtendableDTO dto) {
        StringBuffer errorMessage = new StringBuffer();
        if (dto.getTotalNumberOfRowsInNewFile() < 2000 || dto.getTotalNumberOfRowsInNewFile() > 2000000) {
            errorMessage.append("Not a correct number of rows to add\n");
        }

        if (dto.getTotalNumberOfColumnsInNewFile() < 15 || dto.getTotalNumberOfColumnsInNewFile() > 5000) {
            errorMessage.append("Wrong number of columns");
        }
        if (dto.getTotalNumberOfColumnsInNewFile() > dto.getTotalNumberOfRowsInNewFile() / 10) {
            errorMessage.append("Wrong number of rows per columns");
        }
        if(dto.getPathToCsv().isEmpty()){
            errorMessage.append("No path To Csv file as example");
        }

        if(errorMessage.toString().isEmpty()){
            errorMessage.append("No Errors");
        }
        return errorMessage.toString();
    }

    @Override
    public boolean extendToCsvFile(CsvExtendableDTO dto) throws IOException, ExecutionException, InterruptedException {
        boolean finalResult = true;
        ObjectList<String[]> newFileCsv = null;
        int numberOfColumnsToAdd = 0;
        int numberOfRowsToAdd;
        this.extensionDto = dto;
        this.writer = createWriter();
        reader = new CSVReader(new FileReader(dto.getPathToCsv()));
        ObjectList<String[]> originalCsvFileData = new ObjectArrayList<>(reader.readAll());
        reader.close();
        numberOfColumnsToAdd = extensionDto.getTotalNumberOfColumnsInNewFile() - originalCsvFileData.get(0).length;
        if (numberOfColumnsToAdd > 0) {
            newFileCsv = addColumnsForFile(originalCsvFileData);
        }
        numberOfRowsToAdd = extensionDto.getTotalNumberOfRowsInNewFile() - originalCsvFileData.size();
        if(numberOfRowsToAdd > 0 ){
            if(newFileCsv == null){
                addRowsToFile(originalCsvFileData);
            }
            else{
                addRowsToFile(newFileCsv);
            }
            writer.flush();
            writer.close();
            reader = new CSVReader(new FileReader(demo_File_Name));
            ObjectList<String[]> DemolCsvFileData = new ObjectArrayList<>(reader.readAll());
            reader.close();
            extensionDto.oneFileCreated();
           finalResult =   CreateFiles(DemolCsvFileData);
        }
        return finalResult;
    }



    private  ObjectList<String[]> addColumnsForFile(ObjectList<String []> OriginalDataFromCsvlFile){
        ObjectList<String[]> newCsvFileObject = new ObjectArrayList<>();
        int numberOfOriginalColumns = OriginalDataFromCsvlFile.get(0).length;
        for (String[] row : OriginalDataFromCsvlFile) {
            String[] newRow = new String[extensionDto.getTotalNumberOfColumnsInNewFile()];
            for (int j = 0; j < extensionDto.getTotalNumberOfColumnsInNewFile(); j++) {
                if (j < numberOfOriginalColumns) {
                    newRow[j] = row[j];
                } else {
                    int index = j % numberOfOriginalColumns;
                    analyzeOfFieldInCsv.parseField(newRow,row[index],j);
                }
            }
            writer.writeNext(newRow);
            newCsvFileObject.add(newRow);
        }
        return  newCsvFileObject;
    }

    private  void addRowsToFile(ObjectList<String []> newCsvFileWithColumns){
        int newSize = newCsvFileWithColumns.size();
        int numberRowsToAdd = extensionDto.getTotalNumberOfRowsInNewFile() -  newSize;
        if(numberRowsToAdd > 0){
            do{
                for(String [] row : newCsvFileWithColumns){
                    int index = 0 ;
                    String [] newRow = new String[row.length];
                    for(String data : row){
                        analyzeOfFieldInCsv.parseField(newRow,data,index++);
                    }
                    writer.writeNext(newRow);
                }
                numberRowsToAdd = numberRowsToAdd - newSize;
            }while(numberRowsToAdd  > 0);
            if(numberRowsToAdd < 0){
                numberRowsToAdd = Math.abs(numberRowsToAdd);
                for(int i  = 0; i < numberRowsToAdd; i++){
                    String [] tempRow = newCsvFileWithColumns.get(i);
                        int index = 0 ;
                        String [] newRow = new String[tempRow.length];
                        for(String data : tempRow){
                            analyzeOfFieldInCsv.parseField(newRow,data,index++);;
                        }
                        writer.writeNext(newRow);
                }
            }
        }

    }

    private Boolean CreateFiles(ObjectList<String[]> list) throws IOException, ExecutionException, InterruptedException {
        boolean finalResult = true;
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(extensionDto.getNumberOfFiles());
        List<Future<Boolean>> resultOfCreation = new ArrayList<>();
        for(int i =0 ; i < extensionDto.getNumberOfFiles() ; i++){
            Thread.sleep(10);
            Future<Boolean> result = executor.submit(new CreateDuplicateParallelFiles(list,createWriter()));
            resultOfCreation.add(result);
        }
        for(Future<Boolean> future : resultOfCreation)
        {
            try
            {
                System.out.println("Future result is - " + " - " + future.get() + "; And Task done is " + future.isDone());
            }
            catch (InterruptedException | ExecutionException e)
            {
                e.printStackTrace();
            }
        }
        executor.shutdown();
        for(Future<Boolean> future : resultOfCreation){
            if(future.get() == false){
                finalResult = false;
                break;
            }
        }
        return finalResult;
    }

        /*if(extensionDto.getNumberOfFiles() % 2 == 1){
            Future<Boolean> result = executor.submit(new CreateDuplicateParallelFiles(list,createWriter()));
            resultOfCreation.add(result);
            try
            {
                extensionDto.oneFileCreated();
                System.out.println("Future result is - " + " - " + result.get() + "; And Task done is " + result.isDone());
            }
            catch (InterruptedException | ExecutionException e)
            {
                e.printStackTrace();
            }
        }*/
             /*   for(;  extensionDto.getNumberOfFiles() > resultOfCreation.size();){
                    for(int j = 0 ; j < 2 ; j++) {
                        Future<Boolean> result = executor.submit(new CreateDuplicateParallelFiles(list, createWriter()));
                        resultOfCreation.add(result);
                    }
                        for(Future<Boolean> future : resultOfCreation)
                        {
                            try
                            {
                                System.out.println("Future result is - " + " - " + future.get() + "; And Task done is " + future.isDone());
                            }
                            catch (InterruptedException | ExecutionException e)
                            {
                                e.printStackTrace();
                            }
                        }
                }*/


    private CSVWriter createWriter() throws IOException {
        Date date = new Date();
        Timestamp currentTimestamp = new Timestamp(date.getTime());
        checkDirectory(extensionDto.getOutputFolder());
        csvFilename = new File(extensionDto.getOutputFolder() + extensionDto.getFileName() + "_" + currentTimestamp + ".csv");
        demo_File_Name = csvFilename.getAbsolutePath();
        return new CSVWriter(new FileWriter(csvFilename), extensionDto.getSeparator().getSeparatorAsChar(),CSVWriter.NO_QUOTE_CHARACTER);
    }

    private void checkDirectory(String directory) {
        File dir = new File(directory);
        if (!dir.exists())
            dir.mkdirs();
    }

}
