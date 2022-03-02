package ru.reinform.security.smartgateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.reinform.security.opa.Result;
import ru.reinform.security.opa.RequestPolicyArbiter;

@Slf4j
@Component
@ComponentScan("ru.reinform.security.opa")
public class RequestPolicyFilterFactory extends AbstractGatewayFilterFactory<RequestPolicyFilterFactory.Config> {

    final
    RequestPolicyArbiter requestPolicyArbiter;

    public RequestPolicyFilterFactory(RequestPolicyArbiter requestPolicyArbiter) {
        super(Config.class);
        this.requestPolicyArbiter = requestPolicyArbiter;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            log.info("preparing data...");
            Result result = requestPolicyArbiter.decide(exchange);
            if (result == null) {
                log.error("Decision is null!");
                return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "Policy restrictions"));
            }

            log.debug("allowed: {}, name: {}, tokenValid: {}", result.isAllowed(), result.getName()
                    , result.isTokenValid());

            if (!result.isAllowed() || !result.isTokenValid()) {
                log.info("Access for subject  \"{}\" is denied: allowed by OPA: {}, token is valid: {}"
                        , result.getName(), result.isAllowed(), result.isTokenValid());
                return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "Policy restrictions"));
            }

            return chain.filter(exchange)
                    .then(Mono.fromRunnable( () -> log.debug("Passed authorization filter")));

        });
    }

    @Override
    public GatewayFilter apply(String routeId, Config config) {
        return super.apply(routeId, config);
    }

    public static class Config { }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus)  {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }
}
