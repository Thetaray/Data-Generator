package com.tr.csvgenerator.ExetendDataService;

import com.tr.csvgenerator.dto.CsvExtendableDTO;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by roman on 18/01/16.
 */
public interface ExtendCsvService {

    boolean extendToCsvFile(CsvExtendableDTO dto) throws IOException, ExecutionException, InterruptedException;

    String validateInput(CsvExtendableDTO dto);

}
