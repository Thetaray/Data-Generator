package RandomDataGeneratorBuilder;

import CommonObjects.ContainerPackage.Container;
import CommonObjects.Data;
import RandomDataGeneratorBuilder.ContainerPackage.ContainerBuilder;
import RandomDataGeneratorBuilder.ContainerPackage.FeaturableContainer;
import RandomDataGeneratorBuilder.ContainerPackage.RegularContainerBuilder;
import RandomDataGeneratorBuilder.FeatureBuilderPackage.Distribution;
import RandomDataGeneratorBuilder.FeatureBuilderPackage.DistributionFeatureBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: Naor Ben David
 * Mail: naor.bendaivd@thetaray.com
 */
public class DataBuilder {


    private Data data;
    private LinkedList<Container> containerBuilderList;

    public DataBuilder(long seed, String outputFilePath, Integer shuffle, Integer pkColumn) {

        data.setSeed(seed);
        data.setOutputFile(outputFilePath);
        data.setShuffle(shuffle);
        data.setPkColumn(pkColumn);
    }

    public DataBuilder() {
        data = new Data();
        containerBuilderList = new LinkedList<>();
    }

    public DataBuilder withOutputFile(String outputFile) {
        this.data.setOutputFile(outputFile);
        return this;
    }

    public DataBuilder withSeed(long seed) {
        this.data.setSeed(seed);
        return this;

    }

    public DataBuilder withContainers(ContainerBuilder... containersBuilder) {
        for (ContainerBuilder cb : containersBuilder) {
            containerBuilderList.add(cb.build());
        }
        return this;
    }

    public DataBuilder withFeaturableContainer(FeaturableContainer featurableContainer) {

        containerBuilderList.add(featurableContainer.build());
        return this;
    }

    public DataBuilder withShuffle(Integer shuffle) {
        this.data.setShuffle(shuffle);
        return this;

    }

    public DataBuilder withPkColumn(Integer pkColumn) {
        this.data.setPkColumn(pkColumn);
        return this;
    }

    public String builderRequest() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        data.setContainers(containerBuilderList);
        String request = mapper.writeValueAsString(data);
        return request;
    }


    @Test
    public void test() throws JsonProcessingException {


        Integer testNumber = 0;
        String DataSourceName = "DataSetPos1#" + testNumber + ".csv";
        String DataPath = "/opt/tr/input/csvgen/output/TestPos1#" + testNumber + "/";
        String OutputFile = DataPath + DataSourceName;
        int label_col_Number = 3;
        int pk_col_Number = 2;

        String jsonConfig = new DataBuilder()
                .withSeed(testNumber)
                .withOutputFile(OutputFile)
                .withShuffle(3)
                .withPkColumn(pk_col_Number)
                .withContainers(
                        new RegularContainerBuilder()
                                .withFeatures(
                                        new DistributionFeatureBuilder(Distribution.UNIFORM, Arrays.asList(200.0, 1500.0)),
                                        new DistributionFeatureBuilder(Distribution.UNIFORM, Arrays.asList(200.0, 500.0)))
                                .withLabel("0")
                                .withLabelColumn(label_col_Number)
                                .withNumberOfElements(1000),

                        new RegularContainerBuilder()
                                .withFeatures(
                                        new DistributionFeatureBuilder(Distribution.UNIFORM, Arrays.asList(200.0, 1500.0)),
                                        new DistributionFeatureBuilder(Distribution.UNIFORM, Arrays.asList(1200.0, 1500.0)))
                                .withLabel("0")
                                .withLabelColumn(label_col_Number)
                                .withNumberOfElements(1000),

                        new RegularContainerBuilder()
                                .withFeatures(
                                        new DistributionFeatureBuilder(Distribution.UNIFORM, Arrays.asList(200.0, 500.0)),
                                        new DistributionFeatureBuilder(Distribution.UNIFORM, Arrays.asList(500.0, 1200.0)))
                                .withLabel("0")
                                .withLabelColumn(label_col_Number)
                                .withNumberOfElements(1000),

                        new RegularContainerBuilder()
                                .withFeatures(
                                        new DistributionFeatureBuilder(Distribution.UNIFORM, Arrays.asList(1200.0, 1500.0)),
                                        new DistributionFeatureBuilder(Distribution.UNIFORM, Arrays.asList(500.0, 1200.0)))
                                .withLabel("0")
                                .withLabelColumn(label_col_Number)
                                .withNumberOfElements(1000),


                        new RegularContainerBuilder()
                                .withFeatures(
                                        new DistributionFeatureBuilder(Distribution.UNIFORM, Arrays.asList(700.0, 1000.0)),
                                        new DistributionFeatureBuilder(Distribution.UNIFORM, Arrays.asList(700.0, 1000.0)))
                                .withLabel("1")
                                .withLabelColumn(label_col_Number)
                                .withNumberOfElements(100)
                )
                .builderRequest();

        System.out.println(jsonConfig);
    }
}
