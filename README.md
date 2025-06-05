
# API de Agendamento de Serviços Simples

## Autoria

Desenvolvido por:

-   [**Matheus Eduardo Morais de Faria**](https://www.linkedin.com/in/matheusedu7)  
    Responsável por modelagem, regras de negócio, entidades, serviços, controllers e toda a lógica de backend.
    
-   [**Tallys Labanca**](https://www.linkedin.com/in/tallys-labanca/)   
    Responsável por toda a camada de segurança (JWT, autenticação, login/registro), configuração dos filtros e atualmente também pelo desenvolvimento do **frontend** e integração total entre front e back.
    

> **Este projeto é fruto de um esforço colaborativo entre Matheus e Tallys, cada um contribuindo de forma crucial para entregar uma aplicação segura, funcional e bem estruturada.**

----------

## Sumário

-   Introdução
    
-   Objetivo do Projeto
    
-   Tecnologias Utilizadas
    
-   Documentação Swagger
    
-   Arquitetura e Estrutura dos Pacotes
    
-   Entidades e Relacionamentos
    
-   Regras de Negócio
    
-   Camada de Segurança
    
-   Principais Endpoints
    
-   Guia de Instalação e Uso
    
-   Para o Futuro
    

----------

## Introdução

Esta API de gerenciamento de serviços é uma solução desenvolvida como desafio final para o Bootcamp da Deloitte de Java e Angular. O sistema tem como objetivo **modernizar o agendamento de serviços entre clientes e profissionais**, oferecendo uma forma automatizada, eficiente e segura para esses processos.

Com esta aplicação, é possível **cadastrar profissionais e clientes, gerenciar serviços oferecidos, definir e consultar disponibilidades, realizar agendamentos, e controlar todas as regras de negócio associadas ao processo**. O sistema também garante segurança na autenticação e autorização, protegendo dados sensíveis e limitando acessos conforme o perfil do usuário.

Além disso, a solução proporciona maior transparência, controle de horários e histórico de agendamentos para profissionais e clientes, promovendo uma experiência mais organizada e moderna, tanto para prestadores de serviço quanto para usuários finais.

O frontend, bem como a integração com o back, está sendo desenvolvida por **Tallys Labanca**.

----------

## Objetivo do Projeto

-   Gerenciar agendamentos entre clientes e profissionais de forma segura.
    
-   Permitir profissionais cadastrarem disponibilidades e serviços.
    
-   Garantir que clientes agendem apenas em horários realmente disponíveis.
    
-   Evitar sobreposição de horários/agendamentos.
    
-   Implementar regras claras de cancelamento.
    
-   Garantir autenticação segura com JWT.
    

----------

## Tecnologias Utilizadas

-   **Java 17**
    
-   **Spring Boot 3.5.0**
    
-   **Spring Data JPA (Hibernate)**
    
-   **Spring Security (JWT)**
    
-   **Lombok**
    
-   **Swagger/OpenAPI**
    
-   **H2 Database** (para testes)
    
-   **Maven**
    

----------

## Documentação Swagger (ainda não feito)

Acesse a documentação e teste todos os endpoints:  
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

----------

## Arquitetura e Estrutura dos Pacotes

-   **controller:** Recebe requisições HTTP, responde com DTOs/ResponseEntity.
    
-   **service:** Lógica de negócio, validações, acesso a repositórios.
    
-   **model.entity:** Entidades JPA.
    
-   **model.dto:** Objetos de transferência de dados (request/response).
    
-   **model.mapper:** Conversão entre entidades e DTOs.
    
-   **repository:** Interfaces JPA para persistência.
    
-   **exception:** Exceptions customizadas para tratamento centralizado de erros.
    
-   **security:** Configuração de segurança, filtros JWT, autenticação/autorização.
    

----------

## Entidades e Relacionamentos

-   **User:**  
    `id, nome, email, senha, roleName (CLIENTE/PROFISSIONAL)`
    
-   **Servico:**  
    `id, nome, descricao, preco, duracao (min), profissional (User)`
    
-   **Disponibilidade:**  
    `id, profissional (User), diaSemana, horaInicio, horaFim`
    
-   **Agendamento:**  
    `id, cliente (User), profissional (User), servico (Servico), data, horaInicio, horaFim, status`
    

----------

## Regras de Negócio

-   Apenas profissionais cadastram disponibilidades/serviços.
    
-   Disponibilidade não pode sobrepor horários no mesmo dia.
    
-   Clientes só podem agendar em horários livres e disponíveis.
    
-   Não pode haver agendamento sobreposto para o mesmo profissional.

-   Um agendamento deve ter a mesma duração que o serviço proposto.
    
-   Cancelamento permitido apenas com pelo menos 2h de antecedência.
    
-   Cada usuário só acessa/edita seus próprios dados/agendamentos.
    

----------

## Camada de Segurança (por Tallys Labanca)

-   **Autenticação JWT** para todas rotas sensíveis.
    
-   **Registro e login** abertos; demais rotas protegidas por JWT.
    
-   **Roles** para CLIENTE/PROFISSIONAL, controlando acesso via filtros
    
-   **Frontend e integração**: Em desenvolvimento por **Tallys**.
    

----------

## Principais Endpoints
### - Registro de Usuário
```
POST /user/register
Content-Type: application/json

{
  "nome": "João Silva",
  "email": "joao@teste.com",
  "senha": "senha123",
  "roleName": "ROLE_CLIENTE"
}
```
### - Login de Usuário
```
POST /auth/login
Content-Type: application/json

{
  "email": "joao@teste.com",
  "senha": "senha123"
}
```

**Resposta**

```
{
  "token": "jwt-token-aqui"
}
```
###  Registro de Serviços (Apenas PROFISSIONAL)
```
POST /servicos/profissional/{profissionalid}
Authorization: Bearer <token-do-profissional>
Content-Type: application/json

{
  "nome": "Consulta Clínica",
  "descricao": "Atendimento clínico geral.",
  "preco": 120.0,
  "duracao": 60
}
```

**Resposta**

```
{
  "id": 3,
  "nome": "Consulta Clínica",
  "descricao": "Atendimento clínico geral.",
  "preco": 120.0,
  "duracao": 60,
  "profissionalId": 5
}
```
### Mostrar Serviço por id
```
GET /servicos/{id}
Authorization: Bearer <token>
```

**Resposta**

```
[
  {
    "id": 3,
    "nome": "Consulta Clínica",
    "descricao": "Atendimento clínico geral.",
    "preco": 120.0,
    "duracao": 60,
    "profissionalId": 5
  }
]
```
### Atualizar um Serviço (Apenas o dono)
```
GET /servicos/profissional/{profissionalid}
Authorization: Bearer <token>
```

**Resposta**

```
[
{

"nome":  "Corte de Cabelo Atualizado",
"descricao":  "Corte masculino e barba",
"preco":  100.0,
"duracao":  45

}
]

```
### Registro de disponibilidade (Apenas PROFISSIONAL)
```
POST /disponibilidades
Authorization: Bearer <token-do-profissional>
Content-Type: application/json

{
  "diasSemana": ["QUARTA", "SEXTA"],
  "horaInicio": "09:00",
  "horaFim": "12:00"
}
```

**Resposta**

```
[
  {
    "id": 10,
    "profissionalId": 5,
    "diaSemana": "QUARTA",
    "horaInicio": "09:00",
    "horaFim": "12:00"
  },
  {
    "id": 11,
    "profissionalId": 5,
    "diaSemana": "SEXTA",
    "horaInicio": "09:00",
    "horaFim": "12:00"
  }
]
```
### Listagem de Disponibilidades de um profissional
```
GET /disponibilidades/profissional/{profissionalId}
Authorization: Bearer <token>
```
### Registro de Agendamento (Apenas CLIENTE)
```
POST /agendamentos/agendamentos
Authorization: Bearer <token-do-cliente>
Content-Type: application/json

{
  "profissionalId": 5,
  "servicoId": 3,
  "data": "2025-06-06",
  "horaInicio": "10:00",
  "horaFim": "11:00"
}
```

**Resposta**

```
{
  "id": 18,
  "clienteId": 2,
  "profissionalId": 5,
  "servicoId": 3,
  "data": "2025-06-06",
  "horaInicio": "10:00",
  "horaFim": "11:00",
  "status": "AGENDADO"
}
```
### Cancelamento de Agendamento (Apenas CLIENTE)
```
DELETE /agendamentos/{id}
Authorization: Bearer <token-do-cliente>
```
### Listagem de Agendamentos do cliente (Apenas CLIENTE)
```
GET /agendamentos/meus
Authorization: Bearer <token-do-cliente>
```
### Listagem de Agendamentos de um Profissional
```
GET /agendamentos/profissional/{profissionalId}
Authorization: Bearer <token-do-profissional>
```


----------

## Guia de Instalação e Uso

1.  **Clone o repositório:**
    
    ```
    git clone https://github.com/Deloitte-bootcamp/backend
    ```
    
2.  **Configure o banco em**:
    
    ```
    spring.h2.console.enabled=true
    spring.datasource.url=jdbc:h2:mem:testdb
    ```
    
3.  **Instale dependências e rode:**
    
    ```
    mvn spring-boot:run
    ```
    
    ou utilize o botão "Run" na IDE.
    
4.  **Acesse a documentação e teste os endpoints:**  
    [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
    

----------

## Para o Futuro

-   Notificação por email para confirmações/cancelamentos.
    
-   Dashboard para profissionais.
    
-   Integração com gateways de pagamento.
    
-   Autenticação em dois fatores.
    
-   Polimento do front-end (em desenvolvimento).
    

----------

> *Tallys Labanca*  |  Segurança e Front-End
> (**[**LinkedIn**](https://www.linkedin.com/in/tallys-labanca/)**)
> 
>  *Matheus Eduardo Morais de Faria* | Back-end geral e regras de negócio  (**[**LinkedIn**](https://www.linkedin.com/in/matheusedu7)**)   ([**E-mail**](matheuseduardo_1994@hotmail.com)**)**
