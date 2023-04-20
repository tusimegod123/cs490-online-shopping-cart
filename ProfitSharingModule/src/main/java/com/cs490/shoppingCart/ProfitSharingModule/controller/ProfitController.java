package com.cs490.shoppingCart.ProfitSharingModule.controller;
import com.cs490.shoppingCart.ProfitSharingModule.dto.ProfitRequest;
import com.cs490.shoppingCart.ProfitSharingModule.dto.ReportRequest;
import com.cs490.shoppingCart.ProfitSharingModule.service.ProfitService;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/profit")
public class ProfitController {

    @Autowired
    private ProfitService profitService;

    @PostMapping("/processProfit")
    public ResponseEntity<?> processProfit(@RequestBody ProfitRequest request)  {
        profitService.processProfit(request);
        return ResponseEntity.ok().body("Profit share is divided successfully!");
    }

    @PostMapping("/getProfit")
    public ResponseEntity<?> getProfit(@RequestBody ReportRequest request)  {
        Double profit = profitService.getProfit(request);
        return ResponseEntity.ok().body(profit);
    }

    @PostMapping("/getRevenue")
    public ResponseEntity<?> getRevenue(@RequestBody ReportRequest request)  {
        Double revenue = profitService.getRevenue(request);
        return ResponseEntity.ok().body(revenue);
    }

}
