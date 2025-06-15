package com.gustavosdaniel.security.model;

public class Aluno {
    private long id;
    private String nome;
    private int matricula;

    public Aluno(long id, String nome, int matricula) {
        this.id = id;
        this.nome = nome;
        this.matricula = matricula;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", matricula=" + matricula +
                '}';
    }
}
