package com.tr.csvgenerator.GenerateDataForSupervised;

import au.com.bytecode.opencsv.CSVWriter;
import com.tr.csvgenerator.GenerateDataForSupervised.Shapes.Rectangle;
import com.tr.csvgenerator.dto.GenerateDataForSupervisedDTO;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by naor on 02/03/16.
 */
@Service
public class GenerateDataForSupervisedImpl implements GenerateDataForSupervised {

    List<Integer> categoryIndexes;

    @Override
    public String GenerateDataForSupervised(GenerateDataForSupervisedDTO dto) throws IOException {

        /*  Read Params */
        int Rank = dto.getRank();
        Double Cell_Max_Boundary = dto.getCell_Max_Boundary();
        int Total_Size = dto.getTotal_Size();
        int Positive_Percent = dto.getPositive_Percent();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        Date date = new Date();


        String FilePath ="";
        if(System.getProperty("user.name").equals("naor"))
            FilePath = System.getProperty("user.dir") + File.separator + dateFormat.format(date) + "_SP_DataSet_(" + Total_Size + ").csv";
        else
            FilePath = "/opt/tr/input/csvgen/output/" + dateFormat.format(date) + "_SP_DataSet_(" + Total_Size + ").csv";

        /*  Transform params for general to specific */
        int Train_Pos_Size = Total_Size * Positive_Percent / 100;
        int Train_Neg_Size = Total_Size - Train_Pos_Size;
        SmallDataSetGenerator BatchOfDataSet;


        /*  Make Shape  */
        Rectangle rec = new Rectangle(Rank, Cell_Max_Boundary, categoryIndexes);

        /*  Generate the .csv file and directories to it    */
        File file = new File(FilePath);
        file.getParentFile().mkdirs();
        CSVWriter writer = new CSVWriter(new FileWriter(file), ',', CSVWriter.NO_QUOTE_CHARACTER);


        /* Calculate the batch  size  */
        long SampleSize = (Rank + 2) * Double.SIZE;                         // Size of one sample
        long TotalMemForJVM = Runtime.getRuntime().totalMemory();           // Total memory available to JVM (bytes)
        int Batch_Size = (int) ((TotalMemForJVM / SampleSize) * 0.8);       // Amount of samples to one batch
        int Small_Group_Pos_Size = Batch_Size * Positive_Percent / 100;     // Ratio of small group from the total
        int Small_Group_Neg_Size = Batch_Size - Small_Group_Pos_Size;       //

        boolean firstTime = true;


        while (Train_Pos_Size > 0 && Train_Neg_Size > 0) {
            /* Create The Data */
            if ((Small_Group_Pos_Size + Small_Group_Neg_Size) < (Train_Pos_Size + Train_Neg_Size)) {
                BatchOfDataSet = new SmallDataSetGenerator(Small_Group_Pos_Size, Small_Group_Neg_Size, rec);
            } else {
                BatchOfDataSet = new SmallDataSetGenerator(Train_Pos_Size, Train_Neg_Size, rec);
            }
            /* Write Data To File*/
            writeToFile(BatchOfDataSet, writer, firstTime, 1);//pk=1

            firstTime = false;
            Train_Pos_Size -= Small_Group_Pos_Size;
            Train_Neg_Size -= Small_Group_Neg_Size;

        }
        writer.close();

        return file.getPath();
    }

    @Override
    public String validateInput(GenerateDataForSupervisedDTO dto) throws Exception {

        StringBuilder res = new StringBuilder();
        categoryIndexes = new ArrayList<>();

        /*  Read Params */
        int Rank = dto.getRank();
        Double Cell_Max_Boundary = dto.getCell_Max_Boundary();
        int Total_Size = dto.getTotal_Size();
        int Positive_Percent = dto.getPositive_Percent();


        /*  Validate inputs */
        if (Rank <= 0) {
            res.append("ERROR: Rank must be bigger than 0\n");
        }
        if (Total_Size <= 0) {
            res.append("ERROR: Total_Size Must by bigger than 0.\n");
        }
        if (Positive_Percent <= 0 || Positive_Percent >= 100) {
            res.append("ERROR: Positive_Percent must by number between 0-100.\n");
        }

//        if(!dto.getPiping().equals("")){
//            categoryIndexes = PipingHeandler.PipingHeandler(dto.getPiping(),",");
//        }
        if (res.toString().isEmpty()) {
            res.append("OK");
        }

        return res.toString();
    }

    private void writeToFile(SmallDataSetGenerator trainingSet, CSVWriter writer, boolean firstTime, int PK) throws IOException
    {
        int n = trainingSet.getDimension();
        int m = trainingSet.numOfSamples();
        List<String[]> records = null;

        if (firstTime)
            records = new ArrayList<String[]>(m + 1);
        else
            records = new ArrayList<String[]>(m);


        for (int i = 0; i < m; i++) //+1 for header
        {
            String[] str = new String[n + 2]; //+1 for pk and the n columns is the label
            if (firstTime) {
                for (int j = 0; j < n + 2; j++) //pk & Label
                    str[j] = j + "";
                firstTime = false;
                records.add(str);
                str = new String[n + 2];
            }


            for (int j = 0; j <= n; j++) {
                str[j] = String.format("%.16f", trainingSet.valAt(i, j));
            }

            str[n + 1] = PK + "";
            PK++;
            records.add(str);
        }
        writer.writeAll(records);
    }
}
