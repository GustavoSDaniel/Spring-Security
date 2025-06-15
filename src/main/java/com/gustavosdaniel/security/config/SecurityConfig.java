package com.gustavosdaniel.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // PARA INDICAR QUE ESSA CLASSE É DE SECURITY
public class SecurityConfig {

    @Bean //define as regras de segurança da aplicação
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(request -> request.anyRequest().authenticated()) // Exige autenticação para TODAS as requisições
                .formLogin(Customizer.withDefaults()) //Ativa o formulário de login padrão do Spring Security
                .httpBasic(Customizer.withDefaults()) //  Permite autenticação via cabeçalho Authorization: Basic <credenciais>
                .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //Desabilita criação de sessões no servidor
                .build(); //Compila todas as configurações e retorna a cadeia de filtros
    }

    @Bean
    UserDetailsService userDetailsService() {
        UserDetails user1 = User
                .withDefaultPasswordEncoder()
                .username("Gustavo")
                .password("123")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager();

    }



}
