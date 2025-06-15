package com.gustavosdaniel.security.service;

import com.gustavosdaniel.security.model.UserPrincipal;
import com.gustavosdaniel.security.model.Users;
import com.gustavosdaniel.security.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetails implements UserDetailsService { // Implementa interface de segurança

    private final UserRepository userRepository;

    public MyUserDetails(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override // Método obrigatório que carrega usuário pelo username
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1. Busca usuário no banco pelo username
        Users user = userRepository.findByUsername(username);

        // 2. Se não encontrar, lança exceção
        if (user == null) {
            System.out.println("Usuario não encontrado");
            throw new UsernameNotFoundException("Usuario não encontrado");
        }

        // 3. Retorna UserDetails (UserPrincipal) com os dados do usuário
        return new UserPrincipal(user);
    }
}
