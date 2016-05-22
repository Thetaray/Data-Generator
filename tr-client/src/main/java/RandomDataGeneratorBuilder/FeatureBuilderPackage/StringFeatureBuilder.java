package RandomDataGeneratorBuilder.FeatureBuilderPackage;

import CommonObjects.FeatureBuilderPackage.Feature;
import CommonObjects.FeatureBuilderPackage.StringFeature;

/**
 * Created by IntelliJ IDEA.
 * User: Naor Ben David
 * Mail: naor.bendaivd@thetaray.com
 * Date: 15/5/16
 */

public class StringFeatureBuilder implements FeatureBuilder {


    private StringFeature stringFeature;

    public StringFeatureBuilder() {
        stringFeature = new StringFeature();
    }

    public StringFeatureBuilder withLength(int length) {
        this.stringFeature.setLength(length);
        return this;
    }

    public StringFeatureBuilder WithLowerLetter(boolean withLowerLetter) {
        this.stringFeature.setWithLowerLetter(withLowerLetter);
        return this;
    }

    public StringFeatureBuilder withUpperLetter(boolean withUpperLetter) {

        this.stringFeature.setWithUpperLetter(withUpperLetter);
        return this;
    }

    public StringFeatureBuilder withSpace(boolean withSpace) {
        this.stringFeature.setWithSpace(withSpace);
        return this;
    }

    public StringFeatureBuilder withDigit(boolean withDigit) {
        this.stringFeature.setWithDigit(withDigit);
        return this;
    }

    @Override
    public Feature build() {
        return stringFeature;
    }
}