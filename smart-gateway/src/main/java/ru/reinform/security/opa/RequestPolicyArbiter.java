package ru.reinform.security.opa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class RequestPolicyArbiter {
    @Value("${opa.url}") String opaURL;

    private final static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    public boolean decide(ServerWebExchange exchange) {

        ServerHttpRequest request = exchange.getRequest();

        String[] path = request.getURI().toString().replaceAll("^/|/$", "").split("/");

        Map<String, Object> input = new HashMap<>();
        input.put("method", request.getMethod());
        input.put("path", path);
        input.put("headers", request.getHeaders());


        String jsonInput;
        try {
            jsonInput = mapper.writeValueAsString(input);
        } catch (
                JsonProcessingException e) {
            log.error("Cannot serialize OPA input", e);
            return false;
        }


        RestTemplate client = new RestTemplate();
        HttpEntity<?> opaRequest = new HttpEntity<>(new DataRequest(jsonInput));

        DataResponse opaResponse = client.postForObject(opaURL, opaRequest, DataResponse.class);

        if (opaResponse == null) {
            return false;
        }

        return opaResponse.isResult();
    }
}
