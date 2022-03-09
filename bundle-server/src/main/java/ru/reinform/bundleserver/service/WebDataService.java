package ru.reinform.bundleserver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class WebDataService {
    public String getHttpData(String uri) {
        RestTemplate client = new RestTemplate();

        log.trace("Getting data from {} ...", uri);
        ResponseEntity<String> result = client.getForEntity(uri, String.class);
        return result.getBody();
    }
}
