package com.cs490.shoppingCart.ReportingModule.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "myapp")
@Configuration("appInfo")
public class AppInfo {
    private String userUrl;
    private String orderUrl;
    private String profitUrl;
}