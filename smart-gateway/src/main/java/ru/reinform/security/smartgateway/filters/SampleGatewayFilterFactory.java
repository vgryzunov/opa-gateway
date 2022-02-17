package ru.reinform.security.smartgateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class SampleGatewayFilterFactory
        extends AbstractGatewayFilterFactory<SampleGatewayFilterFactory.Config> {

    public SampleGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            log.info("sample gateway filter - pre-processing");
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
