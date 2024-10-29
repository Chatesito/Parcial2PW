package com.segundo.parcial.parcial_dos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class Config {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request -> {
                    // Configurando endpoints públicos
                    request.requestMatchers("/loginPage", "/").permitAll();

                    // Configurando endpoints privados para el rector
                    request.requestMatchers("/subject/add",
                            "/subject/delete/**",
                            "/subject/modify/**",
                            "/subject/insert").hasRole("RECTOR");

                    // Configurando endpoints para el docente
                    request.requestMatchers("/teacher/subjects",
                            "/teacher/subject/edit/**",
                            "/teacher/subject/updateSchedule").hasRole("TEACHER");

                    // Configurando endpoints para el estudiante
                    request.requestMatchers("/subject/list", "/teacher/list").hasAnyRole("RECTOR", "TEACHER", "ESTUDIANTE");

                    // Configurar el resto de endpoints NO ESPECIFICADOS
                    request.anyRequest().authenticated();
                })
                .formLogin(login -> {
                    login.loginPage("/loginPage");
                    login.successHandler(successHandler());
                    login.permitAll();
                })
                .logout(logout -> {
                    logout.logoutUrl("/logout");
                    logout.logoutSuccessUrl("/loginPage?logout");
                    logout.invalidateHttpSession(true);
                    logout.deleteCookies("JSESSIONID");
                })
                .exceptionHandling(ex -> ex.accessDeniedHandler(deniedHandler()));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (request, response, auth) -> {
            response.sendRedirect("/");
        };
    }

    @Bean
    public AccessDeniedHandler deniedHandler() {
        return (request, response, auth) -> {
            response.sendRedirect("/403");
        };
    }

}
