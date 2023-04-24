package com.cs490.shoppingCart.ProfitSharingModule.service;

import com.cs490.shoppingCart.ProfitSharingModule.dto.*;
import com.cs490.shoppingCart.ProfitSharingModule.model.Profit;
import com.cs490.shoppingCart.ProfitSharingModule.repository.ProfitRepository;
import com.cs490.shoppingCart.ProfitSharingModule.service.imp.ProfitServiceImp;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProfitServiceImpTest {

    @MockBean
    private ProfitRepository profitRepository;

    @Mock
    private RestTemplate restTemplate;

    @Autowired
    @InjectMocks
    private ProfitServiceImp profitService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void processProfitTest() {
        OrderDTO orderDTO = new OrderDTO();
        OrderLineDTO orderLineDTO = new OrderLineDTO();
        orderLineDTO.setPrice(10.0);
        orderLineDTO.setProductInfo("{\"productId\": 1, \"userId\": 2}");

        Set<OrderLineDTO> orderLines = new HashSet<>();
        orderLines.add(orderLineDTO);
        orderDTO.setOrderLines(orderLines);

        when(restTemplate.getForObject(eq("http://order-service:8085/api/v1/orders/1"), eq(OrderDTO.class))).thenReturn(orderDTO);

        Profit profit1 = new Profit();
        profit1.setAmount(8.0);
        profit1.setUserId(2L);
        profit1.setProductId(1L);
        profit1.setPercentage(80.0);
        profit1.setTransactionId(1L);
        profit1.setTransactionNumber("TN1");
        profit1.setTransactionDate(new Date());
        Profit profit2 = new Profit();
        profit2.setAmount(2.0);
        profit2.setUserId(0L);
        profit2.setProductId(1L);
        profit2.setPercentage(20.0);
        profit2.setTransactionId(1L);
        profit2.setTransactionNumber("TN1");
        profit2.setTransactionDate(new Date());
        when(profitRepository.saveAll(any())).thenReturn(Arrays.asList(profit1, profit2));

        ProfitRequest profitRequest = new ProfitRequest();
        profitRequest.setOrderId(1L);
        profitRequest.setTransactionId(1L);
        profitRequest.setTransactionNumber("TN1");
        profitRequest.setTransactionDate(new Date());

        Boolean result = profitService.processProfit(profitRequest);

        assertEquals(true, result);
    }

    @Test
    public void testGetRevenue() {
        // Set up test data
        ReportRequest request = new ReportRequest();
        request.setUserId(123L);
        request.setFromDate(LocalDate.of(2023, 1, 1));
        request.setToDate(LocalDate.of(2023, 12, 31));

        List<Profit> profitList = new ArrayList<>();
        profitList.add(new Profit(1L, 123L, 1L, 0.1, 100.0, 1L, "00001", new Date()));
        profitList.add(new Profit(2L, 123L, 2L, 0.2, 200.0, 2L, "00002", new Date()));
        profitList.add(new Profit(3L, 456L, 3L, 0.3, 300.0, 3L, "00003", new Date()));
        profitList.add(new Profit(4L, 456L, 4L, 0.4, 400.0, 4L, "00004", new Date()));

        // Set up mock repository
        when(profitRepository.findAllByUserIdAndTransactionDateIsBetween(eq(123L), any(), any()))
                .thenReturn(profitList);

        // Execute
        Double revenue = profitService.getRevenue(request);

        assertEquals(1000.0, revenue);
    }

    @Test
    public void testGetProfit() {
        ReportRequest request = new ReportRequest();
        request.setUserId(1L);
        request.setFromDate(LocalDate.of(2023, 1, 1));
        request.setToDate(LocalDate.of(2023, 12, 31));

        List<Profit> profitList = new ArrayList<>();
        profitList.add(new Profit(1L, 123L, 1L, 0.1, 100.0, 1L, "00001", new Date()));
        profitList.add(new Profit(2L, 123L, 2L, 0.2, 200.0, 2L, "00002", new Date()));
        profitList.add(new Profit(3L, 456L, 3L, 0.3, 150.0, 3L, "00003", new Date()));
       // profitList.add(new Profit(4L, 456L, 4L, 0.4, 400.0, 4L, "00004", new Date()));


        Map<String, Long> queryParams = new HashMap<>();
        queryParams.put("productId", 1L);
        queryParams.put("productId", 2L);
        queryParams.put("productId", 3L);


        List<ProductResponseDTO> productList = new ArrayList<>();
        ProductResponseDTO product1 = new ProductResponseDTO(1L,100d , 50d);
        ProductResponseDTO product2 = new ProductResponseDTO(2L, 200d, 100d);
        ProductResponseDTO product3 = new ProductResponseDTO(3L, 150d, 100d);
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);

        Mockito.when(profitRepository.findAllByUserIdAndTransactionDateIsBetween(1L,
                        Date.from(LocalDate.of(2023, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        Date.from(LocalDate.of(2023, 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant())))
                .thenReturn(profitList);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://product-service:8083/api/v1/products/productDetail");

        for (Map.Entry<String, Long> entry : queryParams.entrySet()) {
            builder.queryParam(entry.getKey(), entry.getValue());
        }

        String uriString = builder.toUriString();

        Mockito.when(restTemplate.exchange(
                        uriString,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<ProductResponseDTO>>() {}))
                .thenReturn(new ResponseEntity<>(productList, HttpStatus.OK));

        Double expectedProfit = 200d;
        Double actualProfit = profitService.getProfit(request);
        assertEquals(expectedProfit, actualProfit);

    }

}
