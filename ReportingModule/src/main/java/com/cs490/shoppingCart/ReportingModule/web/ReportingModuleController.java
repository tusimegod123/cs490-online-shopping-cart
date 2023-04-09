package com.cs490.shoppingCart.ReportingModule.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

/**
 * The Class ReportingController.
 */
@CrossOrigin
@RestController
@RequestMapping("/reporting")
public class ReportingModuleController{



    /**
     * Generate report endpoint.
     *
     * @param reportDTO the report object
     * @return the String
     */
    @PostMapping(path = "/report")
    @ResponseBody
    public ResponseEntity<String> generateReport(@RequestBody ReportDTO report){

        return new ResponseEntity<>("Reports will be generated soon", HttpStatus.OK);
    }


    @GetMapping("/")
    public String hello() {
        return "<H1>Reporting Module Spring Boot Application Activated</H1>";
    }

}
