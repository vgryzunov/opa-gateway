package ru.reinform.security.smartgateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    Map<String, String> responseBody = new HashMap<>();

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.csrf().disable();

        http.authorizeExchange()
                //.pathMatchers("/hello").permitAll()
             .and()
                .authorizeExchange(
                        exchanges -> exchanges.anyExchange()
                                .authenticated()
                                .and()
                                .oauth2Login()
                                .and()
                                .oauth2Client()
                )
                .exceptionHandling()
                //.authenticationEntryPoint(new CustomAuthenticationEntryPoint(HttpStatus.UNAUTHORIZED, responseBody))
        ;

        return http.build();
    }
}
