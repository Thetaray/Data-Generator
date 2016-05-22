package RandomDataGeneratorBuilder.ContainerPackage;

import CommonObjects.ContainerPackage.Container;
import CommonObjects.ContainerPackage.RegularContainer;
import CommonObjects.FeatureBuilderPackage.Feature;
import RandomDataGeneratorBuilder.FeatureBuilderPackage.FeatureBuilder;

import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: Naor Ben David
 * Mail: naor.bendaivd@thetaray.com
 */
public class RegularContainerBuilder implements FeaturableContainer {


    private RegularContainer regularContainer;
    private LinkedList<Feature> featuresList;

    public RegularContainerBuilder() {
        regularContainer = new RegularContainer();
        featuresList = new LinkedList<>();
    }


    public ContainerBuilder withNumberOfElements(Integer numberOfElements) {
        this.regularContainer.setNumberOfElements(numberOfElements);
        return this;
    }


    public RegularContainerBuilder withFeatures(FeatureBuilder... featuresBuilder) {
        for (FeatureBuilder fb : featuresBuilder) {
            featuresList.add(fb.build());
        }
        return this;

    }

    public ContainerBuilder withLabel(String label) {
        this.regularContainer.setLabel(label);
        return this;
    }

    public ContainerBuilder withLabelColumn(Integer labelColumn) {
        this.regularContainer.setLabelColumn(labelColumn);
        return this;
    }

    @Override
    public Container build() {
        regularContainer.setFeatures(featuresList);
        return regularContainer;
    }
}
