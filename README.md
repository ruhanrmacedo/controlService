# Control Service — API

API para gestão de serviços técnicos, profissionais responsáveis, registros executados, resumos operacionais e comissões.

## Sobre

Este repositório contém o backend do Control Service. A aplicação expõe endpoints REST para autenticação e administração das informações utilizadas pelo frontend Angular.

O sistema centraliza regras de negócio relacionadas a usuários, técnicos, serviços, execuções, contratos associados aos registros, valores e comissões.

## Origem e evolução

Projeto iniciado como Trabalho de Conclusão de Curso e posteriormente ampliado de forma autoral.

A evolução posterior adicionou e refinou funcionalidades operacionais, segurança, consultas por período, resumos e indicadores. O projeto é apresentado como uma aplicação acadêmica evoluída, e não como um produto comercial.

## Tecnologias

- Java 17;
- Spring Boot 3;
- Spring Web;
- Spring Data JPA e Hibernate;
- Spring Security;
- PostgreSQL;
- Flyway;
- JWT com Auth0 Java JWT;
- BCrypt;
- Bean Validation;
- Springdoc OpenAPI/Swagger;
- Lombok;
- Maven Wrapper.

## Arquitetura

A aplicação segue uma organização em camadas:

- **controllers:** endpoints REST e validação da entrada HTTP;
- **services:** regras de negócio, cálculos e coordenação das operações;
- **repositories:** persistência com Spring Data JPA;
- **entities:** modelo relacional da aplicação;
- **DTOs:** contratos de entrada, saída e projeções;
- **infra/security:** autenticação, filtro JWT e configuração do Spring Security;
- **infra/exception:** tratamento centralizado de erros;
- **infra/springdoc:** configuração da documentação OpenAPI;
- **migrations:** evolução do banco com Flyway.

## Funcionalidades

### Autenticação e usuários

- login com emissão de token JWT;
- cadastro de usuários;
- consulta do usuário autenticado;
- edição de dados;
- alteração de senha;
- desligamento e readmissão de usuários.

### Técnicos

- cadastro e listagem;
- edição;
- demissão e readmissão;
- exclusão.

### Serviços

- cadastro, consulta, edição e exclusão;
- classificação por tipos de serviço;
- serviços adicionais.

### Serviços executados

- registro, edição e exclusão;
- listagens operacionais;
- consultas por período, mês e ano;
- informações e quantidades de contratos vinculadas aos registros.

### Resumos e comissões

- resumo mensal;
- resumo quinzenal;
- cálculo de comissões;
- consolidação de contratos e valores executados;
- dados de evolução de valores e contratos para os gráficos do frontend.

As exportações PDF e XLSX são produzidas pelo frontend a partir dos dados fornecidos pela API.

## Banco de dados e migrations

A persistência utiliza PostgreSQL com JPA/Hibernate. O esquema é versionado por migrations Flyway mantidas em `src/main/resources/db/migration`.

Ao iniciar a aplicação em um ambiente configurado, o Flyway controla a aplicação das migrations habilitadas. Antes de executar o projeto, utilize um banco próprio e revise as variáveis do ambiente.

## Autenticação e autorização

- autenticação baseada em JWT;
- senhas processadas com BCrypt;
- filtro de segurança para requisições autenticadas;
- autorização de endpoints com perfis e anotações de segurança.

Os perfis identificados no projeto incluem `ADMINISTRADOR`, `GERENTE` e `ROOT`. Endpoints administrativos utilizam restrições compatíveis com essas responsabilidades.

## Configuração

Use o arquivo `.env.example` apenas como referência e nunca versione um `.env` real.

Variáveis documentadas pelo projeto:

```env
SERVER_PORT=<application_port>
DB_URL=<database_url>
DB_USER=<database_user>
DB_PASS=<database_password>
DB_DRIVER=<database_driver>
DB_POOL_MAX=<maximum_pool_size>
DB_POOL_MIN_IDLE=<minimum_idle_connections>
DB_POOL_IDLE_TIMEOUT=<idle_timeout>
DB_POOL_MAX_LIFETIME=<maximum_connection_lifetime>
DB_POOL_CONNECTION_TIMEOUT=<connection_timeout>
DB_POOL_TEST_QUERY=<connection_test_query>
DB_POOL_VALIDATION_TIMEOUT=<validation_timeout>
DB_POOL_KEEPALIVE_TIME=<keepalive_time>
FLYWAY_ENABLED=<true_or_false>
FLYWAY_LOCATIONS=<migration_locations>
JPA_SHOW_SQL=<true_or_false>
JPA_FORMAT_SQL=<true_or_false>
ERROR_STACKTRACE=<stacktrace_policy>
JWT_SECRET=<jwt_secret>
```

Utilize placeholders apenas como documentação. Defina valores apropriados no ambiente local e mantenha credenciais fora do Git.

## Executando localmente

### Requisitos

- Java 17;
- PostgreSQL;
- Maven Wrapper incluído no repositório.

### Windows

```powershell
.\mvnw.cmd spring-boot:run
```

### Linux ou macOS

```bash
./mvnw spring-boot:run
```

### Empacotamento

```bash
./mvnw clean package
```

No Windows, utilize o comando equivalente com `mvnw.cmd`.

## Testes

Para executar os testes:

```bash
./mvnw test
```

No Windows:

```powershell
.\mvnw.cmd test
```

O projeto contém o teste de contexto da aplicação e testes do serviço de registros executados. O conjunto atual ainda é limitado e inclui teste dependente de configuração de banco. Ampliar testes unitários e de integração faz parte da modernização do projeto.

## Documentação da API

O projeto possui configuração do Springdoc OpenAPI/Swagger. Com a aplicação em execução, a documentação gerada pode ser consultada conforme as rotas configuradas pelo Springdoc no ambiente utilizado.

## Frontend relacionado

A interface que consome esta API está disponível em:

- [Control Service — Frontend](https://github.com/ruhanrmacedo/control-service-front)

## Estado atual e modernização

As funcionalidades centrais de autenticação, cadastros, registros, resumos e comissões estão implementadas. Como projeto legado em evolução, os principais pontos de modernização são:

- ampliar testes automatizados;
- evoluir a documentação técnica;
- revisar configurações e padrões de segurança;
- manter a apresentação pública alinhada ao estado atual do sistema.

Esses itens são tratados como evolução técnica, sem alterar o contexto acadêmico e autoral do projeto.

## Autoria

Desenvolvido por **Ruhan Macedo**.

## Licença

Este projeto ainda não possui uma licença pública definida.
