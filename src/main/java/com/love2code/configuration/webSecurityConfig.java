//package com.love2code.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//@Configuration
//@EnableWebSecurity
//public class webSecurityConfig {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        // adding security on the viewpage
//        http.authorizeRequests((requests) ->
//                        requests.requestMatchers(
//                                        new AntPathRequestMatcher("/"),
//                                        new AntPathRequestMatcher("/search"),
//                                        new AntPathRequestMatcher("/css/")
//                                ).permitAll()
//                                .anyRequest().authenticated()
//                );
//
//
//
//        return http.build();
//    }
//
//
//
//}
