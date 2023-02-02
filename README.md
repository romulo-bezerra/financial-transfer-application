# Financial Transfer Application

> Aplicação case de avaliação de transferencias financeiras

# O que é preciso ter instalado

* [Java 11](https://www.oracle.com/java/technologies/downloads/#java11)
* [Maven](https://maven.apache.org/download.cgi)

## Tecnologias utilizadas

* Java 11
* Spring Boot
* Rest
* Maven
* H2
* Lombok
* OpenApi

## Como rodar localmente

Para buildar o projeto:

```
$ make build
```

Executar o projeto:

```
$ make run-local
```

## Arquitetura Hexagonal

<img src="docs/hexagonal-architecture.png" width="600" height="300">

### Motivação

Código limpo, sustentável, com contextos definidos, código de domínio podendo mais facil serem bem testados e com lógica
de negócios isolados de preocupações externas.

### URLs para fácil acesso

<u>Health Application</u>

- http://localhost:8092/financial-transfer/api/v1/health

<u>H2 Database UI</u>

- http://localhost:8092/financial-transfer/h2

<u>Open API With Swagger UI</u>

- http://localhost:8092/financial-transfer/swagger-ui/index.html

---

### Conlusoes finais

1. falar do strategy como ponto d emelhoria caso o sistemas de transferencia se tornasse mais complexo
2. falar dos testes
3. pontos positivos:
4. O que poderia ser implementado pra melhoria (mas nao considerei para nao deixar tão complexo e por falta d etempo
   mesmo?
   numero de conta ser gerado unico

