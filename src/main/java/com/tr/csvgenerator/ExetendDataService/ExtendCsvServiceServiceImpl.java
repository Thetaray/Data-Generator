package com.tr.csvgenerator.ExetendDataService;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.tr.csvgenerator.FeatureService.FeatureService;
import com.tr.csvgenerator.dto.CsvExtendableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import scala.Char;

import java.io.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.ExecutionException;

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

    @Override
    public boolean extendToCsvFile(CsvExtendableDTO dto) throws IOException, ExecutionException, InterruptedException {
        isDemoFile = true;
        isFullDemoFile = false;
        boolean finalResult = true;
        int numberOfColumnsToAdd = 1;
        int numberOfRowsToAdd   = 1;
        this.extensionDto = dto;
        if(!extensionDto.getSeperatorToRead().isEmpty() && !extensionDto.getSeperatorToRead().contains(" ")){
            separatorToRead = extensionDto.getSeperatorToRead().charAt(0);
        }
        if(!extensionDto.getSeperatorToWrite().isEmpty() && !extensionDto.getSeperatorToWrite().contains(" ")) {
            separatorToOut  =  extensionDto.getSeperatorToWrite().charAt(0);
        }
        this.m_writer = createWriter();
        if(extensionDto.getTimeStampFeature() == 1){
            AddColumnsWithTimeStampFeautre();
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
        m_reader = createReader(extensionDto.getPathToCsv());
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
            m_reader = createReader(demo_File_Name_First);
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
                    else {
                        extensionDto.setTotalNumberOfRowsInNewFile(extensionDto.getTotalNumberOfRowsInNewFile()+1);
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
                        m_reader = createReader(demo_File_Name_First);
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

    private void addRowsWithTimeStampFeature() throws IOException {
        boolean firstTimeToCreateColumns = true;
        try {
            m_writer = createWriter();
            m_reader = createReader(demo_File_Name_First);
            for (int i = 0; i <= extensionDto.getTotalNumberOfRowsInNewFile(); i++) {
                String[] rowFromFile = m_reader.readNext();
                int index = 0;
                if (extensionDto.getHasHeader() == 1 && i % numberOfOriginalsRowInFile == 0) {
                    if (firstTimeToCreateColumns) {
                        if (rowFromFile != null) {
                            String[] newRow = new String[rowFromFile.length];
                            for (String data : rowFromFile) {
                                analyzeOfFieldInCsv.parseField(newRow, data.trim(), index++);
                            }
                            m_writer.writeNext(newRow);
                        }
                        firstTimeToCreateColumns = false;
                    }
                    else{
                        extensionDto.setTotalNumberOfRowsInNewFile(extensionDto.getTotalNumberOfRowsInNewFile() + 1);
                    }
                } else {
                    if (rowFromFile != null) {
                        String[] newRow = new String[rowFromFile.length];
                        newRow[0] = featureService.getValueForIndex();
                        for (int k = 1; k < rowFromFile.length; k++) {
                            analyzeOfFieldInCsv.parseField(newRow, rowFromFile[k], k);
                        }
                        m_writer.writeNext(newRow);
                    } else {
                        m_reader.close();
                        m_reader = createReader(demo_File_Name_First);
                    }
                }
            }
        } finally {
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


    private void AddColumnsWithTimeStampFeautre() throws IOException {
        int numberOfOriginalColumnsInFile  = 0;
        m_reader = createReader(extensionDto.getPathToCsv());
        boolean firstRow = true;
        String[] rowFromFile = m_reader.readNext();
        int newNumberOfColumnsWithTimeStampFeautre = extensionDto.getTotalNumberOfColumnsInNewFile() + 1;
        while(rowFromFile != null){
            numberOfOriginalColumnsInFile = rowFromFile.length; //Geting number of columns from sample data
            String[] newRow   = new String[extensionDto.getTotalNumberOfColumnsInNewFile() + 1]; //Adding rows for new file with time stamp feature
            if(firstRow == true){
                newRow[0] = featureService.getFeatureNameHeader();//Add feature name to header
            }
            else{
                newRow[0] = featureService.getValueForIndex();//Add value from feature
            }
            for(int i = 0, j=1 ;i < extensionDto.getTotalNumberOfColumnsInNewFile() ; i++,j++){
                if(i < numberOfOriginalColumnsInFile){
                    newRow[j] = rowFromFile[i].trim();//Add originals values to new csv file
                }else {
                    int index = i % numberOfOriginalColumnsInFile;//Finished columns in original file
                    //Adding new values to header
                    if(firstRow && extensionDto.getHasHeader() == 1){
                        analyzeOfFieldInCsv.parseField(newRow,rowFromFile[index].trim() + getIntToString(ColumnHeaderName++),j);//feature name parsing
                    }
                    else{
                        analyzeOfFieldInCsv.parseField(newRow,rowFromFile[index].trim(),j);//value from csv file parsing
                    }
                }

            }
            m_writer.writeNext(newRow);
            firstRow = false;
            if(!firstRow && !(extensionDto.getHasHeader() == 1)){
                numberOfOriginalsRowInFile++;
            }
            //counting original number of rows in sample file
            rowFromFile = m_reader.readNext();
        }
        m_reader.close();
        m_writer.close();
    }
}
