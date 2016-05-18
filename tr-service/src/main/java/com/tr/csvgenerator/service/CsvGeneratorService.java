package com.tr.csvgenerator.service;

import com.tr.csvgenerator.dto.CsvConfigDTO;

import java.io.IOException;

/**
 * Created by erez on 11/25/15.
 */
public interface CsvGeneratorService {

    String validateCsvConfig(CsvConfigDTO csvConfigDTO);

    boolean createCsv(CsvConfigDTO csvConfig) throws IOException, InterruptedException;
}
