package com.gustavosdaniel.security.service;

import com.gustavosdaniel.security.model.UserPrincipal;
import com.gustavosdaniel.security.model.Users;
import com.gustavosdaniel.security.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetails implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetails(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = userRepository.findByUsername(username);
        if (user == null) {
            System.out.println("Usuario não encontrado");
            throw new UsernameNotFoundException("Usuario não encontrado");
        }

        return new UserPrincipal(user);
    }
}
