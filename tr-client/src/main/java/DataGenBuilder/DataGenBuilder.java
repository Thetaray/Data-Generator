package DataGenBuilder;

import RandomDataGeneratorObject.ContainerPackage.Container;
import RandomDataGeneratorObject.Data;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by roman on 19/05/16.
 */
public class DataGenBuilder {

    private Data mainData;
    private List<Container> containerList;
    private Gson gson;

    public DataGenBuilder() {
        mainData = new Data();
        containerList = new LinkedList<>();
    }

    public DataGenBuilder withSeed(Long seed) {
        mainData.setSeed(seed);
        return this;
    }

    public DataGenBuilder withShuffle(Integer shuffle) {
        mainData.setShuffle(shuffle);
        return this;
    }


    public DataGenBuilder withContainer(Container container) {
        containerList.add(container);
        return this;
    }

    public DataGenBuilder withOutPutFile(String path) {
        mainData.setOutputFile(path);
        return this;
    }

    public DataGenBuilder withPkColumn(Integer pkColumn) {
        mainData.setPkColumn(pkColumn);
        return this;
    }

    public String builderRequest() {
        gson = new GsonBuilder().setPrettyPrinting().serializeNulls().setDateFormat("EEE MMM dd HH:mm:ss z yyyy").create();
        mainData.setContainers(containerList);
        String request = gson.toJson(mainData);
        return request;
    }

}
