package com.twtw.backend.config.security;

import com.twtw.backend.config.security.jwt.JwtAccessDeniedHandler;
import com.twtw.backend.config.security.jwt.JwtAuthenticationEntryPoint;
import com.twtw.backend.config.security.jwt.JwtFilter;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtFilter jwtFilter;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http.cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .httpBasic(h -> h.disable())
                .formLogin(f -> f.disable())
                .authorizeHttpRequests(
                        x ->
                                x.requestMatchers(
                                                "auth/refresh",
                                                "auth/save",
                                                "auth/login",
                                                "member/duplicate/**",
                                                "plan/**",
                                                "actuator/**")
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated())
                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(
                        x -> {
                            x.authenticationEntryPoint(jwtAuthenticationEntryPoint);
                            x.accessDeniedHandler(jwtAccessDeniedHandler);
                        })
                .build();
    }
}
