package com.Chaltteok.DailyCheck.config;

import com.Chaltteok.DailyCheck.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    //private final UserService userService;

    @Bean
    public WebSecurityCustomizer configure(){
        return (web)->web.ignoring()
                //.requestMatchers(toH2Console())
                .requestMatchers("/static/**");
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http, HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .httpBasic((auth)-> auth.disable())
//                .csrf((auth)-> auth.disable())
//                .sessionManagement((session)->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .cors((auth)-> auth.disable());
//        httpSecurity
//                .authorizeHttpRequests((auth)->auth
//                        .requestMatchers("/","/login/**","/sign-up").permitAll()
//                        .anyRequest().authenticated());
//                //.addFilterBefore(new JwtTokenFilter)
//        return httpSecurity.build();
//    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/webjars/**", "/swagger-resources/**").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .permitAll()
                );

        return http.build();
    }
}
