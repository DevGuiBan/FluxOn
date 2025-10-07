# FluxOn

FluxOn é um sistema de controle de ponto eletrônico desenvolvido em Java com Spring Boot. Ele permite o registro de horários de entrada e saída de usuários, além de gerenciar status de presença e justificativas.

## Funcionalidades

- Registro de ponto (entrada e saída)
- Controle de status de presença (ex: presente, ausente)
- Justificativa de ausência
- Integração com banco de dados PostgreSQL
- API RESTful para integração com frontends ou outros sistemas

## Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Maven
- PostgreSQL
- Spring Security (autenticação e autorização)
- JPA/Hibernate

## Como executar

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/DevGuiBan/FluxOn.git

2. **Configure o banco de dados:**
    ```bash
   spring.datasource.url=jdbc:postgresql://localhost:5432/fluxon
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha

3. **Instalar as dependências:**
   ```bash
   mvn clean install

4. **Compile e execute o projeto:**
    ```bash
   mvn spring-boot:run

5. **Acesse a API:**
    ```bash
   http://localhost:8080
   
## Exemplo de Uso

```bash

    localhost:8080/auth/register

    {
        "name": "Exemple",
        "email": "exemple@mail.com",
        "password": "123"
    }
