package com.tr.csvgenerator.DataGenerator;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.apache.commons.math3.random.UnitSphereRandomVectorGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Naor Ben David
 * Mail: naor.bendaivd@thetaray.com
 * Date: 5/2/16
 * Time: 1:53 PM
 */
public class SphereContainer implements Container {

    UnitSphereRandomVectorGenerator SphereRandom;
    RandomDataGenerator r = Data.getRandom();
    /*      User variable     */
    private Boolean withFill = false;
    private Integer radius = 100;
    private Integer numberOfElements;
    private Integer labelColumn;
    private ArrayList<Double> centrePoint;
    private String label;
    /*      Internal variable     */
    private int countSupply = 0;

    public ArrayList<Double> getCentrePoint() {
        return centrePoint;
    }

    public void setCentrePoint(ArrayList<Double> centrePoint) {
        this.centrePoint = centrePoint;
        SphereRandom = new UnitSphereRandomVectorGenerator(centrePoint.size(), r.getRandomGenerator());
    }


    public Boolean getWithFill() {
        return withFill;
    }

    public void setWithFill(Boolean withFill) {
        this.withFill = withFill;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getLabelColumn() {
        return labelColumn;
    }

    @Override
    public Integer getNumberOfElements() {
        return numberOfElements;
    }


    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    @Override
    public boolean hasNext() {

        if (countSupply < getNumberOfElements())
            return true;
        else
            return false;

    }

    @Override
    public List<Object> Next() throws Exception {
        List<Object> res = new ArrayList<>();
        double[] vector = SphereRandom.nextVector();

        for (int i = 0; i < vector.length; i++)
            res.add(i, (vector[i] + centrePoint.get(i)) * radius);

        return res;
    }
    //return  (List<Object>)(List<?>)Doubles.asList(SphereRandom.nextVector());

//        ArrayList<Object> res = new ArrayList<>();
//        double val=0;
//
//        for (int i = 0; i < centrePoint.size(); i++)
//        {
//            val=0;
//            for(int j=0;j<i;j++)
//                val+=Math.sin(Theta.get(j));
//            val+=Math.cos(Theta.get(i));
//
//            res.add(i,val*radius);
//        }
//
//        var theta = 2 * Math.PI * u;
//        var phi = Math.acos(2 * v - 1);
//        var x = x0 + (radius * Math.sin(phi) * Math.cos(theta));
//        var y = y0 + (radius * Math.sin(phi) * Math.sin(theta));
//        var z = z0 + (radius * Math.cos(phi));


//        double sum = 0; //xx+yy+zz<r ==> z=sqrt(r-xx-yy)
//        double tmp = 0;
//        double normolized;
//        double angle = Math.random() * Math.PI * 2;
//        int len = centrePoint.size();
//        while(sum>radius || sum==0)
//        {
//            res.clear();
//            for (int i = 0; i < len; i++)
//            {
//                tmp = r.nextLong(-radius, radius);
//                res.add(i, tmp);
//                sum += tmp * tmp;
//            }
//            normolized = Math.sqrt(sum);
//            for (int i = 0; i < len; i++)
//                res.set(i,(double)res.get(i)/normolized);
//
//        }

//        double x = Math.cos(angle)*radius;
//        double y = Math.sin(angle)*radius;
//        double z = radius - x - y;
//        res.add(0,x);
//        res.add(1,y);
//        res.add(2,z);


}
