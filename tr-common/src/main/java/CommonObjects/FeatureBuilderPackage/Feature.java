package CommonObjects.FeatureBuilderPackage;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Created by IntelliJ IDEA.
 * User: Naor Ben David
 * Mail: naor.bendaivd@thetaray.com
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@type")
public interface Feature {

    Object NextValue() throws Exception;
}
