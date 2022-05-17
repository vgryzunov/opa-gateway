package ru.reinform.security.smartgateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
@Slf4j
public class LoggingGatewayFilterFactory
        extends AbstractGatewayFilterFactory<LoggingGatewayFilterFactory.Config> {

    public LoggingGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            log.info("sample gateway filter - pre-processing");

            String requestPath = exchange.getRequest().getPath().toString();
            log.info("Request path = " + requestPath);

            HttpHeaders headers = exchange.getRequest().getHeaders();
            Set<String> headerNames = headers.keySet();

            headerNames.forEach((header) -> log.info(header + " " + headers.get(header)));

            return chain.filter(exchange)
                    .then(Mono.fromRunnable( () -> log.info("sample gateway filter - post-processing")));

        });
    }

    @Override
    public GatewayFilter apply(String routeId, Config config) {
        return super.apply(routeId, config);
    }

    public static class Config { }
}
