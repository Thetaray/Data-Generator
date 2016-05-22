package RandomDataGeneratorBuilder.FeatureBuilderPackage;

import CommonObjects.FeatureBuilderPackage.DistributionFeature;
import CommonObjects.FeatureBuilderPackage.Feature;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Naor Ben David
 * Mail: naor.bendaivd@thetaray.com
 */
public class DistributionFeatureBuilder implements FeatureBuilder {

    private DistributionFeature distributionFeature;



    public DistributionFeatureBuilder(Distribution distribution, List<Double> params) {
        distributionFeature = new DistributionFeature();
        this.distributionFeature.setModel(distribution.getDistribution());
        this.distributionFeature.setParams(params);

    }

    public DistributionFeatureBuilder withModel(String model) {
        distributionFeature.setModel(model);
        return this;
    }

    public DistributionFeatureBuilder withParams(List<Double> params) {

        this.distributionFeature.setParams(params);
        return this;
    }

    @Override
    public Feature build() {
        return distributionFeature;
    }
}
