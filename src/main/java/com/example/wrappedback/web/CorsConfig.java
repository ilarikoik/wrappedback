// package com.example.wrappedback.web;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import
// org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// public class CorsConfig {

// @Bean
// public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
// Exception {
// http
// .authorizeRequests()
// .antMatchers("/**").permitAll() // Salli kaikki pyynnöt kaikille reiteille
// .anyRequest().permitAll() // Salli myös kaikki muut pyynnöt
// .and()
// .csrf().disable(); // Jos et halua käyttää CSRF-suojausta (ei suositeltavaa
// tuotannossa)

// return http.build();
// }
// }
