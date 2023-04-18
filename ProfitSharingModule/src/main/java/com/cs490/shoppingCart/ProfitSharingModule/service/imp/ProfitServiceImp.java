package com.cs490.shoppingCart.ProfitSharingModule.service.imp;
import com.cs490.shoppingCart.ProfitSharingModule.dto.ProfitRequest;
import com.cs490.shoppingCart.ProfitSharingModule.repository.ProfitRepository;
import com.cs490.shoppingCart.ProfitSharingModule.service.ProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProfitServiceImp implements ProfitService {

    @Autowired
    private ProfitRepository profitRepository;

    @Autowired
   private RestTemplate restTemplate;

    @Override
    public Double processProfit(ProfitRequest profitRequest) {
        System.out.println(profitRequest);

        //order Id = CONNECT order service -- list orderlines
        //loop order line, get productId, userid
        //split the amount to vendor abd service
        //save to your table

        return 0d;
    }
}
