package com.tr.csvgenerator.FeatureService;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Created by roman on 09/02/16.
 */
@Service("Id")
@Scope("prototype")
public class IdFeature implements FeatureService {
    private    int Index = 1;

    @Override
    public String getFeatureNameHeader() {
        return "KEY";
    }

    @Override
    public String getValueForIndex() {
        return  Integer.toString(Index++);
    }
}
