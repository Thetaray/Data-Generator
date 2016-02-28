package com.tr.csvgenerator.ExetendDataService;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.tr.csvgenerator.FeatureService.FeatureService;
import com.tr.csvgenerator.dto.CsvExtendableDTO;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.Timestamp;
import java.util.Date;
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


    private  char charSeperator = '*';

    @Autowired
    @Qualifier("TimeStamp")
    protected FeatureService featureService;
    private Character separatorToRead = null;
    private Character separatorToOut = null;
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
    private ThreadPoolExecutor executor;
    @Override
    public String validateInput(CsvExtendableDTO dto) {
        StringBuffer errorMessage = new StringBuffer();
        if (dto.getTotalNumberOfRowsInNewFile() < 1  || dto.getTotalNumberOfRowsInNewFile() > 20000000) {
            errorMessage.append("Not a correct number of rows to add\n");
        }

        if(dto.getSeperatorToRead() == null || dto.getSeperatorToRead().length() > 1){
            errorMessage.append("Wrong  input for separator read \n");
        }
        if(dto.getSeperatorToWrite() == null || dto.getSeperatorToWrite().length() > 1){
            errorMessage.append("Wrong  input for separator to write \n");
        }

        if(dto.getTimeStampFeature() < 0 || dto.getTimeStampFeature() > 1){
            errorMessage.append("Wrong dateStampFormat value");
        }

        if (dto.getTotalNumberOfColumnsInNewFile() < 1 || dto.getTotalNumberOfColumnsInNewFile() > 5000) {
            errorMessage.append("Wrong number of columns");
        }
        if (dto.getTotalNumberOfColumnsInNewFile() > dto.getTotalNumberOfRowsInNewFile()) {
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

    private void setSeparatorAfterValidationOfSeparator(){
        if(!extensionDto.getSeperatorToRead().isEmpty() && !extensionDto.getSeperatorToRead().contains(" ")){
            separatorToRead = extensionDto.getSeperatorToRead().charAt(0);
        }
        if(!extensionDto.getSeperatorToWrite().isEmpty() && !extensionDto.getSeperatorToWrite().contains(" ")) {
            separatorToOut  =  extensionDto.getSeperatorToWrite().charAt(0);
        }
    }

    @Override
    public boolean extendToCsvFile(CsvExtendableDTO dto) throws IOException, ExecutionException, InterruptedException {
        isDemoFile = true;
        isFullDemoFile = false;
        boolean finalResult = true;
        int numberOfColumnsToAdd = 1;
        int numberOfRowsToAdd   = 1;
        this.extensionDto = dto;
        setSeparatorAfterValidationOfSeparator();
        this.m_writer = createWriter();
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        readByLineInFromOriginalFileAndCreateDemoFileWithNewNumberOfColumns();
        createFullFileByDuplicateDemoFileAndAddingRows();
        extensionDto.oneFileCreated();
        if(extensionDto.getNumberOfFiles() >= 1){
            createFullDuplicateFile();
        }
        /*if(extensionDto.getTimeStampFeature() == 1){
            readByLineInFromOriginalFileAndCreateDemoFileWithNewNumberOfColumns();
            if(numberOfRowsToAdd > 0 ){
                addRowsWithTimeStampFeature();
                extensionDto.oneFileCreated();
                if(extensionDto.getNumberOfFiles() >= 1){
                    createFullDuplicateFile();
                }
            }
        }
        else {
            readByLineAndAddColumns();
            if(numberOfRowsToAdd > 0 ){
                readByLineAndAddRows();
                extensionDto.oneFileCreated();
                if(extensionDto.getNumberOfFiles() >= 1){
                    createFullDuplicateFile();
                }
               }
        }*/
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

    private int setNumberOfTotalColumnsInNewFile(){
        int totalNumberOfColumnsInNewFile = 0;
        if(extensionDto.getTimeStampFeature() == 1){
            totalNumberOfColumnsInNewFile = extensionDto.getTotalNumberOfColumnsInNewFile() + 1;
        }
        else {
            totalNumberOfColumnsInNewFile = extensionDto.getTotalNumberOfColumnsInNewFile();
        }
        return  totalNumberOfColumnsInNewFile;
    }

    private void readByLineInFromOriginalFileAndCreateDemoFileWithNewNumberOfColumns() throws IOException {
        Future<Boolean> submit = null;
        int indexToStartBecauseOfFeature = 0;
        int numberOfOriginalColumnsInFile = 0;
        String[] rowFromFile = null;
        int totalNumberOfColumnsInNewFile  =  0;
        boolean firstRow = true;
        totalNumberOfColumnsInNewFile = setNumberOfTotalColumnsInNewFile();
        ObjectArrayList<String []> dataFromFile = new ObjectArrayList<>();
        try{
            m_reader = createReader(extensionDto.getPathToCsv());
            rowFromFile = m_reader.readNext();
            while(rowFromFile != null){
                indexToStartBecauseOfFeature = 0;
                String [] newRow = new String[totalNumberOfColumnsInNewFile];
                if(firstRow){
                    numberOfOriginalColumnsInFile = rowFromFile.length;
                    if(extensionDto.getTimeStampFeature() == 1){
                        indexToStartBecauseOfFeature++;
                        newRow[0] = featureService.getFeatureNameHeader();
                    }
                }
                else {
                    if(extensionDto.getTimeStampFeature() == 1){
                        indexToStartBecauseOfFeature++;
                        newRow[0] = featureService.getValueForIndex();
                    }
                }
                for(int  i = 0; indexToStartBecauseOfFeature < totalNumberOfColumnsInNewFile ;i++,indexToStartBecauseOfFeature++){
                    if(i < rowFromFile.length){
                        newRow[indexToStartBecauseOfFeature] = rowFromFile[i].trim();//Add originals values to new csv file
                    }else{
                        int index = i % numberOfOriginalColumnsInFile;
                        if(firstRow && extensionDto.getHasHeader() == 1){
                            analyzeOfFieldInCsv.parseField(newRow,rowFromFile[index].trim() + getIntToString(ColumnHeaderName++),indexToStartBecauseOfFeature);//feature name parsing
                        }
                        else{
                            analyzeOfFieldInCsv.parseField(newRow,rowFromFile[index].trim(),indexToStartBecauseOfFeature);//value from csv file parsing
                        }
                    }
                }
                dataFromFile.push(newRow);
                firstRow = false;
                if(dataFromFile.size() > 20000) {
                    if (submit == null) {
                        WriteDataToFile writeDataToFile = new WriteDataToFile(m_writer, dataFromFile);
                        submit = executor.submit(writeDataToFile);
                        dataFromFile.clear();
                    } else {
                        if (submit.isDone()) {
                            WriteDataToFile writeDataToFile = new WriteDataToFile(m_writer, dataFromFile);
                            submit = executor.submit(writeDataToFile);
                            dataFromFile.clear();
                        }
                    }
                }
                rowFromFile = m_reader.readNext();
                numberOfOriginalsRowInFile ++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            m_reader.close();
            if(dataFromFile.size() > 0){
                m_writer.writeAll(dataFromFile);
            }
            m_writer.close();
            extensionDto.setTotalNumberOfColumnsInNewFile(totalNumberOfColumnsInNewFile);
        }
    }

    private void createFullFileByDuplicateDemoFileAndAddingRows() throws IOException {
        ObjectArrayList<String []> dataFromFile = new ObjectArrayList<>();
        Future<Boolean> submit = null;
        int indexToStratBecauseOfFeature = 0;
        boolean firstTimeToCreateColumns = true;
        try{
            m_writer = createWriter();
            m_reader = createReader(demo_File_Name_First);
            int finalNumberOfRowsToWriteBecauseHeader = extensionDto.getTotalNumberOfRowsInNewFile();
            for (int i = 0; i <= finalNumberOfRowsToWriteBecauseHeader ; i++) {
                String[] rowFromFile = m_reader.readNext();
                int index = 0;
                if (extensionDto.getHasHeader() == 1 && i % numberOfOriginalsRowInFile == 0) {
                    if (firstTimeToCreateColumns) {
                        if (rowFromFile != null) {
                            String[] newRow = new String[rowFromFile.length];
                            for (String data : rowFromFile) {
                                analyzeOfFieldInCsv.parseField(newRow, data.trim(), index++);
                            }
                            dataFromFile.push(newRow);
                        }
                        firstTimeToCreateColumns = false;
                    }else{
                         finalNumberOfRowsToWriteBecauseHeader++;
                    }
                }else{
                    if(rowFromFile != null){
                        String[] newRow = new String[rowFromFile.length];
                            if(extensionDto.getTimeStampFeature() == 1){
                                newRow[0] = featureService.getValueForIndex();
                                indexToStratBecauseOfFeature  = 1;
                            }else{
                                indexToStratBecauseOfFeature = 0;
                            }
                        for (; indexToStratBecauseOfFeature < rowFromFile.length; indexToStratBecauseOfFeature++) {
                            analyzeOfFieldInCsv.parseField(newRow, rowFromFile[indexToStratBecauseOfFeature], indexToStratBecauseOfFeature);
                        }
                        dataFromFile.push(newRow);
                    }else {
                        m_reader.close();
                        m_reader = createReader(demo_File_Name_First);
                    }
                }
               if(dataFromFile.size() > 20000){
                   if(submit == null){
                       WriteDataToFile writeDataToFile = new WriteDataToFile(m_writer,dataFromFile);
                       submit = executor.submit(writeDataToFile);
                       dataFromFile.clear();
                   }else{
                       if(submit.isDone()){
                             WriteDataToFile writeDataToFile = new WriteDataToFile(m_writer,dataFromFile);
                               submit = executor.submit(writeDataToFile);
                               dataFromFile.clear();
                       }
                   }

               }
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(dataFromFile.size() > 0){
                m_writer.writeAll(dataFromFile);
            }
            m_reader.close();
            m_writer.close();
            File file = new File(demo_File_Name_First);
            if (file.delete()) {
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
                reader = createReader(Full_Demo_File);
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

    private CSVWriter createWriter() throws IOException {
        Date date = new Date();
        CSVWriter writer;
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
        if(separatorToOut != null){
              writer = new CSVWriter(new FileWriter(csvFilename), separatorToOut,CSVWriter.NO_QUOTE_CHARACTER);
        }
        else{
               writer = new CSVWriter(new FileWriter(csvFilename),extensionDto.getSeparator().getSeparatorAsChar(),CSVWriter.NO_QUOTE_CHARACTER);
        }
        return writer;
    }

    private CSVReader createReader(String pathToFile) throws FileNotFoundException {
        CSVReader reader;
        if(separatorToRead != null){
            reader = new CSVReader(new FileReader(pathToFile), separatorToRead);
        }
        else{
            reader = new CSVReader(new FileReader(pathToFile));
        }
        return  reader;
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
