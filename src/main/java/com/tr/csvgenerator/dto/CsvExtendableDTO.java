package com.tr.csvgenerator.dto;

import com.tr.csvgenerator.model.Separator;

/**
 * Created by roman on 18/01/16.
 */
public class CsvExtendableDTO {

    private int TimeStampFeature = 0 ;

    private int hasHeader = 1;

    private int numberOfFiles = 1;

    private Separator separator = Separator.DEFAULT;

    private String fileName = "Test";

    private String outputFolder = "target/";

    private String pathToCsv;

    private String validationError = null;

    private int totalNumberOfColumnsInNewFile = 250;

    private int totalNumberOfRowsInNewFile = 5000 ;

    public void setNumberOfFiles(int numberOfFiles) {
        this.numberOfFiles = numberOfFiles;
    }

    public int getTimeStampFeature() {
        return TimeStampFeature;
    }

    public void setTimeStampFeature(int timeStampFeature) {
        TimeStampFeature = timeStampFeature;
    }


    public int getNumberOfFiles() {
        return numberOfFiles;
    }

    public Separator getSeparator() {
        return separator;
    }

    public void setSeparator(Separator separator) {
        this.separator = separator;
    }

    public int getHasHeader() {
        return hasHeader;
    }

    public void setHasHeader(int hasHeader) {
        this.hasHeader = hasHeader;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    public void setOutputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
    }

    public String getPathToCsv() {
        return pathToCsv;
    }

    public void setPathToCsv(String pathToCsv) {
        this.pathToCsv = pathToCsv;
    }

    public String getValidationError() {
        return validationError;
    }

    public void setValidationError(String validationError) {
        this.validationError = validationError;
    }

    public int getTotalNumberOfColumnsInNewFile() {
        return totalNumberOfColumnsInNewFile;
    }

    public void setTotalNumberOfColumnsInNewFile(int totalNumberOfColumnsInNewFile) {
        this.totalNumberOfColumnsInNewFile = totalNumberOfColumnsInNewFile;
    }

    public int getTotalNumberOfRowsInNewFile() {
        return totalNumberOfRowsInNewFile;
    }

    public void setTotalNumberOfRowsInNewFile(int totalNumberOfRowsInNewFile) {
        this.totalNumberOfRowsInNewFile = totalNumberOfRowsInNewFile;
    }


    public void oneFileCreated(){
        this.numberOfFiles --;
    }
}
