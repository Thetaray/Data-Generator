import RandomDataGeneratorBuilder.ContainerPackage.RegularContainerBuilder;
import RandomDataGeneratorBuilder.DataBuilder;
import RandomDataGeneratorBuilder.FeatureBuilderPackage.Distribution;
import RandomDataGeneratorBuilder.FeatureBuilderPackage.DistributionFeatureBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Created by roman on 31/07/16.
 */
public class TestCore {

    @Test
    public void testDuplicationFeatures() throws JsonProcessingException {
        String s = new DataBuilder()
                .withContainers(new RegularContainerBuilder()
                                .withDuplicateFetures(100, new DistributionFeatureBuilder(Distribution.NORMAL, Arrays.asList(0.0, 600.0)))
                                .withNumberOfElements(10000)
                        , new RegularContainerBuilder()
                                .withDuplicateFetures(100, new DistributionFeatureBuilder(Distribution.NORMAL, Arrays.asList(1750.0, 500.0))
                                ).withNumberOfElements(1000)
                        , new RegularContainerBuilder()
                                .withDuplicateFetures(100, new DistributionFeatureBuilder(Distribution.NORMAL, Arrays.asList(2750.0, 500.0)))
                                .withNumberOfElements(500)
                        , new RegularContainerBuilder()
                                .withDuplicateFetures(100, new DistributionFeatureBuilder(Distribution.NORMAL, Arrays.asList(3750.0, 500.0)))
                                .withNumberOfElements(250))
                .withOutputFile("/home/roman/Documents/DATAGENT/TT.csv").builderRequest();
        System.out.println(s);
    }

    @Test
    public void createRequestWithLittleAmountOffAnomalies() throws JsonProcessingException {
        String s = new DataBuilder()
                .withContainers(new RegularContainerBuilder()
                        .withDuplicateFetures(100, new DistributionFeatureBuilder(Distribution.NORMAL, Arrays.asList(100.0, 0.2)))
                        .withNumberOfElements(10000))
                .withOutputFile("/home/roman/Documents/DATAGENT/TT2.csv")
                .builderRequest();
        System.out.println(s);
    }


}
