package com.gustavosdaniel.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    private String secretkey = ""; // Chave secreta para assinar os tokens

    // Construtor: gera a chave secreta automaticamente
    public JWTService() {

        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256"); // Algoritmo de geração
            SecretKey sk = keyGen.generateKey();// Gera uma nova chave
            secretkey = Base64.getEncoder().encodeToString(sk.getEncoded());  // Converte para Base64
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar chave JWT",e);  // Trata erro de algoritm
        }
    }

    // Método principal para gerar tokens JWT
    public String gerarToken(String username) {
        Map<String, Object> claims = new HashMap<>();  // Dados adicionais (payload)
        return Jwts.builder()
                .claims()
                .add(claims) // Adiciona claims (informações extras)
                .subject(username) // Define o dono do token
                .issuedAt(new Date(System.currentTimeMillis())) // Data de criação
                .expiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000))   // Expira em 10 minutos
                .and()
                .signWith(getKey()) // Assina com a chave secreta
                .compact(); // Gera o token compactado
    }

    // Converte a chave secreta para formato usado na assinatura
    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretkey); // Decodifica de Base64
        return Keys.hmacShaKeyFor(keyBytes); // Cria chave para algoritmo HMAC
    }

    // Extrai o username do token
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject); // Pega o subject (username)
    }

    // Método genérico para extrair informações do token
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token); // Extrai todos os claims
        return claimResolver.apply(claims); // Aplica a função para pegar o dado específico
    }

    // Extrai todos os dados do token (payload)
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey()) // Usa a chave para verificar
                .build()
                .parseSignedClaims(token)  // Faz o parsing
                .getPayload(); // Retorna os claims
    }

    // Valida se o token é válido para o usuário
    public boolean tokenValidado(String token, UserDetails userDetails) {
        final String userName = extractUserName(token); // Pega username do token
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token)); // Verifica match e expiração
    }

    // Verifica se o token expirou
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }  // Compara com data atual

    // Extrai data de expiração do token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    } // Pega campo expiration


}
