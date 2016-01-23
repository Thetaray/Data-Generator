package com.tr.csvgenerator.ExetendDataService;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by roman on 20/01/16.
 */
public class AnalyzeOfFieldInCsv {

    private float minValue = (float) 0.00000001;

    private  Random random;

    public AnalyzeOfFieldInCsv(){
        random = new Random();
    }

    public   void parseField(String[] row , String value,int index){
        try{
            Pattern p = Pattern.compile("[a-zA-Z]");
            Matcher m = p.matcher(value);
            if(!m.find()){
                if(value.contains(".")){
                    float number = Float.parseFloat(value);
                    //number = number + (random.nextFloat());
                    number = number + minValue*(random.nextInt(500) + 1);
                    row[index] = Float.toString(number);
                }
                else{
                    int number = Integer.parseInt(value);
                    if(number > 0){
                        if(number > 100){
                            number = number + random.nextInt(100)/2;
                        }
                        if(number > 1000)
                        {
                            number = number + random.nextInt(1000)/2;
                        }
                        row[index] = Integer.toString(number);
                    }
                    else{
                        row[index] = Integer.toString(number);
                    }
                }
            }
            else{
                row[index] = value;
            }
        }
        catch (NumberFormatException e ){
            row[index] = value;
        }

    }
}
