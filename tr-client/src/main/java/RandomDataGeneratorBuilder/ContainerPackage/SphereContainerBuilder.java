package RandomDataGeneratorBuilder.ContainerPackage;

import CommonObjects.ContainerPackage.Container;
import CommonObjects.ContainerPackage.SphereContainer;

/**
 * Created by IntelliJ IDEA.
 * User: Naor Ben David
 * Mail: naor.bendaivd@thetaray.com
 * Date: 5/2/16
 * Time: 1:53 PM
 */
public class SphereContainerBuilder implements ContainerBuilder {

    private SphereContainer sphereContainer;


    public SphereContainerBuilder() {
        sphereContainer = new SphereContainer();
    }

    public SphereContainerBuilder withFill(Boolean withFill) {
        sphereContainer.setWithFill(withFill);
        return this;
    }

    public SphereContainerBuilder withRadius(Integer radius) {
        this.sphereContainer.setRadius(radius);
        return this;
    }

    public SphereContainerBuilder withLabel(String label) {

        this.sphereContainer.setLabel(label);
        return this;
    }

    @Override
    public ContainerBuilder withLabelColumn(Integer labelColumn) {
        this.sphereContainer.setLabelColumn(labelColumn);
        return this;
    }

    @Override
    public Container build() {
        return sphereContainer;
    }

    public SphereContainerBuilder withNumberOfElements(Integer numberOfElements) {
        this.sphereContainer.setNumberOfElements(numberOfElements);
        return this;
    }
}
