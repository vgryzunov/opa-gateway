package ru.reinform.bundleserver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class CertsDataService {
    @Value("${endpoints.certs}") String uri;

    @Cacheable("certsdata")
    public String getCertsData() {
        RestTemplate client = new RestTemplate();

        log.trace("Getting certs from {} ...", uri);
        ResponseEntity<String> result = client.getForEntity(uri, String.class);
        return result.getBody();
    }
}
