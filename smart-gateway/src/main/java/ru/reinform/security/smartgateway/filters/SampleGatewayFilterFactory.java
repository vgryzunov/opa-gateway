package ru.reinform.security.smartgateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class SampleGatewayFilterFactory
        extends AbstractGatewayFilterFactory<SampleGatewayFilterFactory.Config> {

    final Logger logger = LoggerFactory.getLogger(SampleGlobalFilter.class);

    public SampleGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            logger.info("sample gateway  filter - pre-processing");
            return chain.filter(exchange)
                    .then(Mono.fromRunnable( () -> logger.info("sample gateway filter - post-processing")));

        });
    }

    @Override
    public GatewayFilter apply(String routeId, Config config) {
        return super.apply(routeId, config);
    }

    public static class Config { }
}
