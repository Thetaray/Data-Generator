package RandomDataGeneratorBuilder.ContainerPackage;

import CommonObjects.ContainerPackage.Container;

/**
 * Created by IntelliJ IDEA.
 * User: Naor Ben David
 * Mail: naor.bendaivd@thetaray.com
 */

public interface ContainerBuilder {

    ContainerBuilder withNumberOfElements(Integer numberOfElements);

    ContainerBuilder withLabel(String Label);

    ContainerBuilder withLabelColumn(Integer labelColumn);

    Container build();
}
