package com.tr.csvgenerator.RandomDataGenerator;


import com.tr.csvgenerator.RandomDataGenerator.ContainerPackage.Container;
import org.apache.commons.math3.random.RandomDataGenerator;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Naor Ben David
 * Mail: naor.bendaivd@thetaray.com
 */
public class Data {


    private static RandomDataGenerator RFI = null;

    /*  User Input  */
    private Long seed;
    private Integer shuffle = 0; //0 - no shuffle, 1 - cycle shuffle, 2 - random
    private String outputFile;
    private Integer pkColumn = 0;
    private List<Container> Containers;

    /*  Internal use    */
    private int pk = 1;
    private int lastIndex = 0;

    public Data(String outputPath, long seed, int pk_column, ArrayList<Container> containers) {

        initDefaultValues();
        this.seed = seed;
        Containers = containers;
        this.outputFile = outputPath;
        this.pkColumn = pk_column;
    }

    public Data() {
        initDefaultValues();
    }

    public static RandomDataGenerator getRandom() {
        return RFI;
    }

    private void initDefaultValues() {
        RFI = new RandomDataGenerator();
    }

    public String getOutputFile() {

        if(outputFile==null)
        {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            Date date = new Date();
            int Total_Size =0;
            if(Containers!=null)
                for (Container c:Containers)
                    Total_Size+=c.getNumberOfElements();

            String FilePath ="";
            if(System.getProperty("user.name").equals("naor"))
                outputFile = System.getProperty("user.dir") + File.separator + dateFormat.format(date) + "_DataSet_(" + Total_Size + ").csv";
            else
                outputFile = "/opt/tr/input/csvgen/output/" + dateFormat.format(date) + "_DataSet_(" + Total_Size + ").csv";
        }
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    public void setSeed(long seed) {
        this.seed = seed;
        RFI.reSeed(seed);
    }

    public List<Container> getContainers() {
        return Containers;
    }

    public void setContainers(List<Container> containers) {
        Containers = containers;
    }

    public Integer getShuffle() {
        return shuffle;
    }

    public void setShuffle(Integer shuffle) {
        this.shuffle = shuffle;
    }

    public int getPkColumn() {
        return pkColumn;
    }

    public void setPkColumn(int pkColumn) {
        this.pkColumn = pkColumn;
    }

    public boolean hasNext() {

        //if we have containers we have something to return;
        if(Containers.size() > 0)
            return true;
        else
            return false;
    }

    public List<Object> Next() throws Exception {

        int indexOfContainer = selectContainer(getShuffle());
        List<Object> res = Containers.get(indexOfContainer).Next();

        /*  set the Label and the PK column */
        String label = Containers.get(indexOfContainer).getLabel();
        Integer labelColumn = Containers.get(indexOfContainer).getLabelColumn();
        if (label != null && labelColumn != null)
        {
            if(labelColumn > 0 && labelColumn <= res.size()+1)
            {
                if (labelColumn < pkColumn)
                {
                    res.add(labelColumn, label);
                    res.add(pkColumn, pk);
                }
                else
                {
                    res.add(pkColumn, pk);
                    res.add(labelColumn, label);
                }
            }
            else throw new Exception("ERROR: label column un legal");

        }
        else
            res.add(pkColumn, pk);


        /*  Remove container if we take all the elements from it*/
        if(!Containers.get(indexOfContainer).hasNext())
            Containers.remove(indexOfContainer);

        pk++;

        return res;

    }

    private int selectContainer(Integer shuffle) throws Exception {


        if(shuffle==0)return 0;
        if(shuffle==1) {
            lastIndex++;
            return lastIndex % getContainers().size();
        }
        if(shuffle == 2)
        {
            return RFI.nextInt(0,getContainers().size()-1);
        }
        else throw new Exception("ERROR: bad shuffle number ");
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Data{");
        sb.append("outputFile='").append(outputFile).append('\'');
        sb.append(", seed='").append(seed).append('\'');
        sb.append(", pkColumn='").append(pkColumn).append('\'');
        sb.append(", Containers=").append(Containers);
        sb.append(']');
        return sb.toString();
    }
}
