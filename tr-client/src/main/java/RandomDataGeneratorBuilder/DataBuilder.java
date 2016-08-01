package RandomDataGeneratorBuilder;

import CommonObjects.ContainerPackage.Container;
import CommonObjects.Data;
import RandomDataGeneratorBuilder.ContainerPackage.ContainerBuilder;
import RandomDataGeneratorBuilder.ContainerPackage.FeaturableContainer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
        data.setPkColumn(1);
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

    public DataBuilder withDuplicateContainers(int numberOfDuplicates, ContainerBuilder... containerBuilders) {
        for (int i = 0; i < numberOfDuplicates; i++) {
            withContainers(containerBuilders);
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



}
