package com.tr.csvgenerator.dto;

import com.tr.csvgenerator.model.Separator;

/**
 * Created by erez on 11/25/15.
 */
public class CsvConfigDTO {

    private String validationError = null;
    private int numberOfFilesToGen = 0;
    private int fileSizeInMB = 0;
    private int numberOfColumns = 20;
    private int minValue = 0;
    private int maxValue = 1000;
    private Separator separator = Separator.DEFAULT;
    private int randomBatch = 10000;

    private String outputFolder = "target/";
    private String outputfileName = "Test";

    private String[] columnsList = {};

    public void oneFileCreated(){
        numberOfFilesToGen--;
    }

    public String getValidationError() {
        return validationError;
    }

    public void setValidationError(String validationError) {
        this.validationError = validationError;
    }

    public int getNumberOfFilesToGen() {
        return numberOfFilesToGen;
    }

    public void setNumberOfFilesToGen(int numberOfFilesToGen) {
        this.numberOfFilesToGen = numberOfFilesToGen;
    }

    public int getFileSizeInMB() {
        return fileSizeInMB;
    }

    public void setFileSizeInMB(int fileSizeInMB) {
        this.fileSizeInMB = fileSizeInMB;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    public void setOutputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
    }

    public String getOutputfileName() {
        return outputfileName;
    }

    public void setOutputfileName(String outputfileName) {
        this.outputfileName = outputfileName;
    }

    public String[] getColumnsList() {
        return columnsList;
    }

    public void setColumnsList(String[] columnsList) {
        this.columnsList = columnsList;
    }

    public Separator getSeparator() {
        return separator;
    }

    public void setSeparator(Separator separator) {
        this.separator = separator;
    }

    public int getRandomBatch() {
        return randomBatch;
    }

    public void setRandomBatch(int randomBatch) {
        this.randomBatch = randomBatch;
    }
}
