package ru.reinform.security.smartgateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class GatekeeperGatewayFilterFactory
        extends AbstractGatewayFilterFactory<GatekeeperGatewayFilterFactory.Config> {

    final Logger logger = LoggerFactory.getLogger(SampleGlobalFilter.class);

    public GatekeeperGatewayFilterFactory() {
        super(GatekeeperGatewayFilterFactory.Config.class);
    }

    @Override
    public GatewayFilter apply(GatekeeperGatewayFilterFactory.Config config) {
        return new GatekeeperGatewayFilter();
    }

    public static class Config {
    }
}
