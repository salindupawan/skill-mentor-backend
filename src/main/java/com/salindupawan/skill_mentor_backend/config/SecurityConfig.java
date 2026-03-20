package com.salindupawan.skill_mentor_backend.config;

import com.salindupawan.skill_mentor_backend.security.AuthenticationFilter;
import com.salindupawan.skill_mentor_backend.security.SkillMentorAuthenticationEntrypoint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final SkillMentorAuthenticationEntrypoint authenticationEntrypoint;
    private final AuthenticationFilter authFilter;

//    @Qualifier("corsConfigurationSource")
    private final CorsConfigurationSource source;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors-> cors.configurationSource(source))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        ses ->
                                ses.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(
                        ex ->
                                ex.authenticationEntryPoint(authenticationEntrypoint)
                )
                .authorizeHttpRequests(req -> req
                        .requestMatchers(
                                "/api/public/**",
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/webjars/**",
                                "/swagger-resources/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/mentors/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/mentors").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/subjects/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/subjects").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
