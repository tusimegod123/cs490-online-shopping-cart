//package com.cs490.shoppingCart.ProfitSharingModule.controller;
//
//import static org.junit.Assert.assertEquals;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import com.cs490.shoppingCart.ProfitSharingModule.dto.ProfitRequest;
//import com.cs490.shoppingCart.ProfitSharingModule.dto.ReportRequest;
//import com.cs490.shoppingCart.ProfitSharingModule.service.ProfitService;
//import com.cs490.shoppingCart.ProfitSharingModule.service.SalesDTO;
//
//import java.time.LocalDate;
//
//@RunWith(MockitoJUnitRunner.class)
//public class ProfitControllerTest {
//
//    @Mock
//    private ProfitService profitService;
//
//    @InjectMocks
//    private ProfitController profitController;
//
//    private ProfitRequest profitRequest;
//    private ReportRequest reportRequest;
//    private SalesDTO salesDTO;
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//        profitRequest = new ProfitRequest();
//        reportRequest = new ReportRequest();
//        reportRequest.setUserId(1L);
//        reportRequest.setFromDate(LocalDate.of(2023, 1, 1));
//        reportRequest.setToDate(LocalDate.of(2023, 12, 31));
//        salesDTO = new SalesDTO();
//    }
//
//    @Test
//    public void testProcessProfit() {
//        ResponseEntity<?> response = profitController.processProfit(profitRequest);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Profit share is divided successfully!", response.getBody());
//    }
//
//    @Test
//    public void testGetProfit() {
//        Double profit = 5000.0;
//        Mockito.when(profitService.getProfit(reportRequest)).thenReturn(profit);
//        ResponseEntity<?> response = profitController.getProfit(reportRequest);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(profit, response.getBody());
//    }
//
//    @Test
//    public void testGetRevenue() {
//        Double revenue = 20000.0;
//        Mockito.when(profitService.getRevenue(reportRequest)).thenReturn(revenue);
//        ResponseEntity<?> response = profitController.getRevenue(reportRequest);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(revenue, response.getBody());
//    }
//
//    @Test
//    public void testGetReport() {
//        Double revenue = 20000.0;
//        Double profit = 5000.0;
//        Mockito.when(profitService.getRevenue(reportRequest)).thenReturn(revenue);
//        Mockito.when(profitService.getProfit(reportRequest)).thenReturn(profit);
//        salesDTO.setAnnualRevenue(revenue);
//        salesDTO.setAnnualProfit(profit);
//        ResponseEntity<?> response = profitController.getReport(reportRequest);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(salesDTO.getAnnualRevenue(), ((SalesDTO) response.getBody()).getAnnualRevenue());
//        assertEquals(salesDTO.getAnnualProfit(), ((SalesDTO) response.getBody()).getAnnualProfit());
//    }
//
//    @Test
//    public void testGetReportException() {
//        Mockito.when(profitService.getRevenue(reportRequest)).thenThrow(new RuntimeException("Exception occurred"));
//        ResponseEntity<?> response = profitController.getReport(reportRequest);
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertEquals("Exception occurred", response.getBody());
//    }
//}
