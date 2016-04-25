package com.tr.csvgenerator.DataGenerator;

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
    private Integer numberOfElements;

    private String label;

    private Integer labelColumn;
    private List<Feature> Features;
    /*      Internal variable     */
    private int countSupply = 0;

    public RegularContainer(ArrayList<Feature> features , int numberOfElements){
        Features = features;
        this.numberOfElements = numberOfElements;
    }

    public RegularContainer() {
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public RegularContainer setLabelColumn(Integer labelColumn) {
        this.labelColumn = labelColumn;
        return this;
    }

    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    @Override
    public Integer getNumberOfElements() {
        return numberOfElements;
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

    public Integer getLabelColumn() {
        return labelColumn;
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
