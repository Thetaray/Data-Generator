package com.tr.csvgenerator.RandomDataGenerator.FeaturePackage;

import com.tr.csvgenerator.RandomDataGenerator.Data;

import java.util.GregorianCalendar;

/**
 * Created by IntelliJ IDEA.
 * User: Naor Ben David
 * Mail: naor.bendaivd@thetaray.com
 * Date: 15/5/16
 */

public class DateFeature implements Feature {

    GregorianCalendar gc = new GregorianCalendar();
    String separator = "-";
    int minYear = 1900;
    int maxYear = 3010;


    @Override
    public Object NextValue() throws Exception {

        int year = Data.getRandom().nextInt(minYear, maxYear);

        gc.set(gc.YEAR, year);

        int dayOfYear = Data.getRandom().nextInt(1, gc.getActualMaximum(gc.DAY_OF_YEAR));

        gc.set(gc.DAY_OF_YEAR, dayOfYear);

        return gc.get(gc.YEAR) + separator + (gc.get(gc.MONTH) + 1) + separator + gc.get(gc.DAY_OF_MONTH);
    }

    public int getMinYear() {
        return minYear;
    }

    public void setMinYear(int minYear) {
        this.minYear = minYear;
    }

    public int getMaxYear() {
        return maxYear;
    }

    public void setMaxYear(int maxYear) {
        this.maxYear = maxYear;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }


}
