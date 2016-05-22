package RandomDataGeneratorBuilder.ContainerPackage;

import RandomDataGeneratorBuilder.FeatureBuilderPackage.FeatureBuilder;

/**
 * Created by naor on 5/23/16.
 */
public interface FeaturableContainer extends ContainerBuilder {

    RegularContainerBuilder withFeatures(FeatureBuilder... featuresBuilder);
}
