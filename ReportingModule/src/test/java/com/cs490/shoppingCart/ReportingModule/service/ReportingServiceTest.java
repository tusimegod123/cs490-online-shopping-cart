package com.cs490.shoppingCart.ReportingModule.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ReportingServiceTest {
    @Autowired
    private ReportingService reportingService;

    /**
     * Method under test: {@link ReportingService#getSales(ReportRequest)}
     */
    @Test
    void testGetSales() {
        // Arrange, Act and Assert
        assertTrue(reportingService.getSales(new ReportRequest()).isPresent());
        assertTrue(reportingService.getSales(null).isPresent());
        assertTrue(reportingService.getSales(new ReportRequest(LocalDate.ofEpochDay(1L), LocalDate.ofEpochDay(1L), 123L))
                .isPresent());
    }

    /**
     * Method under test: {@link ReportingService#getAnnulProfit(ReportRequest)}
     */
    @Test
    void testGetAnnulProfit() {
        // Arrange and Act
        Optional<SalesDTO> actualAnnulProfit = reportingService.getAnnulProfit(new ReportRequest());

        // Assert
        assertTrue(actualAnnulProfit.isPresent());
        assertNull(actualAnnulProfit.get().getAnnualProfit());
    }

    /**
     * Method under test: {@link ReportingService#getAnnualLoss(ReportRequest)}
     */
    @Test
    void testGetAnnualLoss() {
        // Arrange and Act
        Optional<SalesDTO> actualAnnualLoss = reportingService.getAnnualLoss(new ReportRequest());

        // Assert
        assertTrue(actualAnnualLoss.isPresent());
        assertNull(actualAnnualLoss.get().getAnnualLoss());
    }

    /**
     * Method under test: {@link ReportingService#getAnnualRevenue(ReportRequest)}
     */
    @Test
    void testGetAnnualRevenue() {
        // Arrange and Act
        Optional<SalesDTO> actualAnnualRevenue = reportingService.getAnnualRevenue(new ReportRequest());

        // Assert
        assertTrue(actualAnnualRevenue.isPresent());
        assertNull(actualAnnualRevenue.get().getAnnualRevenue());
    }

}

