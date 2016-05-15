package com.tr.csvgenerator.RandomDataGenerator.ContainerPackage;

import com.tr.csvgenerator.RandomDataGenerator.FeaturePackage.Feature;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Naor Ben David
 * Mail: naor.bendaivd@thetaray.com
 */
public class RegularContainer implements Container
{

    /*      User variable     */
    private Integer numberOfElements = 100;
    private Integer labelColumn;
    private String label;
    private List<Feature> Features;

    /*      Internal variable     */
    private int countSupply = 0;

    public RegularContainer(ArrayList<Feature> features , int numberOfElements){
        Features = features;
        this.numberOfElements = numberOfElements;
    }

    public RegularContainer() {
    }

    @Override
    public Integer getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public List<Feature> getFeatures() {
        return Features;
    }

    public void setFeatures(List<Feature> features) {
        Features = features;
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

    public RegularContainer setLabelColumn(Integer labelColumn) {
        this.labelColumn = labelColumn;
        return this;
    }

    @Override
    public boolean hasNext() {

        if(countSupply < getNumberOfElements())
            return true;
        else
            return false;

    }

    @Override
    public List<Object> Next() throws Exception {
        List<Object> res = new ArrayList<>();//Object[Features.size()];
        for (int i=0 ; i < Features.size() ; i++)
            res.add(Features.get(i).NextValue());
        countSupply++;
        return res;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RegularContainer{");
        sb.append("numberOfElements='").append(numberOfElements).append('\'');
        sb.append(", label='").append(label).append('\'');
        sb.append(", labelColumn='").append(labelColumn).append('\'');
        sb.append(", Features=").append(Features);
        sb.append(']');
        return sb.toString();
    }
}
