package com.tr.csvgenerator.controller;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by erez on 11/24/15.
 */
@RestController
@RequestMapping("/health")
@Api(value = "Health Check", description = "Checks the availability of the service")
public class HealthController {

    @ApiOperation(value = "CSV generator health check")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity health() {
        return new ResponseEntity(HttpStatus.OK);
    }
}
