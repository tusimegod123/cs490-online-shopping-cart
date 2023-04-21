package com.cs490.shoppingCart.ReportingModule.web;

import com.cs490.shoppingCart.ReportingModule.service.ReportRequest;
import com.cs490.shoppingCart.ReportingModule.service.ReportingService;
import com.cs490.shoppingCart.ReportingModule.service.SalesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

/**
 * The Class ReportingController.
 */
@RestController
@CrossOrigin("*")
public class ReportingModuleController {

    @Autowired
    private ReportingService reportingService;


    @GetMapping("/sales")
    public ResponseEntity<?> getSales(
            @RequestParam(value="fromDate", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate fromDate,
            @RequestParam(value="toDate", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate toDate,
            @RequestParam(value="vendorId", required = false) Long vendorId) {

        CustomErrorType error=new CustomErrorType("No sales found for the given input.");
        ReportRequest request = new ReportRequest(fromDate, toDate, vendorId);
        Optional<SalesDTO> sales = reportingService.getSales(request);
        return responseEntityForOptional(sales,error);

    }

    @GetMapping("/annual-profit")
    public ResponseEntity<?> getAnnualProfit(
            @RequestParam(value="fromDate", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate fromDate,
            @RequestParam(value="toDate", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate toDate,
            @RequestParam(value="vendorId", required = false) Long vendorId) {

        CustomErrorType error=new CustomErrorType("No records found for profit calculations");
        ReportRequest request = new ReportRequest(fromDate, toDate, vendorId);
        Optional<SalesDTO> sales = reportingService.getAnnulProfit(request);
        return responseEntityForOptional(sales,error);
    }

    @GetMapping("/annual-loss")
    public ResponseEntity<?> getAnnualLoss(
            @RequestParam(value="fromDate", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate fromDate,
            @RequestParam(value="toDate", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate toDate,
            @RequestParam(value="vendorId", required = false) Long vendorId) {

        CustomErrorType error=new CustomErrorType("No records found for loss calculations");
        ReportRequest request = new ReportRequest(fromDate, toDate, vendorId);
        Optional<SalesDTO> sales = reportingService.getAnnualLoss(request);
        return responseEntityForOptional(sales,error);

    }

    @GetMapping("/annual-revenue")
    public ResponseEntity<?> getAnnualRevenue(
            @RequestParam(value="fromDate", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate fromDate,
            @RequestParam(value="toDate", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate toDate,
            @RequestParam(value="vendorId", required = false) Long vendorId) {

        CustomErrorType error=new CustomErrorType("No records found for revenue calculations");
        ReportRequest request = new ReportRequest(fromDate, toDate, vendorId);
        Optional<SalesDTO> sales = reportingService.getAnnualRevenue(request);
        return responseEntityForOptional(sales,error);

    }


    public ResponseEntity<?> responseEntityForOptional(Optional<SalesDTO> sales, CustomErrorType error){
        if(sales.isPresent()){
            return new ResponseEntity<>(sales.get(), HttpStatus.OK);
        } return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


}