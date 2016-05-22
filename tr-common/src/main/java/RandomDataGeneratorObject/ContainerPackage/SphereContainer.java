package RandomDataGeneratorObject.ContainerPackage;

import RandomDataGeneratorObject.Data;
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
            res.add(i, (vector[i] * radius) + centrePoint.get(i));

        countSupply++;
        return res;
    }
}
