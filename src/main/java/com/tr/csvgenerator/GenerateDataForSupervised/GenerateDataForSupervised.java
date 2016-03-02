package com.tr.csvgenerator.GenerateDataForSupervised;

import com.tr.csvgenerator.dto.GenerateDataForSupervisedDTO;

import java.io.IOException;

/**
 * Created by naor on 02/03/16.
 */
public interface GenerateDataForSupervised {

    boolean GenerateDataForSupervised(GenerateDataForSupervisedDTO dto) throws IOException;

    String validateInput(GenerateDataForSupervisedDTO dto);
}
