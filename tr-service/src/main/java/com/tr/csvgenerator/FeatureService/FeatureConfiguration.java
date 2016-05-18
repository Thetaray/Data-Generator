package com.tr.csvgenerator.FeatureService;

import com.tr.csvgenerator.dto.CsvExtendableDTO;
import org.springframework.stereotype.Component;


/**
 * Created by roman on 01/03/16.
 */
@Component
public class FeatureConfiguration {


    public FeatureConfiguration() {
    }

    public FeatureService getFeature(CsvExtendableDTO dto) {
        if (dto.getTimeStampFeature() == 1) {
            return new TimeStampFeature();
        } else {
            return new IdFeature();
        }
    }
}
