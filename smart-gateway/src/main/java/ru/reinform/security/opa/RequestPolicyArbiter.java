package ru.reinform.security.opa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

@Slf4j
@Component
public class RequestPolicyArbiter {
    @Value("${opa.url}") String opaURL;

    public Result decide(ServerWebExchange exchange) {

        ServerHttpRequest request = exchange.getRequest();

        String[] path = request.getURI().toString().replaceAll("^/|/$", "").split("/");

        DataRequest data = new DataRequest();
        data.getInput().put("method", request.getMethod());
        data.getInput().put("path", path);
        data.getInput().put("headers", request.getHeaders());

        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> opaRequest = new HttpEntity<>(data, headers);

        DataResponse opaResponse = client.postForObject(opaURL, opaRequest, DataResponse.class);

        return opaResponse.getResult();
    }
}
