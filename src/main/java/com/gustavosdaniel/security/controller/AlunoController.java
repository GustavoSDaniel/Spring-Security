package com.gustavosdaniel.security.controller;

import com.gustavosdaniel.security.model.Aluno;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AlunoController {

    private List<Aluno> alunos = new ArrayList<>(List.of(
            new Aluno(1, "Gustavo", 1),
            new Aluno(2, "Gabriela", 2),
            new Aluno(3, "Liziane", 4),
            new Aluno(4, "Gustavo", 16),
            new Aluno(5, "Wandro", 6)
    ));

    @GetMapping("/csrt-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) { // GERADOR DE TOKENS
        return (CsrfToken) request.getAttribute("_csrf");
    }


    @GetMapping("/alunos")
    public List<Aluno> getAlunos() {
        return alunos;
    }

    @PostMapping("/alunos")
    public Aluno addAluno(@RequestBody Aluno aluno) {
        alunos.add(aluno);
        return aluno;
    }
}
