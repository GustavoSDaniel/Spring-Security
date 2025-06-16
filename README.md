# 🔐 Spring Boot API com Spring Security e JWT

Este projeto demonstra a implementação de uma API REST segura utilizando **Spring Boot**, **Spring Security** e **JWT (JSON Web Token)** para autenticação e autorização. A aplicação inclui funcionalidades de registro e login de usuários, além de um exemplo básico de gerenciamento de dados protegidos.

---

##  Funcionalidades

- **Autenticação e Autorização com JWT**: Sistema robusto de segurança para proteger os endpoints da API.
- **Registro de Usuário**: Endpoint público para criação de novas contas.
- **Login de Usuário**: Endpoint público que autentica o usuário e retorna um JWT.
- **Gerenciamento de Usuários**: Integração com JPA para armazenar e recuperar informações.
- **Codificação de Senha**: Armazenamento seguro com o algoritmo BCrypt.
- **Filtro JWT Personalizado**: Intercepta e valida o token JWT nas requisições.
- **Sessões Stateless**: API configurada para não manter estado de sessão no servidor.
- **Exemplo de Endpoint Protegido**: Recurso `/alunos` que requer autenticação JWT.

---

## 🛠️ Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot**
- **Spring Security**
- **Spring Data JPA**
- **JJWT (Java JWT)**
- **Maven**
- **BCrypt**

---

## ✅ Pré-requisitos

- Java Development Kit (JDK) 17 ou superior
- Maven 3.x ou superior
- IDE (IntelliJ IDEA, VS Code ou Eclipse - opcional)

