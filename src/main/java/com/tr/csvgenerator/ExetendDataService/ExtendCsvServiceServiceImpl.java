package com.tr.csvgenerator.ExetendDataService;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.tr.csvgenerator.dto.CsvExtendableDTO;
import it.unimi.dsi.fastutil.objects.*;
import org.springframework.context.annotation.Scope;
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
@Scope("prototype")
public class ExtendCsvServiceServiceImpl implements ExtendCsvService {

    private int ColumnHeaderName = 1;
    private CsvExtendableDTO extensionDto;
    private File csvFilename;
    private CSVReader m_reader;
    private CSVWriter m_writer;
    private  AnalyzeOfFieldInCsv analyzeOfFieldInCsv = new AnalyzeOfFieldInCsv();
    private String demo_File_Name_First;
    private String Full_Demo_File;
    private  int numberOfOriginalsRowInFile = 0;
    private  boolean isDemoFile = true;
    private boolean isFullDemoFile = false;

    @Override
    public String validateInput(CsvExtendableDTO dto) {
        StringBuffer errorMessage = new StringBuffer();
        if (dto.getTotalNumberOfRowsInNewFile() < 1  || dto.getTotalNumberOfRowsInNewFile() > 2000000) {
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
        if(dto.getNumberOfFiles() < 1 ||dto.getNumberOfFiles() > 30) {
            errorMessage.append("No right number of files to gen : Min 1 , Max 30");
        }
        if(errorMessage.toString().isEmpty()){
            errorMessage.append("No Errors");
        }
        return errorMessage.toString();
    }

    @Override
    public boolean extendToCsvFile(CsvExtendableDTO dto) throws IOException, ExecutionException, InterruptedException {
        boolean finalResult = true;
        int numberOfColumnsToAdd = 1;
        int numberOfRowsToAdd   = 1;
        this.extensionDto = dto;
        this.m_writer = createWriter();
        if (numberOfColumnsToAdd > 0) {
            readByLineAndAddColumns();
        }
        if(numberOfRowsToAdd > 0 ){
            readByLineAndAddRows();
            extensionDto.oneFileCreated();
            if(extensionDto.getNumberOfFiles() >= 1){
                createFullDuplicateFile();
            }
        }
        return finalResult;
    }

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


    private void readByLineAndAddColumns() throws IOException {
        m_reader = new CSVReader(new FileReader(extensionDto.getPathToCsv()));
        boolean firstRow = true;
        String[] rowFromFile = m_reader.readNext();
        int numberOfOriginalColumns = 0;
        while (rowFromFile != null){
            numberOfOriginalColumns = rowFromFile.length;
            String[] newRow   = new String[extensionDto.getTotalNumberOfColumnsInNewFile()];
            for (int j = 0; j < extensionDto.getTotalNumberOfColumnsInNewFile(); j++) {
                if (j < rowFromFile.length) {
                    newRow[j] = rowFromFile[j].trim();
                } else {
                    int index = j % numberOfOriginalColumns;
                    //Adding new values to header
                    if(firstRow && extensionDto.getHasHeader() == 1){
                        analyzeOfFieldInCsv.parseField(newRow,rowFromFile[index].trim() + getIntToString(ColumnHeaderName++),j);
                    }
                    else{
                        analyzeOfFieldInCsv.parseField(newRow,rowFromFile[index].trim(),j);
                    }
                }
            }
            m_writer.writeNext(newRow);
            firstRow = false;
            numberOfOriginalsRowInFile++;
            rowFromFile = m_reader.readNext();
        }
        m_reader.close();
        m_writer.close();
    }



    private void  readByLineAndAddRows() throws IOException {
        boolean firstTimeToCreateColumns = true;
        try{
            m_writer = createWriter();
            m_reader = new CSVReader((new FileReader(demo_File_Name_First)));
            for(int i = 0 ;i < extensionDto.getTotalNumberOfRowsInNewFile(); i++){
                String[] rowFromFile = m_reader.readNext();
                int index = 0;
                if(extensionDto.getHasHeader() == 1  &&  i % numberOfOriginalsRowInFile == 0 ){
                    if(firstTimeToCreateColumns){
                        if(rowFromFile != null){
                            String[] newRow = new String[rowFromFile.length];
                            for(String data : rowFromFile){
                                analyzeOfFieldInCsv.parseField(newRow,data.trim(),index++);
                            }
                            m_writer.writeNext(newRow);
                        }
                        firstTimeToCreateColumns = false;
                    }
                }
                else {
                    if (rowFromFile != null) {
                        String[] newRow = new String[rowFromFile.length];
                        for (String data : rowFromFile) {
                            analyzeOfFieldInCsv.parseField(newRow, data.trim(), index++);
                        }
                        m_writer.writeNext(newRow);
                    } else {
                        m_reader.close();
                        m_reader = new CSVReader(new FileReader(demo_File_Name_First));
                    }
                }
            }
        }finally {
            m_reader.close();
            m_writer.close();
            File file = new File(demo_File_Name_First);
            if(file.delete()){
                System.out.println("OK");
            }
        }
    }

    private  void createFullDuplicateFile() throws IOException {
        boolean header = true;
        CSVReader reader = null;
        CSVWriter writer = null;
        try {
            for (int i = 0; i < extensionDto.getNumberOfFiles(); i++) {
                reader = new CSVReader(new FileReader(Full_Demo_File));
                writer = createWriter();
                String[] rowFromFile = null;
                do {
                    rowFromFile = reader.readNext();
                    if (rowFromFile != null) {
                            String[] newRow = new String[rowFromFile.length];
                            int indexOfForRow = 0;
                            for (String valueFromValue : rowFromFile) {
                                analyzeOfFieldInCsv.parseField(newRow, valueFromValue.trim(), indexOfForRow);
                                indexOfForRow++;
                            }
                            writer.writeNext(newRow);
                    }
                } while (rowFromFile != null);

            }
           }finally{
                writer.close();
                reader.close();
            }
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
                    m_writer.writeNext(newRow);
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
                    m_writer.writeNext(newRow);
                }
            }
        }

    }

    private  ObjectList<String[]> addColumnsForFile(ObjectList<String []> OriginalDataFromCsvlFile){
        boolean firstRowOfHeader = true;
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
            m_writer.writeNext(newRow);
            newCsvFileObject.add(newRow);
        }
        return  newCsvFileObject;
    }

    private Boolean CreateFiles() throws IOException, ExecutionException, InterruptedException {
        boolean finalResult = true;
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(extensionDto.getNumberOfFiles());
        List<Future<Boolean>> resultOfCreation = new ArrayList<>();
        CSVReader Reader = new CSVReader(new FileReader(Full_Demo_File));
        for(int i =0 ; i < extensionDto.getNumberOfFiles() ; i++){
            Thread.sleep(10);
            Future<Boolean> result = executor.submit(new CreateDuplicateParallelFiles(Reader,createWriter()));
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
        m_reader.close();
        for(Future<Boolean> future : resultOfCreation){
            if(future.get() == false){
                finalResult = false;
                break;
            }
        }
        return finalResult;
    }

    private CSVWriter createWriter() throws IOException {
        Date date = new Date();
        Timestamp currentTimestamp = new Timestamp(date.getTime());
        checkDirectory(extensionDto.getOutputFolder());
        csvFilename = new File(extensionDto.getOutputFolder() + extensionDto.getFileName() + "_" + formatTimeStap(currentTimestamp) + ".csv");
        csvFilename.setExecutable(true,false);
        csvFilename.setReadable(true, false);
        csvFilename.setWritable(true, false);
        if(isFullDemoFile){
            Full_Demo_File = csvFilename.getAbsolutePath();
            isFullDemoFile = false;
        }
        if(isDemoFile){
            demo_File_Name_First = csvFilename.getAbsolutePath();
            isDemoFile = false;
            isFullDemoFile = true;
        }
        return new CSVWriter(new FileWriter(csvFilename), extensionDto.getSeparator().getSeparatorAsChar(),CSVWriter.NO_QUOTE_CHARACTER);
    }

    private void checkDirectory(String directory) {
        File dir = new File(directory);
        if (!dir.exists())
            dir.mkdirs();
    }


    private String formatTimeStap(Timestamp timestamp){
        return timestamp.toString().replaceAll(":","-").replaceAll(" ","").replaceAll("[\\s.]", "");
    }

}
