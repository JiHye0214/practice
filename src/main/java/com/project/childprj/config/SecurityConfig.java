package com.project.childprj.config;

import com.lec.spring.config.LoginFailure;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    // password Encoder
    @Bean
    public
    PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/post/detail/*").authenticated()
                        .requestMatchers("/post/write").authenticated()
                        .requestMatchers("/product/detail/*").authenticated()
                        .requestMatchers("/product/write").authenticated()
                        .requestMatchers("/user/mypage").authenticated()
                        .requestMatchers("/user/zzim").authenticated()
                        .anyRequest().permitAll()
                )

                .formLogin(form -> {
                            form
                                    .usernameParameter("loginId")
                                    .loginPage("/user/logIn")
                                    .loginProcessingUrl("/user/logIn")
                                    .successHandler(new LoginSuccess("/home"))
                                    .failureHandler(new LoginFailure());
                        }
                )

                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutUrl("/doLogout")
                        .logoutSuccessUrl("/home")
                        .invalidateHttpSession(false)
                )

                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                        .accessDeniedHandler(new AccessDeniedHandler1())
                )
                .build();
    }
}
