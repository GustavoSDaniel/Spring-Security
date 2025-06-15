package com.gustavosdaniel.security.config;

import com.gustavosdaniel.security.filter.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // PARA INDICAR QUE ESSA CLASSE É DE SECURITY
public class SecurityConfig {

    private JwtFilter jwtFilter;

    private UserDetailsService userDetailsService;

    public SecurityConfig(JwtFilter jwtFilter, UserDetailsService userDetailsService) {
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean //define as regras de segurança da aplicação
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(customizer -> customizer.disable())// Desabilita CSRF (não necessário para APIs)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("register", "login")// Libera acesso sem autenticação
                        .permitAll()
                        .anyRequest().authenticated()) // Exige autenticação para TODAS as requisições
                .httpBasic(Customizer.withDefaults())  // Habilita autenticação básica (usuário/senha)
                .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //Desabilita criação de sessões no servidor
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)// Adiciona nosso filtro JWT
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
    @Bean// Configura como a autenticação vai funcionar
    public AuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setPasswordEncoder(new BCryptPasswordEncoder(12));  // Usa BCrypt forte para senhas
            provider.setUserDetailsService(userDetailsService); // Define onde buscar usuários
            return provider;
    }

    @Bean// Expõe o gerenciador de autenticação para o Spring
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();// Retorna o gerenciador padrão
    }
}
