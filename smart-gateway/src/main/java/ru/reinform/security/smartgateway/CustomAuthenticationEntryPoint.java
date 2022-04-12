package ru.reinform.security.smartgateway;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class CustomAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    private final HttpStatus httpStatus;
    private final Object responseBody;

    public CustomAuthenticationEntryPoint(HttpStatus httpStatus, Object responseBody) {
        Assert.notNull(httpStatus, "httpStatus cannot be null");
        Assert.notNull(responseBody, "responseBody cannot be null");
        this.httpStatus = httpStatus;
        this.responseBody = responseBody;
    }

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        return null;
    }
}
