package com.tr.csvgenerator.service;

import au.com.bytecode.opencsv.CSVWriter;
import com.tr.csvgenerator.dto.CsvConfigDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by erez on 11/25/15.
 */
@Service
public class CsvGeneratorServiceImpl implements CsvGeneratorService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private CsvConfigDTO csvConfigDTO;
    private CSVWriter csvWriter;
    private File csvFilename;

    private static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    @Override
    public String validateCsvConfig(CsvConfigDTO csvConfigDTO) {
        String errorMessage = "";

        //todo Copy Values from csvConfig to returned
        if (csvConfigDTO.getFileSizeInMB() < 1 || csvConfigDTO.getFileSizeInMB() > 1000) {
            errorMessage += "File Size must be : bigger than 0 and smaller than 1000 ";
        }
        if (csvConfigDTO.getNumberOfFilesToGen() < 1 || csvConfigDTO.getNumberOfFilesToGen() > 10) {
            errorMessage += "Too Many Files Too Generate";
        }
        if (csvConfigDTO.getColumnsList().length > csvConfigDTO.getNumberOfColumns()) {
            logger.warn("Size Of Column list is bigger than number of columns\n" +
                    "will generate csv file based on column list");
            csvConfigDTO.setNumberOfColumns(csvConfigDTO.getColumnsList().length);
        } else {
            if (csvConfigDTO.getColumnsList().length != csvConfigDTO.getNumberOfColumns()) {
                String[] columnList = new String[csvConfigDTO.getNumberOfColumns()];
                for (int i = 0; i < csvConfigDTO.getColumnsList().length; i++) {
                    columnList[i] = csvConfigDTO.getColumnsList()[i];
                }
                for (int i = csvConfigDTO.getColumnsList().length; i < csvConfigDTO.getNumberOfColumns(); i++) {
                    columnList[i] = getIntToString(i);
                }
                csvConfigDTO.setColumnsList(columnList);
            }
        }

        return errorMessage;
    }

    @Override
    public boolean createCsv(CsvConfigDTO csvConfig) throws IOException, InterruptedException {
        this.csvConfigDTO = csvConfig;
        while (csvConfig.getNumberOfFilesToGen() != 0) {
            Thread.sleep(1000);
            long time = new Date().getTime();
            this.csvWriter = createWriter();
            long numberOfBytes = 0;
            Random r = new Random();
            String[] stringsColumn;
            int i = 0;
            List<String[]> data = new ArrayList<>();
            List<String[]> biggerData = new ArrayList<>();
            while (numberOfBytes < (csvConfig.getFileSizeInMB() * 1000000)) {
                if (numberOfBytes > 0) {
                    data.clear();
                }
                while (i < csvConfig.getRandomBatch()) {
                    stringsColumn = new String[csvConfig.getNumberOfColumns()];
                    if (i == 0 && numberOfBytes == 0) {
                        stringsColumn = csvConfig.getColumnsList();
                    }
                    else {
                        for (int col = 0; col < csvConfig.getNumberOfColumns(); col++) {
                            int random = r.nextInt(csvConfig.getMaxValue());
                            stringsColumn[col] = Integer.toString(random);
                        }
                    }

                    data.add(stringsColumn);
                    i++;
                }
                i = 0;
                biggerData.addAll(data);
                for (int j = 0; j < biggerData.size(); j++) {
                    csvWriter.writeAll(biggerData);
                    csvWriter.flush();
                    numberOfBytes = csvFilename.length();
//                    if(j % 5 == 0) {
//                        System.out.println(humanReadableByteCount(numberOfBytes, true));
//                    }
                    if(numberOfBytes > (csvConfig.getFileSizeInMB() * 1000000)){
                        break;
                    }
                }

            }
            csvConfig.oneFileCreated();
            long time2 = new Date().getTime();
            System.out.println("Took " + TimeUnit.SECONDS.convert(time2 - time, TimeUnit.MILLISECONDS) + " Seconds");
        }
        csvWriter.close();
        return true;
    }

    private String getIntToString(int value) {

        StringBuffer result = new StringBuffer();
        if(value == 0){
            result.append("Header");
        }
        while (--value >= 0) {
            result.append((char) ('A' + value % 26));
            value /= 26;
        }
        return result.toString();
    }

    private void checkDirectory(String directory) {
        File dir = new File(directory);
        if (!dir.exists())
            dir.mkdirs();
    }

    private CSVWriter createWriter() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(new Date());
        checkDirectory(csvConfigDTO.getOutputFolder());
        csvFilename = new File(csvConfigDTO.getOutputFolder() + csvConfigDTO.getOutputfileName() + "_" + timeStamp + ".csv");
        return new CSVWriter(new FileWriter(csvFilename), csvConfigDTO.getSeparator().getSeparatorAsChar());
    }
}
