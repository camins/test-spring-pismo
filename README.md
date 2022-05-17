# API Pismo Back Teste 3.0

API developed for Pismo challenge testing

## Ferramentas

- [Maven](https://maven.apache.org/) 
- [MySQL](https://www.mysql.com/)
- [Spring Boot](https://start.spring.io/) 
- [JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Swagger](https://swagger.io/docs/)
- [JUnit](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito](https://site.mockito.org/)
- [Docker](https://www.docker.com/get-started/)
- [Docker Compose](https://docs.docker.com/compose/install/)

## Bora testar!

* Para criar o BD mySQL, rodar o seguinte comando:
  
        docker-compose -f docker-compose.yml up -d
        
* Para rodar a aplicação, realizar o clone e abrir em sua IDE de preferência.
* Documentação da aplicação desenvolvida com swagger: http://localhost:8080/swagger-ui.html
* O endpoint POST de accounts pode ser testado com o seguinte JSON:
        
        {
              "documentNumber": "12345678900"
        }
        
Ambos endpoints retornam as seguintes informações:
        
        {
              "id": 1
              "documentNumber": "12345678900"
        }

* O endpoint POST de transaction pode ser testado com o seguinte JSON:
        
        {
              "accountId" : 1,
              "operationType" : 1,
              "amount": -20
        }
        
Ele retorna a seguinte informação:

        {
              "id": 1,
              "account": {
                    "id": 1,
                    "documentNumber": "12345678900"
              },
              "operationType": 1,
              "amount": -20.0,
              "eventDate": "2022-05-17T10:00:00.4264589"
        }
