package com.gustavosdaniel.security.filter;


import com.gustavosdaniel.security.service.JWTService;
import com.gustavosdaniel.security.service.MyUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component  // Transforma a classe em um componente gerenciado pelo Spring
public class JwtFilter extends OncePerRequestFilter {// Filtro executado uma vez por requisição

    private JWTService jwtService;
    private ApplicationContext applicationContext;

    public JwtFilter(JWTService jwtService, ApplicationContext applicationContext) {
        this.jwtService = jwtService;
        this.applicationContext = applicationContext;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. Extrai o token do cabeçalho Authorization
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // 2. Verifica se o token existe e começa com "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);  // Remove "Bearer " para pegar só o token
            username = jwtService.extractUserName(token); // Extrai username do token
        }

        // 3. Se tem username e não está autenticado
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 4. Carrega os detalhes do usuário do banco
            UserDetails userDetails = applicationContext.getBean(MyUserDetails.class).loadUserByUsername(username);

                // 5. Valida o token
                if(jwtService.tokenValidado(token, userDetails)){

                    // 6. Cria objeto de autenticação
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    // 7. Adiciona detalhes da requisição
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // 8. Registra a autenticação no contexto de segurança
                    SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // 9. Continua o processamento da requisição
        filterChain.doFilter(request, response);
    }
}
