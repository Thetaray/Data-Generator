package RandomDataGeneratorBuilder.FeatureBuilderPackage;


import CommonObjects.FeatureBuilderPackage.DateFeature;
import CommonObjects.FeatureBuilderPackage.Feature;

/**
 * Created by IntelliJ IDEA.
 * User: Naor Ben David
 * Mail: naor.bendaivd@thetaray.com
 * Date: 15/5/16
 */

public class DateFeatureBuilder implements FeatureBuilder {

    private DateFeature dateFeature;

    public DateFeatureBuilder() {
        dateFeature = new DateFeature();
    }

    public DateFeatureBuilder withMinYear(int minYear) {
        this.dateFeature.setMinYear(minYear);
        return this;
    }


    public DateFeatureBuilder withMaxYear(int maxYear) {
        this.dateFeature.setMaxYear(maxYear);
        return this;
    }


    public DateFeatureBuilder withSeparator(String separator) {
        this.dateFeature.setSeparator(separator);
        return this;
    }


    @Override
    public Feature build() {
        return dateFeature;
    }
}
