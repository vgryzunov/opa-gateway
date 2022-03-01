package ru.reinform.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@Slf4j
public class ResourceServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResourceServerApplication.class, args);
    }

    @GetMapping("/resource")
    public String resource(@AuthenticationPrincipal Jwt jwt) {
        log.trace("***** JWT Headers: {}", jwt.getHeaders());
        log.trace("***** JWT Claims: {}", jwt.getClaims().toString());
        log.trace("***** JWT Token: {}", jwt.getTokenValue());

        return String.format("Resource accessed by: %s (with subjectId: %s)" ,
                jwt.getClaims().get("preferred_username"),
                jwt.getSubject());
    }



}
