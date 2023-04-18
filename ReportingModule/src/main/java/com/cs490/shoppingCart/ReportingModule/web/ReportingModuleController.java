package com.cs490.shoppingCart.ReportingModule.web;

import com.cs490.shoppingCart.ReportingModule.service.ReportingService;
import com.cs490.shoppingCart.ReportingModule.service.SalesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * The Class ReportingController.
 */
@RestController
@CrossOrigin
@RequestMapping("/reporting")
public class ReportingModuleController {

    @Autowired
    private ReportingService reportingService;


    @GetMapping("/sales")
    public ResponseEntity<?> getSales(
            @RequestParam(value="days", required = false) String days,
            @RequestParam(value="productName", required = false) String productName,
            @RequestParam(value="category", required = false) String category,
            @RequestParam(value="vendorId", required = false) String vendorId) {

        CustomErrorType error=new CustomErrorType("No sales found for the given input.");
        if(days != null){
            Optional<SalesDTO> sales = reportingService.getSalesByDays(days);
            return responseEntityForOptional(sales,error);
        }
        if(productName != null){
            Optional<SalesDTO> sales = reportingService.getSalesByProduct(productName);
            return responseEntityForOptional(sales,error);
        }
        if(category != null){
            Optional<SalesDTO> sales = reportingService.getSalesByCategory(category);
            return responseEntityForOptional(sales,error);
        }
        if(vendorId != null){
            Optional<SalesDTO> sales = reportingService.getSalesByVendor(vendorId);
            return responseEntityForOptional(sales,error);
        }
        return null;

    }

    @GetMapping("/annual-profit")
    public ResponseEntity<?> getAnnualProfit(
            @RequestParam(value="vendorId", required = false) String vendorId) {

        CustomErrorType error=new CustomErrorType("No records found for profit calculations");
        if(vendorId != null){
            Optional<SalesDTO> sales = reportingService.getAnnulProfitByVendor(vendorId);
            return responseEntityForOptional(sales,error);
        }
        else{
            Optional<SalesDTO> sales = reportingService.getTotalAnnualProfit();
            return responseEntityForOptional(sales,error);
        }

    }

    @GetMapping("/annual-loss")
    public ResponseEntity<?> getAnnualLoss(
            @RequestParam(value="vendorId", required = false) String vendorId) {

        CustomErrorType error=new CustomErrorType("No records found for loss calculations");
        if(vendorId != null){
            Optional<SalesDTO> sales = reportingService.getAnnulLossByVendor(vendorId);
            return responseEntityForOptional(sales,error);
        }
        else{
            Optional<SalesDTO> sales = reportingService.getTotalAnnualLoss();
            return responseEntityForOptional(sales,error);
        }

    }

    @GetMapping("/annual-revenue")
    public ResponseEntity<?> getAnnualRevenue(
            @RequestParam(value="vendorId", required = false) String vendorId) {

        CustomErrorType error=new CustomErrorType("No records found for revenue calculations");
        if(vendorId != null){
            Optional<SalesDTO> sales = reportingService.getAnnulRevenueByVendor(vendorId);
            return responseEntityForOptional(sales,error);
        }
        else{
            Optional<SalesDTO> sales = reportingService.getTotalAnnualRevenue();
            return responseEntityForOptional(sales,error);
        }

    }


    public ResponseEntity<?> responseEntityForOptional(Optional<SalesDTO> sales, CustomErrorType error){
        if(sales.isPresent()){
            return new ResponseEntity<>(sales.get(), HttpStatus.OK);
        } return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


}