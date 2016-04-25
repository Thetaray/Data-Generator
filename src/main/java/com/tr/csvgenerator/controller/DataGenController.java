package com.tr.csvgenerator.controller;

import com.tr.csvgenerator.DataGenerator.*;
import com.tr.csvgenerator.common.TrApiResponse;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;

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

    @Autowired
    private DataGenService dataGenService;// = new DataGenServiceImpl();

    @RequestMapping(value = "/create1", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    TrApiResponse generateRandomData(
            @ApiParam(value = "json example also has the default values", required = true)
            @RequestBody Data data)throws Exception
    {
        String result = dataGenService.gen(data);
        TrApiResponse trApiResponse = new TrApiResponse();
        trApiResponse.setMessage(result);
        if (!result.contains("ERROR"))
        {
            trApiResponse.setOk();
            trApiResponse.put("result", data);
        }
        else {
            trApiResponse.setError(result);
        }
        return trApiResponse;
    }
}
