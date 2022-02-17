package ru.reinform.security.smartgateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.csrf().disable();

        http.authorizeExchange()
                .pathMatchers("/iam/**").permitAll()
             .and()
                .authorizeExchange(
                        exchanges -> exchanges.anyExchange()
                                .authenticated()
                                .and()
                                .oauth2Login()
                )
        ;

        return http.build();
    }
}
