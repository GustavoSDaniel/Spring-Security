package com.gustavosdaniel.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // PARA INDICAR QUE ESSA CLASSE É DE SECURITY
public class SecurityConfig {

    private UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

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

//    @Bean FOI USADO PARA MOSTRA COMO ADICIONAR SEM UM DB
//    UserDetailsService userDetailsService() {
//        UserDetails user1 = User
//                .withDefaultPasswordEncoder()
//                .username("Gustavo")
//                .password("123")
//                .roles("USER")
//                .build();
//
//        UserDetails user2 = User
//                .withDefaultPasswordEncoder()
//                .username("Gabriela")
//                .password("123")
//                .roles("ADMIM")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1, user2);
//
//    }
//
    @Bean
    public AuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
            provider.setUserDetailsService(userDetailsService);
            return provider;
        }
}
