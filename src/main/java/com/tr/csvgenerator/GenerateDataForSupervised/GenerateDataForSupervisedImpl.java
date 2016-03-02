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
import java.util.Calendar;
import java.util.List;

/**
 * Created by naor on 02/03/16.
 */
@Service
public class GenerateDataForSupervisedImpl implements GenerateDataForSupervised {

    @Override
    public boolean GenerateDataForSupervised(GenerateDataForSupervisedDTO dto) throws IOException {

        int Train_Pos_Size = dto.getTrain_Pos_Size();
        int Train_Neg_Size = dto.getTrain_Neg_Size();
        int Small_Group_Pos_Size = dto.getSmall_Group_Pos_Size();
        int Small_Group_Neg_Size = dto.getSmall_Group_Neg_Size();

        SmallDataSetGenerator TrainingSet;
        SmallDataSetGenerator TestingSet;

        dto.setPK(dto.getRank() + 1);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        /* Get Times */
        System.out.println("start: " + dateFormat.format(Calendar.getInstance().getTime()));

        /* Make Shape*/
        Rectangle rec = new Rectangle(dto.getRank(), dto.getCellMaxBoundary());

        /* Get Times */
        System.out.println("Finish create rectangle: " + dateFormat.format(Calendar.getInstance().getTime()));

        File file = new File(dto.getTrainDataPath());
        CSVWriter writer = new CSVWriter(new FileWriter(file), ',', CSVWriter.NO_QUOTE_CHARACTER);

        boolean firstTime = true;


        while (Train_Pos_Size > 0 && Train_Neg_Size > 0) {
            /* Create The Data */
            TrainingSet = new SmallDataSetGenerator(Small_Group_Pos_Size, Small_Group_Neg_Size, rec);

            /* Write Data To File*/
            writeToFile(TrainingSet, writer, firstTime, dto.getPK());

            firstTime = false;
            Train_Pos_Size -= Small_Group_Pos_Size;
            Train_Neg_Size -= Small_Group_Neg_Size;

        }
        writer.close();

        System.out.println("Finish create TrainingSet: " + dateFormat.format(Calendar.getInstance().getTime()));

        return true;
    }

    @Override
    public String validateInput(GenerateDataForSupervisedDTO dto) {
        StringBuffer errMsg = new StringBuffer("No Errors");

        if (dto.getPK() > dto.getRank())
            errMsg.append("*************** Pk out of boundary **************");
        if (dto.getRank() < 1)
            errMsg.append("*************** Rank size lower then one *************");


        return errMsg.toString();
    }

    private void writeToFile(SmallDataSetGenerator trainingSet, CSVWriter writer, boolean firstTime, int PK) throws IOException {
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
