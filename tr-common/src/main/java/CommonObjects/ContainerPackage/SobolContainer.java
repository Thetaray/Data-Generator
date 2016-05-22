package CommonObjects.ContainerPackage;

import com.google.common.primitives.Doubles;
import org.apache.commons.math3.random.SobolSequenceGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Naor Ben David
 * Mail: naor.bendaivd@thetaray.com
 * Date: 5/2/16
 * Time: 6:45 PM
 */
public class SobolContainer implements Container {
    private ArrayList<Double> centrePoint;
    private SobolSequenceGenerator sobolVec;
    private Integer numberOfElements;
    private Integer labelColumn;
    private String label;
    private int countSupply = 0;


    public ArrayList<Double> getCentrePoint() {
        return centrePoint;
    }

    public void setCentrePoint(ArrayList<Double> centrePoint) {
        this.centrePoint = centrePoint;
        sobolVec = new SobolSequenceGenerator(centrePoint.size());
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
        return (List<Object>) (List<?>) Doubles.asList(sobolVec.nextVector());

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

    public void setLabelColumn(Integer labelColumn) {
        this.labelColumn = labelColumn;
    }
}
