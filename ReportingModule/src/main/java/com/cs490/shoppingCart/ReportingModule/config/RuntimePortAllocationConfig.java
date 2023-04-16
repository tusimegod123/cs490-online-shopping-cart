package za.co.metropolitan.obtracking.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.util.SocketUtils;

import java.util.List;


@Component
public class RuntimePortAllocationConfig implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Value("#{ '${server.port.range:8090-8090}'.split('-')}")
    private List<Integer> range;

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        if (range != null && !range.isEmpty())
            factory.setPort(SocketUtils.findAvailableTcpPort(range.get(0), range.get(1)));
    }
}
