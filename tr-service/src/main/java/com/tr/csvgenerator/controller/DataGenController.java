package com.tr.csvgenerator.controller;


import RandomDataGeneratorObject.Data;
import com.tr.csvgenerator.RandomDataGenerator.DataGenService;
import com.tr.csvgenerator.RandomDataGenerator.Visualization.DataVisualizationService;
import com.tr.csvgenerator.common.TrApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    private DataGenService dataGenService;

    @Autowired
    private DataVisualizationService dataVisualizationService;

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    TrApiResponse generateRandomData(
            @ApiParam(value = "json example also has the default values", required = true)
            @RequestBody Data data) throws Exception {
        String result = dataGenService.gen(data);
        TrApiResponse trApiResponse = new TrApiResponse();
        trApiResponse.setMessage(result);
        if (!result.contains("ERROR")) {
            trApiResponse.setOk();
            trApiResponse.put("result", data);
        } else {
            trApiResponse.setError(result);
        }
        return trApiResponse;
    }

    @RequestMapping(value = "/DataVisualization", method = RequestMethod.POST, produces = "application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "x", value = "field", allowMultiple = false,
                    required = true, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "y", value = "field", allowMultiple = false,
                    required = true, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "z", value = "field", allowMultiple = false,
                    required = true, dataType = "integer", paramType = "query")
    })
    public
    @ResponseBody
    void DataVisualization(
            @RequestBody Data data,
            @RequestParam(value = "x") int x, @RequestParam(value = "y") int y, @RequestParam(value = "z") int z) throws Exception {
        dataVisualizationService.drawData(data, x, y, z);
    }
}
