package com.tr.csvgenerator.GenerateDataForSupervised;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by Naor Ben David on 10/03/16.
 */
public class PipingHeandler {


    public static List<Integer> PipingHeandler(String pipe, String delimiter) throws Exception {

        List<Integer> res = new ArrayList<>();
        String[] indexs = pipe.split(delimiter);
        for (int i = 0; i < indexs.length; i++) {
            if (indexs[i].contains(":")) {
                String[] HighAndLow = indexs[i].split(":");
                if (HighAndLow.length == 2) {
                    throw new Exception("the format need to be: \"LowIndex:HighIndex\"");
                }
                Integer low = new Integer(HighAndLow[0]);
                Integer high = new Integer(HighAndLow[1]);
                if (low > high) {
                    throw new Exception("the format need to be: \"LowIndex:HighIndex\"");
                }
                while (low <= high) {
                    res.add(low++);
                }
            } else {
                res.add(new Integer(indexs[i]));
            }
        }


        /* Sorting of array list using Collections.sort*/
        Collections.sort(res);
        //// TODO: 10/03/16 HANDEL THE CASE OF DUPLICATED VALUES e.g: [2,12:20,2,12,2,12]
        return res;
    }


}
