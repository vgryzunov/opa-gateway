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
        http.formLogin().disable();
        http.logout().disable();
        http.httpBasic().disable();

        http.authorizeExchange(
                        exchanges -> exchanges.anyExchange().permitAll()
                                //.authenticated()
                                //.and().oauth2Login()
                                //.and().oauth2Client()
                )
                .exceptionHandling()
                //.exceptionHandling(ex -> ex.authenticationEntryPoint( new RedirectServerAuthenticationEntryPoint("/login")))
                //.authenticationEntryPoint(new CustomAuthenticationEntryPoint(HttpStatus.UNAUTHORIZED, responseBody))
        ;

        return http.build();
    }
}
