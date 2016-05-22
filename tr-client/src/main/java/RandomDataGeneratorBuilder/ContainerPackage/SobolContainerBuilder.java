package RandomDataGeneratorBuilder.ContainerPackage;

import CommonObjects.ContainerPackage.Container;
import CommonObjects.ContainerPackage.SobolContainer;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Naor Ben David
 * Mail: naor.bendaivd@thetaray.com
 * Date: 5/2/16
 * Time: 6:45 PM
 */
public class SobolContainerBuilder implements ContainerBuilder {

    private SobolContainer sobolContainer;

    public SobolContainerBuilder() {
        sobolContainer = new SobolContainer();
    }

    public SobolContainerBuilder setCentrePoint(ArrayList<Double> centrePoint) {
        this.sobolContainer.setCentrePoint(centrePoint);
        return this;
    }


    public ContainerBuilder withNumberOfElements(Integer numberOfElements) {
        this.sobolContainer.setNumberOfElements(numberOfElements);
        return this;
    }

    public SobolContainerBuilder withLabel(String label) {
        this.sobolContainer.setLabel(label);
        return this;
    }

    @Override
    public ContainerBuilder withLabelColumn(Integer labelColumn) {
        this.sobolContainer.setLabelColumn(labelColumn);
        return this;
    }

    @Override
    public Container build() {
        return sobolContainer;
    }


}
