package com.tr.csvgenerator.controller;

import com.tr.csvgenerator.DataGenerator.Data;
import com.tr.csvgenerator.DataGenerator.DataGenService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Naor Ben David
 * Mail: naor.bendaivd@thetaray.com
 * Date: 4/15/16
 * Time: 10:47 AM
 */

@Controller
@RequestMapping("/DataGen")
@Api(value = "Random Data Generate")
public class DataGenController {

    private DataGenService dataGenService = new DataGenService();

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
    public String generateRandomData(
            @ApiParam(value = "json example also has the default values", required = true)
            @Validated @RequestBody Data data)throws Exception
    {
        dataGenService.gen(data);
        return data.getOutputFile();
//        String validationMessage = generateDataForSupervised.validateInput(generateDataForSupervisedDTO);
//        TrApiResponse trApiResponse = new TrApiResponse();
//        if (validationMessage.equalsIgnoreCase("OK"))
//        {
//            String result = generateDataForSupervised.GenerateDataForSupervised(generateDataForSupervisedDTO);
//            trApiResponse.setMessage(result);
//            if (!result.contains("ERROR")) {
//                trApiResponse.setOk();
//                trApiResponse.put("result", generateDataForSupervisedDTO);
//            } else {
//                trApiResponse.setError(result);
//            }
//        } else {
//            trApiResponse.setError(validationMessage);
//            trApiResponse.put("result", generateDataForSupervisedDTO);
//        }
//        return trApiResponse;
    }
}
