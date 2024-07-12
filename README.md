# 📚 Library Management System

Este é um projeto de um sistema de gerenciamento de biblioteca desenvolvido em Java utilizando Spring Framework. O objetivo é fornecer uma aplicação web baseada no padrão MVC, permitindo gerenciar usuários, livros e empréstimos de maneira eficiente.

## 🛠️ Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Docker
- Docker Compose

## ✨ Funcionalidades

- **Gerenciamento de Usuários:**
  - Criação, leitura, atualização e exclusão de usuários.
- **Gerenciamento de Livros:**
  - Criação, leitura, atualização e exclusão de livros.
- **Gerenciamento de Empréstimos:**
  - Registro de empréstimos de livros, incluindo data de empréstimo e data de devolução.
  - Relacionamento muitos-para-muitos entre livros e empréstimos.

## 🚀 Como Executar

### Pré-requisitos

- Docker e Docker Compose instalados
- JDK 17 ou superior
- Maven

### Passos

1. Clone o repositório:
   ```bash
   git clone https://github.com/Guilherme23k/library-management-system.git
   cd library-management-system
   ```
2. Construa e inicie os containers Docker:
    ```bash
   docker-compose up --build
    ```
3. A aplicação estará disponível em http://localhost:8080.



