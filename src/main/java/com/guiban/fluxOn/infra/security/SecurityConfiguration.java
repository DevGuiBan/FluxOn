package com.guiban.fluxOn.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        //Authentication endpoints
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()

                        //Responsibility endpoints
                        .requestMatchers(HttpMethod.POST, "/responsibility/register").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/responsibility/responsibilities").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/responsibility/update/{id}").hasRole("ADMIN")

                        //User endpoints
                        .requestMatchers(HttpMethod.GET, "/manager/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/manager/createUserSpecs").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/manager/usersWithSpecs").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/manager/user/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/manager/deactivateUser/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/manager/updateUserByUser/{id}").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.PUT, "/manager/updateUserByAdmin/{id}").hasRole("ADMIN")

                        //WorkSchedule endpoints
                        .requestMatchers(HttpMethod.GET, "/workSchedule/workSchedules").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/workSchedule/createWorkSchedule").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/workSchedule/updateWorkSchedule/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/workSchedule/bulkUpdateWorkSchedule").hasRole("ADMIN")

                        //TimeClock endpoints
                        .requestMatchers(HttpMethod.POST, "/timeClock/registerTimeClockIn").hasAnyRole("ADMIN", "EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/timeClock/timeClocks").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "timeClock/timeClocksById/{id}").hasAnyRole("ADMIN","EMPLOYEE")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
