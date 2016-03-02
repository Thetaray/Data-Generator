package com.tr.csvgenerator.dto;

/**
 * Created by naor on 02/03/16.
 */
public class GenerateDataForSupervisedDTO {

    protected Double CellMaxBoundary = new Double(100000000);
    int Small_Group_Pos_Size = 25000;
    int Small_Group_Neg_Size = 25000;


    int Rank = 100;
    int Train_Pos_Size = 600000;
    int Train_Neg_Size = 400000;
    //static int Test_Pos_Size = 700000;
    //static int Test_Neg_Size = 300000;
    String TrainDataPath = "/home/naor/Desktop/TrainingSet.csv";
    /* Input to the program */
    private int PK = 0;
    //static String TestDataPath = "/home/naor/Desktop/TestingSet.csv";

    public int getTrain_Neg_Size() {
        return Train_Neg_Size;
    }

    public void setTrain_Neg_Size(int train_Neg_Size) {
        Train_Neg_Size = train_Neg_Size;
    }

    public int getPK() {
        return PK;
    }

    public void setPK(int PK) {
        PK = PK;
    }

    public int getSmall_Group_Pos_Size() {
        return Small_Group_Pos_Size;
    }

    public void setSmall_Group_Pos_Size(int small_Group_Pos_Size) {
        Small_Group_Pos_Size = small_Group_Pos_Size;
    }

    public int getSmall_Group_Neg_Size() {
        return Small_Group_Neg_Size;
    }

    public void setSmall_Group_Neg_Size(int small_Group_Neg_Size) {
        Small_Group_Neg_Size = small_Group_Neg_Size;
    }

    public int getRank() {
        return Rank;
    }

    public void setRank(int rank) {
        Rank = rank;
    }

    public Double getCellMaxBoundary() {
        return CellMaxBoundary;
    }

    public void setCellMaxBoundary(Double cellMaxBoundary) {
        CellMaxBoundary = cellMaxBoundary;
    }

    public int getTrain_Pos_Size() {
        return Train_Pos_Size;
    }

    public void setTrain_Pos_Size(int train_Pos_Size) {
        Train_Pos_Size = train_Pos_Size;
    }

    public String getTrainDataPath() {
        return TrainDataPath;
    }

    public void setTrainDataPath(String trainDataPath) {
        TrainDataPath = trainDataPath;
    }
}
