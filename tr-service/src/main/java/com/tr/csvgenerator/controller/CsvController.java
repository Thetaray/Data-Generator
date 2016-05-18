package com.tr.csvgenerator.controller;

import com.tr.csvgenerator.ExetendDataService.ExtendCsvService;
import com.tr.csvgenerator.GenerateDataForSupervised.GenerateDataForSupervised;
import com.tr.csvgenerator.common.TrApiResponse;
import com.tr.csvgenerator.dto.CsvConfigDTO;
import com.tr.csvgenerator.dto.CsvExtendableDTO;
import com.tr.csvgenerator.dto.GenerateDataForSupervisedDTO;
import com.tr.csvgenerator.service.CsvGeneratorService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

import static com.tr.csvgenerator.common.TrApiResponse.StatusCode;

/**
 * Created by erez on 11/24/15.
 */

@RestController
@RequestMapping("/csvgen")
@Api(value = "Csv Controller", description = "generate csv by request")
public class CsvController {

    @Autowired
    private CsvGeneratorService csvGeneratorService;
    @Autowired
    private ExtendCsvService extendCsvService;
    @Autowired
    private GenerateDataForSupervised generateDataForSupervised;


    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public TrApiResponse index() {
        TrApiResponse trApiResponse = new TrApiResponse(StatusCode.Ok);
        trApiResponse.put("result", "You're in Csv Generator!");
        return trApiResponse;
    }


    @ApiOperation(value = "Generate CSV Files", notes = "load config json and Generate CSV"
            + "<br><br><a href=\"csv_config.json\" target=\"_blank\">Click here for a JSON example</a>")
    @ApiResponses({@ApiResponse(code = 200, message = "files created", response = TrApiResponse.class)})
    @RequestMapping(value = "/generate", method = RequestMethod.POST, produces = "application/json")
    public TrApiResponse generateCsv(
            @ApiParam(value = "json example also has the default values", required = true) @Validated @RequestBody CsvConfigDTO csvConfigDTO) throws IOException, InterruptedException {
        String validationMessage = csvGeneratorService.validateCsvConfig(csvConfigDTO);
        TrApiResponse trApiResponse = new TrApiResponse();
        if (!validationMessage.isEmpty()) {
            trApiResponse.setError(validationMessage);
            trApiResponse.put("result", csvConfigDTO);
        } else {
            boolean result = csvGeneratorService.createCsv(csvConfigDTO);
            if (result == true) {
                trApiResponse.setOk();
                trApiResponse.put("result", csvConfigDTO);
            } else {
                trApiResponse.setError(csvConfigDTO.getValidationError());
            }
        }
        return trApiResponse;
    }

    @RequestMapping(value = "/duplicate", method = RequestMethod.POST, produces = "application/json")
    public TrApiResponse duplicateCsv(
            @ApiParam(value = "json example also has the default values", required = true) @Validated @RequestBody CsvExtendableDTO csvExtendableDTO) throws Exception {
        String validationMessage = extendCsvService.validateInput(csvExtendableDTO);
        TrApiResponse trApiResponse = new TrApiResponse();
        if (validationMessage.equalsIgnoreCase("No Errors")) {
            boolean result = extendCsvService.extendToCsvFile(csvExtendableDTO);
            if (result == true) {
                trApiResponse.setOk();
                trApiResponse.put("result", csvExtendableDTO);//// FIXME: 02/03/16 get validation string
            } else {
                trApiResponse.setError(csvExtendableDTO.getValidationError());
            }
        } else {
            trApiResponse.setError(validationMessage);
            trApiResponse.put("result", csvExtendableDTO);
        }
        return trApiResponse;
    }


    @RequestMapping(value = "/spdg", method = RequestMethod.POST, produces = "application/json")
    public TrApiResponse generateDataForSupervised(@ApiParam(value = "json example also has the default values", required = true) @Validated @RequestBody GenerateDataForSupervisedDTO generateDataForSupervisedDTO) throws Exception {
        String validationMessage = generateDataForSupervised.validateInput(generateDataForSupervisedDTO);
        TrApiResponse trApiResponse = new TrApiResponse();
        if (validationMessage.equalsIgnoreCase("OK")) {
            String result = generateDataForSupervised.GenerateDataForSupervised(generateDataForSupervisedDTO);
            trApiResponse.setMessage(result);
            if (!result.contains("ERROR")) {
                trApiResponse.setOk();
                trApiResponse.put("result", generateDataForSupervisedDTO);
            } else {
                trApiResponse.setError(result);
            }
        } else {
            trApiResponse.setError(validationMessage);
            trApiResponse.put("result", generateDataForSupervisedDTO);
        }
        return trApiResponse;
    }


    @RequestMapping(method = RequestMethod.GET, path = "/jsonconfig", produces = "application/json")
    @ApiOperation(value = "generateJson", nickname = "generateJson")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "name of feature", value = "field", allowMultiple = false,
                    required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "values", value = "header-test-value", allowMultiple = true,
                    required = true, dataType = "array", paramType = "query")
    })
    public TrApiResponse getJson(@RequestParam(value = "name of feature") String jj, @RequestParam(value = "values") ArrayList<String> values) {
        TrApiResponse trApiResponse = new TrApiResponse();
        System.out.println(jj);
        return trApiResponse;

    }
}
