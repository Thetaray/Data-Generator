package com.tr.csvgenerator.dto;


/**
 * Created by Naor Ben David on 02/03/16.
 */
public class GenerateDataForSupervisedDTO {

    /* Input to the program */
    //TODO:javax validation //@Min(15)
    Integer Total_Size;
    Integer Positive_Percent;
    Integer Rank;
    Double Cell_Max_Boundary;


    //Integer label_column = 1;
    //Integer PK = 0;
    //String Piping;
    
    /*  Getters */

    public Integer getTotal_Size() {
        return Total_Size;
    }

    public void setTotal_Size(Integer total_Size) {
        Total_Size = total_Size;
    }

    public Integer getPositive_Percent() {
        return Positive_Percent;
    }

    public void setPositive_Percent(Integer positive_Percent) {
        Positive_Percent = positive_Percent;
    }
//    public Integer getlabel_column() {
//        return label_column;
//    }
//    public Integer getpk() {
//        return pk;
//    }
//    public string getpiping() {
//        return piping;
//    }

    /*  Setters */

    public Integer getRank() {
        return Rank;
    }

    public void setRank(Integer rank) {
        Rank = rank;
    }

    public Double getCell_Max_Boundary() {
        return Cell_Max_Boundary;
    }

    public void setCell_Max_Boundary(Double cell_Max_Boundary) {
        Cell_Max_Boundary = cell_Max_Boundary;
    }
//    public void setPK(Integer PK) {
//        PK = PK;
//    }
//    public void setLabel_column(Integer label_column) {
//        label_column = label_column;
//    }
//    public void setPiping(String piping) {
//        this.Piping = piping;
//    }

}
